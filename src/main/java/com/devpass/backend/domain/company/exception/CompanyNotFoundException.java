package com.devpass.backend.domain.company.exception;

import com.devpass.backend.global.error.ErrorCode;
import com.devpass.backend.global.error.exception.EntityNotFoundException;

public class CompanyNotFoundException extends EntityNotFoundException {

    public CompanyNotFoundException() { super(ErrorCode.COMPANY_NOT_FOUND); }
}
