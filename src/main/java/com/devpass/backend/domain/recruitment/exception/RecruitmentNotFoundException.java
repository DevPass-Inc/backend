package com.devpass.backend.domain.recruitment.exception;

import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.EntityNotFoundException;

public class RecruitmentNotFoundException extends EntityNotFoundException {

    public RecruitmentNotFoundException() {
        super(ErrorCode.RECRUITMENT_NOT_FOUND);
    }
}
