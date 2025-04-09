package com.devpass.domain.stack.controller;

import com.devpass.domain.stack.converter.StackConverter;
import com.devpass.domain.stack.dto.request.StackAddRequest;
import com.devpass.domain.stack.dto.response.StackListResponseDTO;
import com.devpass.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.domain.stack.entity.Stack;
import com.devpass.domain.stack.service.StackService;
import com.devpass.global.payload.wrapper.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/stacks")
@Tag(name = "Stack API", description = "개발 경험 등록 -> 기술 스택 관련 API")
public class StackController {

    private final StackService stackService;

    @Operation(
            summary = "기술스택 등록",
            description = "기술스택 등록 api"
    )
    @PostMapping("/{devExperience_id}")
    public ApiResponse<StackListResponseDTO> addStacks(
            @PathVariable("devExperience_id") Long devExperienceId,
            @RequestBody StackAddRequest request) {
        List<Stack> stacks = stackService.addStacks(devExperienceId, request);
        List<StackStatusResponseDTO> stackDTOs = StackConverter.toStatusResponseDTOList(stacks);
        StackListResponseDTO responseDTO = new StackListResponseDTO(stackDTOs);
        return ApiResponse.of(SuccessCode.CREATED, responseDTO);
    }


    @GetMapping
    public ApiResponse<StackListResponseDTO> getStacks() {
        List<Stack> stacks = stackService.getAllStacks();
        List<StackStatusResponseDTO> stackDTOs = StackConverter.toStatusResponseDTOList(stacks);
        StackListResponseDTO responseDTO = new StackListResponseDTO(stackDTOs);
        return ApiResponse.of(SuccessCode.OK, responseDTO);
    }
}
