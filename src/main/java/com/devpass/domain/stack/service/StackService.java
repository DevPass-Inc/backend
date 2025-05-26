package com.devpass.domain.stack.service;

import com.devpass.domain.devexpstack.repository.DevExpStackRepository;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devpass.domain.devexperience.repository.DevExperienceRepository;
import com.devpass.domain.stack.entity.Stack;
import com.devpass.domain.stack.repository.StackRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StackService {

	private final StackRepository stackRepository;

	@Transactional(readOnly = true)
	public List<Stack> getAllStacks(Long userId) {
		return stackRepository.findAll();
	}
}
