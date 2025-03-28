package com.devpass.backend.domain.stack.controller;

import com.devpass.backend.domain.stack.converter.StackConverter;
import com.devpass.backend.domain.stack.dto.request.StackAddRequest;
import com.devpass.backend.domain.stack.dto.response.StackListResponseDTO;
import com.devpass.backend.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.backend.domain.stack.entity.Stack;
import com.devpass.backend.domain.stack.service.StackService;
import com.devpass.backend.global.common.response.CustomResponse;
import com.devpass.backend.global.result.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/developments/stacks")
public class StackController {

    private final StackService stackService;

    @PostMapping("/{devExperience_id}")
    public CustomResponse<List<Stack>> addStacks(
            @PathVariable("devExperience_id") Long devExperienceId,
            @RequestBody StackAddRequest request) {
        List<Stack> stacks = stackService.addStacks(devExperienceId, request);
        return CustomResponse.of(ResultCode.CREATED, stacks);
    }

    @GetMapping
    public CustomResponse<StackListResponseDTO> getStacks() {
        List<Stack> stacks = stackService.getAllStacks();
        List<StackStatusResponseDTO> stackDTOs = StackConverter.toStatusResponseDTOList(stacks);
        StackListResponseDTO responseDTO = new StackListResponseDTO(stackDTOs);
        return CustomResponse.of(ResultCode.OK, responseDTO);
    }
}
