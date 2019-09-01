package com.auth.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.server.pojo.QuestionAndAnswers;
import com.auth.server.pojo.UserEntity;
import com.auth.server.service.LoginService;
import com.auth.server.service.PasswordResetService;
import com.auth.server.service.SignUpService;

@RequestMapping("/api/v1/auth-service")
@RestController
@CrossOrigin
public class ProfileController {

	private LoginService loginService;
	private SignUpService signupService;
	private PasswordResetService passwordResetService;
	
	@Autowired
	public ProfileController(LoginService loginService, SignUpService signupService, PasswordResetService passwordResetService) {
		this.loginService = loginService;
		this.signupService = signupService;
		this.passwordResetService = passwordResetService;
	}
	
	@GetMapping("/login")
	public void userLogin(HttpServletRequest request, HttpServletResponse response) {
		
		loginService.authenticate(request, response);
		
	}
	
	@PostMapping("/signup")
	public void userSignUp(@RequestBody @Valid UserEntity user, HttpServletResponse response) {
		
		signupService.persistUser(user, response);
		
	}
	
	@GetMapping("/reset-password")
	public List<String> getSecurityQuestions(HttpServletRequest request, HttpServletResponse response) {
		
		return passwordResetService.retrieveSecurityQuestions(request, response);
		
	}
	
	@PostMapping("/reset-password")
	public void userPasswordReset(HttpServletRequest request, @RequestBody List<QuestionAndAnswers> answers, HttpServletResponse response) {
		
		passwordResetService.validateAnswers(request, answers, response);
		
	}
	
	@PutMapping("/reset-password")
	public void updatePassword(HttpServletRequest request, @RequestBody String password, HttpServletResponse response) {
		
		if(passwordResetService.updatePassword(request.getHeader("USER"), password, request.getHeader("VERFIED_TOKEN"))) {
			response.setStatus(HttpStatus.OK.value());
		}else {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		
	}
	
}
