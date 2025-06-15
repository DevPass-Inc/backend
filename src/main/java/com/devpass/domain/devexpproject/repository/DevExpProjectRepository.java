package com.devpass.domain.devexpproject.repository;

import com.devpass.domain.devexpproject.entity.DevExpProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevExpProjectRepository extends JpaRepository<DevExpProject, Long> {
    void deleteByProjectId(Long projectId);
}
