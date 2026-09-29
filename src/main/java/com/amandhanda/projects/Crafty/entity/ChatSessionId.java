package com.amandhanda.projects.Crafty.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Embeddable
public class ChatSessionId implements Serializable {
    Long projectId;
    Long userId;
}
