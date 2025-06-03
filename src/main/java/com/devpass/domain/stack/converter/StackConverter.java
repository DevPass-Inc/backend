package com.devpass.domain.stack.converter;

import com.devpass.domain.stack.dto.response.StackResponseDTO;
import com.devpass.domain.stack.entity.Stack;

import java.util.List;
import java.util.stream.Collectors;

public class StackConverter {

    public static StackResponseDTO toResponseDTO(Stack stack) {
        return new StackResponseDTO(stack.getId(), stack.getName());
    }

    public static List<StackResponseDTO> toResponseDTOList(List<Stack> stacks) {
        return stacks.stream()
            .map(StackConverter::toResponseDTO)
            .collect(Collectors.toList());
    }
}
