package com.devpass.domain.stack.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devpass.domain.stack.converter.StackConverter;
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
@RequestMapping("/api/stacks")
@Tag(name = "기술스택 API")
public class StackController {

	private final StackService stackService;

	@Operation(summary = "기술스택 조회")
	@GetMapping
	public ApiResponse<StackListResponseDTO> getStacks(
		@AuthUser Long userId) {
		List<Stack> stacks = stackService.getAllStacks(userId);
		List<StackStatusResponseDTO> stackDTOs = StackConverter.toStatusResponseDTOList(stacks);
		StackListResponseDTO responseDTO = new StackListResponseDTO(stackDTOs);
		return ApiResponse.of(SuccessCode.OK, responseDTO);
	}
}
