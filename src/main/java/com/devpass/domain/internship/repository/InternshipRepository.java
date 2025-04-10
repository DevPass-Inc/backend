package com.devpass.domain.internship.repository;

import com.devpass.domain.internship.entity.Internship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findAllByDevExperience_Id(Long devExperienceId);
}