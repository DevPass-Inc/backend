package com.devpass.domain.company.exception;

import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.exception.EntityNotFoundException;

public class CompanyNotFoundException extends EntityNotFoundException {

    public CompanyNotFoundException() { super(ErrorStatus.COMPANY_NOT_FOUND); }
}
