package com.bankingsystem.service;

import com.bankingsystem.Controller.ApiResponse;
import com.bankingsystem.dto.LoginRequestDTO;
import com.bankingsystem.dto.RegisterUserDTO;
import com.bankingsystem.dto.VerifyOtpDTO;


public interface AuthService {
    ApiResponse<?> register(RegisterUserDTO registerUserDTO);
    ApiResponse<String> login(LoginRequestDTO loginRequestDTO);
    ApiResponse<?> verifyOtp(VerifyOtpDTO dto); 
}
