package com.devpass.api.test.service;

import com.devpass.api.test.dto.request.TestRequest;
import com.devpass.api.test.repository.TestRepository;
import com.devpass.api.test.converter.TestConverter;
import com.devpass.api.test.domain.Test;
import com.devpass.global.payload.apicode.ErrorCode;
import com.devpass.global.payload.error.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public Test createTest(TestRequest request) {

        if (request.getValue() != 1) {
            throw new GeneralException(ErrorCode.BAD_REQUEST);
        }

        Test test = TestConverter.toTestEntity(request);
        testRepository.save(test);

        return test;
    }
}