package com.samplejwtauth.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.samplejwtauth.security.model.AuthenticationRequest;
import com.samplejwtauth.security.model.AuthenticationResponse;
import com.samplejwtauth.security.model.RegisterRequest;
import com.samplejwtauth.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
	private final AuthenticationService authenticationService ;

	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(authenticationService.register(request));
		
	}
	
	@RequestMapping(value = "/authenticate",method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request ){
		
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
}

