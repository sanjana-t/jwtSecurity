package com.samplejwtauth.security.service;

import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samplejwtauth.security.config.JwtService;
import com.samplejwtauth.security.model.AuthenticationRequest;
import com.samplejwtauth.security.model.AuthenticationResponse;
import com.samplejwtauth.security.model.RegisterRequest;
import com.samplejwtauth.security.model.Role;
import com.samplejwtauth.security.repository.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Service;
///security/src/main/java/com/samplejwtauth.security.model.User.java
import com.samplejwtauth.security.model.User;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	@Autowired
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private final JwtService jwtService;
	
	 private final AuthenticationManager authenticationManager;
	 
	public AuthenticationResponse register(RegisterRequest request) {
		// TODO Auto-generated method stub
//		User user = new User();
//		user.setFirstname(request.getFirstname());
		
		
		var user = User.builder()
			        .firstname(request.getFirstname())
			        .lastname(request.getLastname())
			        .email(request.getEmail())
			        .password(passwordEncoder.encode(request.getPassword()))
			        .role(Role.USER)
			        .build();
		
		userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
		
		
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		// TODO Auto-generated method stub
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
		
		var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
	
	

	
}
