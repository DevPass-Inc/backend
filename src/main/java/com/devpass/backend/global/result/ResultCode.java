package com.devpass.backend.global.result;

import com.devpass.backend.global.common.status.BaseStatus;
import com.devpass.backend.global.common.response.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCode implements BaseStatus {

    OK(HttpStatus.OK, "COMMON_200", "성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "COMMON_201", "성공적으로 생성되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON_204", "성공적으로 삭제되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .status(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}
