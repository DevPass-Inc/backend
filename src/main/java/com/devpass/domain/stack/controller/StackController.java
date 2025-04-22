package com.devpass.domain.stack.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.stack.converter.StackConverter;
import com.devpass.domain.stack.dto.request.StackAddRequestDTO;
import com.devpass.domain.stack.dto.response.StackListResponseDTO;
import com.devpass.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.domain.stack.entity.Stack;
import com.devpass.domain.stack.service.StackService;
import com.devpass.global.annotation.AuthUser;
import com.devpass.global.payload.ApiResponse;
import com.devpass.global.payload.apicode.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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
		@AuthUser Long userId,
		@PathVariable("devExperience_id") Long devExperienceId,
		@RequestBody StackAddRequestDTO request) {
		List<Stack> stacks = stackService.addStacks(userId, devExperienceId, request);
		List<StackStatusResponseDTO> stackDTOs = StackConverter.toStatusResponseDTOList(stacks);
		StackListResponseDTO responseDTO = new StackListResponseDTO(stackDTOs);
		return ApiResponse.of(SuccessCode.CREATED, responseDTO);
	}

	@GetMapping
	public ApiResponse<StackListResponseDTO> getStacks(@AuthUser Long userId) {
		List<Stack> stacks = stackService.getAllStacks(userId);
		List<StackStatusResponseDTO> stackDTOs = StackConverter.toStatusResponseDTOList(stacks);
		StackListResponseDTO responseDTO = new StackListResponseDTO(stackDTOs);
		return ApiResponse.of(SuccessCode.OK, responseDTO);
	}
}
