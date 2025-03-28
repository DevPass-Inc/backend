package com.devpass.backend.domain.stack.service;

import com.devpass.backend.domain.devexperience.entity.DevExperience;
import com.devpass.backend.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.backend.domain.stack.converter.StackConverter;
import com.devpass.backend.domain.stack.dto.request.StackAddRequest;
import com.devpass.backend.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.backend.domain.stack.entity.Stack;
import com.devpass.backend.domain.stack.repository.StackRepository;
import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StackService {

    private final StackRepository stackRepository;
    private final DevExperienceRepository devExperienceRepository; // DevExperience 조회용

    @Transactional
    public List<Stack> addStacks(Long devExperienceId, StackAddRequest request) {
        DevExperience devExperience = devExperienceRepository.findById(devExperienceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        List<Stack> savedStacks = new ArrayList<>();

        for (String stackName : request.getStacks()) {
            Optional<Stack> existing = stackRepository.findByName(stackName);
            if(existing.isEmpty()){
                Stack stack = Stack.builder()
                        .name(stackName)
                        .devExperience(devExperience)  // 연관관계 설정
                        .build();
                savedStacks.add(stackRepository.save(stack));
            }
        }
        return savedStacks;
    }

    @Transactional(readOnly = true)
    public List<Stack> getAllStacks() {
        return stackRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<StackStatusResponseDTO> getStacksByDevExperienceId(Long devExperienceId) {
        List<Stack> stacks = stackRepository.findAllByDevExperience_Id(devExperienceId);
        return StackConverter.toStatusResponseDTOList(stacks);
    }
}
