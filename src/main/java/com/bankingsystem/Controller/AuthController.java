package com.bankingsystem.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingsystem.dto.LoginRequestDTO;
import com.bankingsystem.dto.RegisterUserDTO;
import com.bankingsystem.dto.VerifyOtpDTO;
import com.bankingsystem.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterUserDTO dto) {
        log.info("Register API called for email: {}", dto.getEmail());
        ApiResponse<?> response = authService.register(dto);
        log.info("Register API response: status={}, message={}", response.getStatusCode(), response.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequestDTO dto) {
        log.info("Login API called for email: {}", dto.getEmail());
        ApiResponse<String> response = authService.login(dto);
        log.info("Login API response: status={}, message={}", response.getStatusCode(), response.getMessage());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestBody VerifyOtpDTO dto) {
        ApiResponse<?> response = authService.verifyOtp(dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
