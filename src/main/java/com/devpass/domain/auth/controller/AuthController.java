package com.devpass.domain.auth.controller;

import com.devpass.domain.auth.dto.request.SignupRequestDTO;
import com.devpass.domain.auth.dto.response.SignupResponseDTO;
import com.devpass.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<SignupResponseDTO> signup(@RequestBody SignupRequestDTO request) {
		return ResponseEntity.ok(authService.signup(request));
	}
}