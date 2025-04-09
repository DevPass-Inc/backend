package com.devpass.domain.stack.service;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.domain.stack.converter.StackConverter;
import com.devpass.domain.stack.dto.request.StackAddRequest;
import com.devpass.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.domain.stack.entity.Stack;
import com.devpass.domain.stack.repository.StackRepository;
import com.devpass.global.payload.apicode.ErrorCode;
import com.devpass.global.payload.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        for (String stackName : request.getStacks()) {
            Optional<Stack> existing = stackRepository.findByName(stackName);
            if(existing.isEmpty()){
                Stack stack = Stack.builder()
                        .name(stackName)
                        .devExperience(devExperience)
                        .build();
                stackRepository.save(stack);
            }
        }
        return stackRepository.findAllByDevExperience_Id(devExperienceId);
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
