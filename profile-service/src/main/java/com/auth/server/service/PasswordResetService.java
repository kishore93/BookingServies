package com.auth.server.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth.server.pojo.QuestionAndAnswers;
import com.auth.server.repository.ApiRepository;
import com.auth.server.util.Util;

@Service
public class PasswordResetService {

	ApiRepository repository;
	
	@Autowired
	public PasswordResetService(ApiRepository reposiory) {
		this.repository = reposiory;
	}

	public List<String> retrieveSecurityQuestions(HttpServletRequest request, HttpServletResponse response) {
		
		if(request.getHeader("USER") != null) {
			
			String user = request.getHeader("USER");
			
			if(user.trim().isEmpty()) {
				
				// TODO - common error handling
				try {
					
					response.setStatus(HttpStatus.BAD_REQUEST.value());
					response.getWriter().write("HTTP Header field - \"USER\" cannot be empty");
					response.getWriter().flush();
					response.getWriter().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				if(repository.validateUser(user)) {
					
					return repository.retrieveSecurityQuestions(user);
					
				} else {
					try {
						response.setStatus(HttpStatus.BAD_REQUEST.value());
						response.getWriter().write("user not found");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			
			try {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.getWriter().write("HTTP Header field - \"USER\" not found");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return null;
		
	}

	public void validateAnswers(HttpServletRequest request, List<QuestionAndAnswers> answers, HttpServletResponse response) {
		
		// TODO Auto-generated method stub
		if(repository.verifyAnswers(answers, request.getHeader("USER"))) {
			
			response.setStatus(HttpStatus.OK.value());
			
			String token = Util.generateToken();
			
			repository.saveVerifiedToken(request.getHeader("USER"), token);
			
			response.setHeader("VERFIED_TOKEN", token);
			
		} else {
			
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			
			try {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.getWriter().write("Couldn't verify answers");
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		
	}

	public boolean updatePassword(String user, String password, String token) {
		
		if(repository.validateVerfiedToken(user, token)) {
			repository.updatePassword(user,password);
			return true;
		}
		return false;
	}
	
	
	
}
