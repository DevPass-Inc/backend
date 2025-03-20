package com.devpass.backend.domain.stack.service;

import com.devpass.backend.domain.stack.dto.request.StackAddRequest;
import com.devpass.backend.domain.stack.entity.Stack;
import com.devpass.backend.domain.stack.repository.StackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StackService {

    private final StackRepository stackRepository;

    @Transactional
    public List<Stack> addStacks(StackAddRequest request) {
        List<Stack> savedStacks = new ArrayList<>();

        for (String stackName : request.getStacks()) {
            Optional<Stack> existing = stackRepository.findByName(stackName);
            if(existing.isEmpty()){
                Stack stack = Stack.builder()
                        .name(stackName)
                        .build();
                savedStacks.add(stackRepository.save(stack));
            }
        }
        return savedStacks;
    }

    @Transactional(readOnly = true)
    public List<Stack> getAllStacks() {
        return stackRepository.findAll();
    }
}
