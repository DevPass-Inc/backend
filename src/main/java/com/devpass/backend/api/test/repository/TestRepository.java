package com.devpass.backend.api.test.repository;

import com.devpass.backend.api.test.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
