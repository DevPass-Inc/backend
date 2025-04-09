package com.devpass.domain.company.exception;

import com.devpass.global.payload.apicode.ErrorCode;
import com.devpass.global.payload.error.exception.EntityNotFoundException;

public class CompanyNotFoundException extends EntityNotFoundException {

    public CompanyNotFoundException() { super(ErrorCode.COMPANY_NOT_FOUND); }
}
