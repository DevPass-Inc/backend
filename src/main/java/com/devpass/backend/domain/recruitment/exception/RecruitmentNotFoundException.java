package com.devpass.backend.domain.recruitment.exception;

import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.BusinessException;

public class RecruitmentNotFoundException extends BusinessException {

    public RecruitmentNotFoundException() {
        super(ErrorCode.RECRUITMENT_NOT_FOUND);
    }
}
