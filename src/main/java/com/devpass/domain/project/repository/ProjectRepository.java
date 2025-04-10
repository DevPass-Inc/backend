package com.devpass.domain.project.repository;

import com.devpass.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByDevExperience_Id(Long devExperienceId);
}
