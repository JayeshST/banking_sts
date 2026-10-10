package com.bankingsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bankingsystem.Controller.ApiResponse;
import com.bankingsystem.dto.LoginRequestDTO;
import com.bankingsystem.dto.RegisterUserDTO;
import com.bankingsystem.dto.VerifyOtpDTO;
import com.bankingsystem.entity.RoleEntity;
import com.bankingsystem.entity.RoleEnum;
import com.bankingsystem.entity.UserEntity;
import com.bankingsystem.repository.RoleRepository;
import com.bankingsystem.repository.UserRepository;
import com.bankingsystem.util.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public ApiResponse<?> register(RegisterUserDTO dto) {
		log.info("Register request received for email: {}", dto.getEmail());

		// Password mismatch check
		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
			log.warn("Password mismatch for email: {}", dto.getEmail());
			return new ApiResponse<>("Passwords do not match", 400, null);
		}

		// Email unique check
		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			log.error("Duplicate email registration attempt: {}", dto.getEmail());
			return new ApiResponse<>("Email already registered", 400, null);
		}

		// Role check
		RoleEntity role = roleRepository.findByRoleName(
				dto.getRole() != null && dto.getRole().equalsIgnoreCase("ADMIN") ? RoleEnum.ADMIN : RoleEnum.USER)
				.orElseThrow(() -> {
					log.error("Role not found: {}", dto.getRole());
					return new RuntimeException("Role not found");
				});

		// Save user
		UserEntity user = new UserEntity();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setMobile(dto.getMobile()); // duplicate allowed
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(role);

		userRepository.save(user);
		log.info("User registered successfully with email: {}", dto.getEmail());

		return new ApiResponse<>("User registered successfully", 201, null);
	}

	@Override
	public ApiResponse<String> login(LoginRequestDTO dto) {
		log.info("Login attempt for email: {}", dto.getEmail());

		UserEntity user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> {
			log.error("Invalid email login attempt: {}", dto.getEmail());
			return new RuntimeException("Invalid email or password");
		});

		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			log.error("Invalid password attempt for email: {}", dto.getEmail());
			throw new RuntimeException("Invalid email or password");
		}

		String token = jwtUtil.generateToken(user.getEmail(), user.getRole().getRoleName().name());
		log.info("Login successful for email: {}", dto.getEmail());

		return new ApiResponse<>("Login successful", 200, token);
	}

	@Override
	public ApiResponse<?> verifyOtp(VerifyOtpDTO dto) {
		log.info("OTP verification attempt for email: {}", dto.getEmail());

		// By default OTP = 1234
		String defaultOtp = "1234";

		if (!defaultOtp.equals(dto.getOtp())) {
			log.error("Invalid OTP attempt for email: {}", dto.getEmail());
			return new ApiResponse<>("Invalid OTP", 400, null);
		}

		// If OTP matches
		log.info("OTP verified successfully for email: {}", dto.getEmail());
		return new ApiResponse<>("OTP verified successfully", 200, null);
	}

}
