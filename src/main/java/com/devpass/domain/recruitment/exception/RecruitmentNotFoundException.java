package com.devpass.domain.recruitment.exception;

import com.devpass.global.payload.apicode.ErrorCode;
import com.devpass.global.payload.error.exception.EntityNotFoundException;

public class RecruitmentNotFoundException extends EntityNotFoundException {

    public RecruitmentNotFoundException() {
        super(ErrorCode.RECRUITMENT_NOT_FOUND);
    }
}
