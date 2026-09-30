package com.amandhanda.projects.Crafty;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.amandhanda.projects.Crafty.entity.Project;
import com.amandhanda.projects.Crafty.entity.ProjectMember;
import com.amandhanda.projects.Crafty.entity.ProjectMemberId;
import com.amandhanda.projects.Crafty.entity.User;
import com.amandhanda.projects.Crafty.enums.ProjectRole;
import com.amandhanda.projects.Crafty.repository.ProjectMemberRepository;
import com.amandhanda.projects.Crafty.repository.ProjectRepository;
import com.amandhanda.projects.Crafty.repository.UserRepository;

@SpringBootTest
class CraftyApplicationTests {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectMemberRepository projectMemberRepository;

	@Autowired
	private UserRepository userRepository;

	static {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void projectQueriesReturnMembershipRole() {
		User user = userRepository.saveAndFlush(User.builder()
				.username("project-query-test@example.com")
				.password("test-password")
				.build());
		Project project = projectRepository.saveAndFlush(Project.builder().name("Repository query test").build());
		projectMemberRepository.saveAndFlush(ProjectMember.builder()
				.id(new ProjectMemberId(user.getId(), project.getId()))
				.user(user)
				.project(project)
				.role(ProjectRole.OWNER)
				.build());

		var projects = projectRepository.findAllAccessibleByUser(user.getId());
		var projectWithRole = projectRepository.findAccessibleProjectByIdWithRole(project.getId(), user.getId());

		assertEquals(1, projects.size());
		assertEquals(project.getId(), projects.getFirst().getProject().getId());
		assertEquals(ProjectRole.OWNER, projects.getFirst().getRole());
		assertTrue(projectWithRole.isPresent());
		assertEquals(ProjectRole.OWNER, projectWithRole.get().getRole());
	}

}
