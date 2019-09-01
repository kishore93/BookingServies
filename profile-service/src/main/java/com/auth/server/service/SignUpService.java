package com.auth.server.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth.server.pojo.UserEntity;
import com.auth.server.repository.ApiRepository;

@Service
public class SignUpService {

	ApiRepository repository;
	
	@Autowired
	public SignUpService(ApiRepository repository) {
		this.repository = repository;
	}
	
	public void persistUser(UserEntity user, HttpServletResponse response) {
		
		if(repository.persistUser(user)) {
			response.setStatus(HttpStatus.OK.value());
		}
		
	}

	
}
