package com.devpass.domain.internship.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devpass.domain.internship.entity.Internship;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
	List<Internship> findAllByDevExperience_Id(Long devExperienceId);
}