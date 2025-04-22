package com.devpass.domain.recruitment.exception;

import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.EntityNotFoundException;

public class RecruitmentNotFoundException extends EntityNotFoundException {

    public RecruitmentNotFoundException() {
        super(ErrorStatus.RECRUITMENT_NOT_FOUND);
    }
}
