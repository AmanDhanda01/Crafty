# Start the local Docker services and apply the preview resources to the selected Kubernetes context.
$ErrorActionPreference = 'Stop'

function Invoke-Step {
    param([string]$Purpose, [scriptblock]$Command)
    Write-Host "`n== $Purpose =="
    & $Command
    if ($LASTEXITCODE -ne 0) { throw "$Purpose failed (exit code $LASTEXITCODE)." }
}

function Start-PortForward {
    param([string]$Service, [int]$LocalPort, [int]$TargetPort)
    $listener = Get-NetTCPConnection -LocalPort $LocalPort -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1
    if ($listener) {
        $owner = Get-Process -Id $listener.OwningProcess -ErrorAction SilentlyContinue
        if ($owner.ProcessName -eq 'kubectl') {
            Write-Host "Port $LocalPort already has a kubectl listener; leaving the existing port-forward running."
            return
        }
        throw "Port $LocalPort is already in use by '$($owner.ProcessName)' (PID $($listener.OwningProcess)). Stop it or choose another local port."
    }

    $mapping = '{0}:{1}' -f $LocalPort, $TargetPort
    $command = "kubectl port-forward -n crafty svc/$Service $mapping"
    Start-Process -FilePath 'powershell.exe' -WorkingDirectory $PSScriptRoot -WindowStyle Normal -ArgumentList @('-NoExit', '-Command', $command)
}

Set-Location -LiteralPath $PSScriptRoot

$context = (& kubectl config current-context).Trim()
if ($LASTEXITCODE -ne 0) { throw 'kubectl has no current context. Select your Kubernetes context and rerun this script.' }
Write-Host "Using Kubernetes context: $context"

# Start PostgreSQL and MinIO in Docker Compose on the host.
Invoke-Step 'Start Docker Compose services' { docker compose -f .\services.docker-compose.yml up -d }

# Apply the Kubernetes services and deployments to the selected cluster.
Invoke-Step 'Apply Kubernetes infrastructure' { kubectl apply -f .\k8s\infra.yml }
Invoke-Step 'Create/update MinIO credentials' { kubectl create secret generic minio-credentials -n crafty --from-literal=root-user=minioadmin --from-literal=root-password=minioadmin123 --dry-run=client -o yaml | kubectl apply -f - }
Invoke-Step 'Apply MinIO and runner-pool deployments' { kubectl apply -f .\k8s\runner-pods.yml }
Invoke-Step 'Apply runner network policy' { kubectl apply -f .\k8s\policy.yml }

# Build the proxy image in the local Docker engine, then apply its Kubernetes deployment.
Invoke-Step 'Build shuttle-proxy image' { docker build -t shuttle-proxy:latest .\proxy }
Invoke-Step 'Apply shuttle-proxy deployment and service' { kubectl apply -f .\k8s\shuttle-proxy.yml }
Invoke-Step 'Restart shuttle-proxy deployment' { kubectl rollout restart deployment/shuttle-proxy -n crafty }

# Wait until the services are ready before opening their local port forwards.
Invoke-Step 'Wait for Redis' { kubectl rollout status deployment/redis-server -n crafty --timeout=180s }
Invoke-Step 'Wait for MinIO' { kubectl rollout status deployment/minio -n crafty --timeout=180s }
Invoke-Step 'Wait for runner pool' { kubectl rollout status deployment/runner-pool -n crafty --timeout=180s }
Invoke-Step 'Wait for shuttle-proxy' { kubectl rollout status deployment/shuttle-proxy -n crafty --timeout=180s }

Start-PortForward -Service 'redis-service' -LocalPort 6379 -TargetPort 6379
Start-PortForward -Service 'shuttle-proxy-svc' -LocalPort 8090 -TargetPort 80

Write-Host "`nDocker Compose services and Kubernetes resources are ready. Keep the port-forward windows open and Spring Boot running separately. Add '127.0.0.1 project-3.app.domain.com' to the Windows hosts file as Administrator, then POST /api/projects/3/deploy in Postman."
