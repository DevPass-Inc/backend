package com.devpass.backend.domain.stack.converter;

import com.devpass.backend.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.backend.domain.stack.entity.Stack;

import java.util.List;
import java.util.stream.Collectors;

public class StackConverter {

    public static StackStatusResponseDTO toStatusResponseDTO(Stack stack) {
        return new StackStatusResponseDTO(stack.getName(), false);
    }

    public static List<StackStatusResponseDTO> toStatusResponseDTOList(List<Stack> stacks) {
        return stacks.stream()
                .map(StackConverter::toStatusResponseDTO)
                .collect(Collectors.toList());
    }
}
