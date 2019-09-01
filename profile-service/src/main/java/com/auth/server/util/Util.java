package com.auth.server.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.auth.server.pojo.QuestionAndAnswers;

public class Util {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String generateToken() {

		// Length of token to be generated
		int count = 18;

		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}

		return builder.toString();
	}

	public static Date utcDate(Date date) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return dateFormat.parse(dateFormat.format(date));
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date;
	}

	public static boolean compare(List<QuestionAndAnswers> A, List<QuestionAndAnswers> B) {

		// check size first
		if (A.size() == B.size()) {

			// if the Maps are abstracted into a POJO you could implement Comparator on that
			// POJO. In the meantime you can sort manually
			// sort A
			Collections.sort(A, new Comparator<QuestionAndAnswers>() {
				
				@Override
				public int compare(final QuestionAndAnswers o1, final QuestionAndAnswers o2) {
					return o1.getQuestion().compareTo(o2.getQuestion());
				}
				
			});

			// sort B
			Collections.sort(B, new Comparator<QuestionAndAnswers>() {
				
				@Override
				public int compare(final QuestionAndAnswers o1, final QuestionAndAnswers o2) {
					return o1.getQuestion().compareTo(o2.getQuestion());
				}
				
			});

			Map<String, String> aMap = new HashMap<>();
			Map<String, String> bMap = new HashMap<>();
			
			for (int i = 0; i < A.size(); i++) {
				// get map from A & B
				aMap.put(A.get(i).getQuestion(), A.get(i).getAnswer());
				aMap.put(B.get(i).getQuestion(), B.get(i).getAnswer());
				
				// check equality of Maps
				if (!aMap.equals(bMap)) {
					return false;
				}
			}
		} else {
			// Data size mismatch
			return false;
		}

		// if we get here then all was good
		return true;
	}

}
