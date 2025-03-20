package com.devpass.backend.domain.stack.converter;

import com.devpass.backend.domain.stack.dto.response.StackStatusResponseDTO;
import com.devpass.backend.domain.stack.entity.Stack;

import java.util.List;
import java.util.stream.Collectors;

public class StackConverter {

    // 단일 Stack 엔티티를 StackStatusResponseDTO로 변환
    public static StackStatusResponseDTO toStatusResponseDTO(Stack stack) {
        // isRequired 값은 필요에 따라 동적으로 결정할 수 있습니다.
        return new StackStatusResponseDTO(stack.getName(), false);
    }

    // List<Stack>을 List<StackStatusResponseDTO>로 변환
    public static List<StackStatusResponseDTO> toStatusResponseDTOList(List<Stack> stacks) {
        return stacks.stream()
                .map(StackConverter::toStatusResponseDTO)
                .collect(Collectors.toList());
    }
}
