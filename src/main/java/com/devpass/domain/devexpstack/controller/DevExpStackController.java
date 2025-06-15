package com.devpass.domain.devexpstack.controller;

import com.devpass.domain.devexpstack.service.DevExpStackService;
import com.devpass.domain.stack.converter.StackConverter;
import com.devpass.domain.stack.dto.request.StackAddRequestDTO;
import com.devpass.domain.stack.dto.response.StackListResponseDTO;
import com.devpass.domain.stack.dto.response.StackResponseDTO;
import com.devpass.domain.stack.entity.Stack;
import com.devpass.global.annotation.AuthUser;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/stacks")
@Tag(name = "개발경험-기술스택 API")
public class DevExpStackController {
    private final DevExpStackService devExpStackService;

    @Operation(summary = "개발경험에 기술스택 추가")
    @PostMapping("/{devExperienceId}")
    public ApiResponse<StackListResponseDTO> addStacks(
        @AuthUser Long userId,
        @PathVariable("devExperienceId") Long devExperienceId,
        @RequestBody StackAddRequestDTO request) {
        List<Stack> stacks = devExpStackService.addStacksToDevExperience(userId, devExperienceId, request);
        List<StackResponseDTO> stackDTOs = StackConverter.toResponseDTOList(stacks);
        StackListResponseDTO responseDTO = new StackListResponseDTO(stackDTOs);
        return ApiResponse.of(SuccessCode.CREATED, responseDTO);
    }

    @Operation(summary = "기술스택 수정")
    @PutMapping("/{devExperienceId}")
    public ApiResponse<StackListResponseDTO> updateStacks(
        @PathVariable("devExperienceId") Long devExperienceId,
        @RequestBody StackAddRequestDTO request) {
        List<Stack> updatedStacks = devExpStackService.updateStacksToDevExperience(devExperienceId, request);
        List<StackResponseDTO> stackDTOs = StackConverter.toResponseDTOList(updatedStacks);
        StackListResponseDTO responseDTO = new StackListResponseDTO(stackDTOs);
        return ApiResponse.of(SuccessCode.OK, responseDTO);
    }

    @Operation(summary = "기술스택 삭제")
    @DeleteMapping("/{devExperienceId}/{stackId}")
    public ApiResponse<Void> deleteStacksByDevExperienceId(
        @AuthUser Long userId,
        @PathVariable("devExperienceId") Long devExperienceId,
        @PathVariable("stackId") Long stackId) {
        devExpStackService.deleteStacksByDevExperienceId(userId, devExperienceId, stackId);
        return ApiResponse.of(SuccessCode.OK);
    }

    @Operation(summary = "개발경험에 등록된 기술스택 조회")
    @GetMapping("/{devExperienceId}")
    public ApiResponse<StackListResponseDTO> getStacksByDevExperienceId(
        @AuthUser Long userId,
        @PathVariable("devExperienceId") Long devExperienceId) {

        List<StackResponseDTO> stackDTOs = devExpStackService.getStacksByDevExperienceId(userId, devExperienceId);
        StackListResponseDTO responseDTO = new StackListResponseDTO(stackDTOs);
        return ApiResponse.of(SuccessCode.OK, responseDTO);
    }
}
