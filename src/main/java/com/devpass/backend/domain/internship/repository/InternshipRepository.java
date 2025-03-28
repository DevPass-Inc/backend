package com.devpass.backend.domain.internship.repository;

import com.devpass.backend.domain.internship.entity.Internship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findAllByDevExperience_Id(Long devExperienceId);
}