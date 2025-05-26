package com.devpass.domain.stack.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StackAddRequestDTO {
    @Schema(description = "기술 스택")
    private List<Long> stackIds;
}
