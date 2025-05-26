package com.devpass.domain.stack.converter;

import com.devpass.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.domain.stack.entity.Stack;

import java.util.List;
import java.util.stream.Collectors;

public class StackConverter {

    public static StackStatusResponseDTO toStatusResponseDTO(Stack stack) {
        return new StackStatusResponseDTO(stack.getId(), stack.getName());
    }

    public static List<StackStatusResponseDTO> toStatusResponseDTOList(List<Stack> stacks) {
        return stacks.stream()
            .map(StackConverter::toStatusResponseDTO)
            .collect(Collectors.toList());
    }
}
