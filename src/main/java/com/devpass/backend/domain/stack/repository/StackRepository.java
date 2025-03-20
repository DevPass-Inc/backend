package com.devpass.backend.domain.stack.repository;

import com.devpass.backend.domain.stack.entity.Stack;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackRepository extends JpaRepository<Stack, Long> {
    Optional<Stack> findByName(String name);
}
