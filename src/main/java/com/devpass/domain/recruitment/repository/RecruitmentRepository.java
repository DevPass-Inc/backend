package com.devpass.domain.recruitment.repository;

import com.devpass.domain.recruitment.entity.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    Page<Recruitment> findAll(Pageable pageable);
}
