package com.devpass.domain.recruitment.exception;

import com.devpass.global.error.ErrorCode;
import com.devpass.global.error.exception.EntityNotFoundException;

public class RecruitmentNotFoundException extends EntityNotFoundException {

    public RecruitmentNotFoundException() {
        super(ErrorCode.RECRUITMENT_NOT_FOUND);
    }
}
