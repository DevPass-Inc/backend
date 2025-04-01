package com.devpass.backend.domain.devexperience.repository;

import com.devpass.backend.domain.devexperience.entity.DevExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevExperienceRepository extends JpaRepository<DevExperience, Long> {
}
