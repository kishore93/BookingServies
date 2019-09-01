package com.auth.server.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.auth.server.pojo.QuestionAndAnswers;
import com.auth.server.pojo.UserEntity;
import com.auth.server.util.Util;

@Repository
@SuppressWarnings("unchecked")
public class ApiRepository {

	MongoTemplate mongoTemplate;

	@Autowired
	public ApiRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public boolean verifyCredentials(String username, String password) {

		Query query = new Query();

		// Filter Criteria
		query.addCriteria(Criteria.where("username").is(username));
		query.addCriteria(Criteria.where("password").is(password));

		return (mongoTemplate.findOne(query, Map.class, "users") != null ? true : false);

	}

	public Date getExpirationTime(String username, String token) {

		Query query = new Query();

		// Filter Criteria
		query.addCriteria(Criteria.where("username").is(username));
		query.addCriteria(Criteria.where("authToken").is(token));

		// Field Summary
		query.fields().include("expirationTime");
		query.fields().exclude("_id");

		Map<String, Object> document = mongoTemplate.findOne(query, Map.class, "auth_tokens");

		if (document != null && !document.isEmpty()) {
			return (Date) document.get("expirationTime");
		}
		return null;
	}

	public String saveAuthToken(String username) {

		Map<String, Object> authRecord = new HashMap<>();

		Query query = new Query();

		// Filter Condition
		query.addCriteria(Criteria.where("username").is(username));

		Map<String, Object> document = mongoTemplate.findOne(query, Map.class, "auth_tokens");

		if (document != null && !document.isEmpty()) {
			authRecord.put("_id", document.get("_id"));
		}

		boolean flag = true;

		String authToken = null;

		while (flag) {
			authToken = Util.generateToken();
			if (tokenChecker(authToken)) {
				flag = false;
			}
		}

		authRecord.put("username", username);
		authRecord.put("authToken", authToken);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// Setting the token expiration time to 24 hours
		calendar.add(Calendar.MINUTE, 1);

		authRecord.put("expirationTime", Util.utcDate(calendar.getTime()));

		mongoTemplate.save(authRecord, "auth_tokens");

		return authToken;
	}

	private boolean tokenChecker(String authToken) {
		Query query = new Query();
		query.addCriteria(Criteria.where("authToken").is(authToken));
		
		if (mongoTemplate.findOne(query, Map.class, "auth_tokens") == null) {
			return true;
		}
		return false;
	}

	public boolean persistUser(UserEntity user) {
		
		mongoTemplate.save(user, "users");
		
		return true;
	}

	public boolean validateUser(String user) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(user));
		
		if(mongoTemplate.findOne(query, Map.class, "users") != null) {
			return true;
		}
		return false;
	}

	public List<String> retrieveSecurityQuestions(String user) {
				
		Query query = new Query(Criteria.where("username").is(user));
		query.fields().include("securityQuestions");
		query.fields().exclude("_id");
		
		List<Map<String, String>> dbQuestions = (List<Map<String,String>>) mongoTemplate.findOne(query, Map.class, "users").get("securityQuestions");
		
		List<String> questions = new ArrayList<>();
		
		for (Map<String, String> dbQuestion : dbQuestions) {
			questions.add(dbQuestion.get("question"));
		}
		
		return questions;
		
	}

	public boolean verifyAnswers(List<QuestionAndAnswers> answers, String username) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		query.fields().exclude("_id");
		
		UserEntity user = mongoTemplate.findOne(query, UserEntity.class, "users");
		
		return Util.compare(answers, user.getSecurityQuestions());
				
	}

	public void saveVerifiedToken(String username, String token) {
		
		Map<String,String> record = new HashMap<>();
		record.put("username", username);
		record.put("token", token);
		
		mongoTemplate.save(record, "verified_tokens");
		
	}

	public boolean validateVerfiedToken(String username, String token) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		query.addCriteria(Criteria.where("token").is(token));
		
		Map<String,String> verifiedToken = mongoTemplate.findOne(query, Map.class, "verified_tokens");
		if(verifiedToken != null) {
			mongoTemplate.remove(verifiedToken, "verified_tokens");
			return true;
		}
		return false;
	}

	public void updatePassword(String username, String password) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		
		Map<String,Object> user = mongoTemplate.findOne(query, Map.class, "users");
		user.put("password", password);
		
		mongoTemplate.save(user, "users");
		
	}

}
