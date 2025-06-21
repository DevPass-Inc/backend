package com.devpass.domain.devexpstack.service;

import com.devpass.domain.devexperience.entity.DevExperience;
import com.devpass.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.domain.devexpstack.entity.DevExpStack;
import com.devpass.domain.devexpstack.repository.DevExpStackRepository;
import com.devpass.domain.stack.converter.StackConverter;
import com.devpass.domain.stack.dto.request.StackAddRequestDTO;
import com.devpass.domain.stack.dto.response.StackResponseDTO;
import com.devpass.domain.stack.entity.Stack;
import com.devpass.domain.stack.repository.StackRepository;
import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.GeneralException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DevExpStackService {

    private final DevExperienceRepository devExperienceRepository;
    private final DevExpStackRepository devExpStackRepository;
    private final StackRepository stackRepository;

    @Transactional
    public List<Stack> addStacksToDevExperience(Long userId, Long devExperienceId, StackAddRequestDTO request) {
        DevExperience devExperience = devExperienceRepository.findByIdAndUserId(devExperienceId,
                userId)
            .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

        List<com.devpass.domain.stack.entity.Stack> stacks = stackRepository.findAllById(
            request.getStackIds());

        List<DevExpStack> devExpStacks = stacks.stream()
            .map(stack -> DevExpStack.builder()
                .devExperience(devExperience)
                .stack(stack)
                .build())
            .toList();

        devExpStackRepository.saveAll(devExpStacks);

        return devExpStackRepository.findStacksByDevExperienceId(userId, devExperienceId);
    }

    @Transactional
    public List<Stack> updateStacksToDevExperience(Long devExperienceId, StackAddRequestDTO request) {
        DevExperience devExperience = devExperienceRepository.findById(devExperienceId)
            .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND));

        devExpStackRepository.deleteByDevExperienceId(devExperienceId);

        List<Stack> stacks = stackRepository.findAllById(request.getStackIds());

        List<DevExpStack> devExpStacks = stacks.stream()
            .map(stack -> DevExpStack.builder()
                .devExperience(devExperience)
                .stack(stack)
                .build())
            .toList();

        devExpStackRepository.saveAll(devExpStacks);

        return stacks;
    }

    @Transactional(readOnly = true)
    public List<StackResponseDTO> getStacksByDevExperienceId(Long userId, Long devExperienceId) {
        List<Stack> stacks = devExpStackRepository.findStacksByDevExperienceId(userId, devExperienceId);
        return StackConverter.toResponseDTOList(stacks);
    }

    @Transactional
    public void deleteStacksByDevExperienceId(Long userId, Long devExperienceId, Long stackId) {
        Optional<DevExpStack> stack = devExpStackRepository.findDevExpStackByDevExperienceIdAndStackId(devExperienceId, stackId);
        if (stack.isEmpty()) {
            throw new GeneralException(ErrorStatus.NOT_FOUND);
        }
        devExpStackRepository.deleteByDevExperienceIdAndStackId(devExperienceId, stackId);
    }
}
