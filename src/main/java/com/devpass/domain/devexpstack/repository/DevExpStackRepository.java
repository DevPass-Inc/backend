package com.devpass.domain.devexpstack.repository;

import com.devpass.domain.devexpstack.entity.DevExpStack;
import com.devpass.domain.stack.entity.Stack;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DevExpStackRepository extends JpaRepository<DevExpStack, Long> {
    void deleteByDevExperienceId(Long devExperienceId);

    @Query("SELECT d.stack FROM DevExpStack d WHERE d.devExperience.id = :devExperienceId AND d.devExperience.user.id = :userId")
    List<Stack> findStacksByDevExperienceId(Long userId, Long devExperienceId);
    Optional<DevExpStack> findDevExpStackByDevExperienceIdAndStackId(Long devExperienceId, Long stackId);

    void deleteByDevExperienceIdAndStackId(Long devExperienceId, Long stackId);
}
