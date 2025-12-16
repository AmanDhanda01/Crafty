package com.amandhanda.projects.Crafty.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Plan {
   
    Long id;
    String name;

    String stripePriceId;
    Integer maxProjects;
    Integer maxTokensPerDay;
    Integer maxPreviews; // maximum number of preview generations allowed

    Boolean unlimitedAi; //unlimited access to AI ignore maxTokensPerDay if true

    Boolean isActive;
}
