package com.devpass.backend.domain.recruitment.repository;

import com.devpass.backend.domain.recruitment.entity.Recruitment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    Optional<Recruitment> findById(Long id);
}
