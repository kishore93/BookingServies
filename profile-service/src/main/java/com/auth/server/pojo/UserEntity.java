package com.auth.server.pojo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserEntity {

	@NotNull
	@Pattern(regexp = "[A-Za-z0-9]{4,8}")
	private String username;

	@NotNull
	@Size(min = 8)
	private String password;

	@NotNull
	@Size(min = 2)
	private List<QuestionAndAnswers> securityQuestions;

	public UserEntity() {

	}

	public UserEntity(String username, String password, List<QuestionAndAnswers> securityQuestions) {

		this.username = username;
		this.password = password;
		this.securityQuestions = securityQuestions;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<QuestionAndAnswers> getSecurityQuestions() {
		return securityQuestions;
	}

	public void setSecurityQuestions(List<QuestionAndAnswers> securityQuestions) {
		this.securityQuestions = securityQuestions;
	}

}