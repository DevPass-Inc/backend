package com.devpass.backend.domain.internship.repository;

import com.devpass.backend.domain.internship.entity.Internship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
}