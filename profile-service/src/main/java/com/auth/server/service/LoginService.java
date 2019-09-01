package com.auth.server.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth.server.repository.ApiRepository;

@Service
public class LoginService {

	ApiRepository repository;

	@Autowired
	public LoginService(ApiRepository repository) {
		this.repository = repository;
	}

	public void authenticate(HttpServletRequest request, HttpServletResponse response) {

		if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
			if(request.getHeader(HttpHeaders.AUTHORIZATION).startsWith("Basic ")) {
				String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

				// Breakdown username and password from HTTPHeader
				String authString = new String(Base64.getDecoder().decode(authHeader.replaceFirst("Basic ", "")));
				String username = authString.split(":", 2)[0];
				String password = authString.split(":", 2)[1];
								
				// Verify against database
				if (repository.verifyCredentials(username, password)) {

					// Save the token into database
					String AUTH_TOKEN = repository.saveAuthToken(username);

					// Set HTTP call status to 200 OK
					response.setStatus(HttpStatus.OK.value());
					response.setHeader("AUTH_TOKEN", AUTH_TOKEN);

				} else {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
				}
			} else {
				try {

					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.getWriter().write("Unsupported Authentication type - only Basic Auth supported");
					response.getWriter().flush();
					response.getWriter().close();

				} catch (IOException e) {
					e.printStackTrace();
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				}

			}
			
		} else if (request.getHeader("AUTH_TOKEN") != null && request.getHeader("USER") != null) {

			// Read header information
			String username = request.getHeader("USER");
			String token = request.getHeader("AUTH_TOKEN");

			// Verify against database and check token expiration
			Date expiryTime = repository.getExpirationTime(username, token);

			if(expiryTime != null) {
				if (new Date().after(expiryTime)) {

					try {

						response.setStatus(HttpStatus.UNAUTHORIZED.value());
						response.getWriter().write("Token Expired");
						response.getWriter().flush();
						response.getWriter().close();

					} catch (IOException e) {
						e.printStackTrace();
						response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
					}
				} else {

					response.setStatus(HttpStatus.OK.value());
				}
			}
			else {
				
				try {

					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					response.getWriter().write("Invalid Username or API TOKEN");
					response.getWriter().flush();
					response.getWriter().close();

				} catch (IOException e) {
					e.printStackTrace();
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				}
				
			}
			
		} else {

			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}

	}

}
