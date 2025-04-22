package com.devpass.domain.devexperience.repository;

import java.util.List;
import java.util.Optional;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DevExperienceRepository extends JpaRepository<DevExperience, Long> {
	Optional<DevExperience> findByIdAndUserId(Long id, Long userId);

	List<DevExperience> findAllByUser(User user);

	Optional<DevExperience> findByIdAndUser(Long id, User user);
}
