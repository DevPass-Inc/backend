package com.devpass.domain.stack.repository;

import com.devpass.domain.stack.entity.Stack;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StackRepository extends JpaRepository<Stack, Long> {
    Optional<Stack> findByName(String name);
    List<Stack> findAllByDevExperience_Id(Long devExperienceId);
}
