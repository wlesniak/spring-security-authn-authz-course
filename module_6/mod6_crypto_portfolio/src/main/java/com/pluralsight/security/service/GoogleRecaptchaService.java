package com.pluralsight.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.security.model.RecaptchaDto;

@Service
public class GoogleRecaptchaService {

	private static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify"
			+ "?secret={secret}&remoteip={remoteip}&response={response}";
	private final RestTemplate restTemplate;
	private final String secretKey = "6Ldsu3UUAAAAALswEquthX1D7mX4EHzaNSpThCMt";
	
	public GoogleRecaptchaService(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
	}
	
	public RecaptchaDto verify(String ip, String recaptchaResponse) {
		Map<String, String> request = new HashMap<>();
		request.put("remoteip", ip);
		request.put("secret", secretKey);
		request.put("response", recaptchaResponse);
		ResponseEntity<Map> response = restTemplate.getForEntity(VERIFY_URL, Map.class, request);
		Map<String, Object> body = response.getBody();
		boolean success = (Boolean)body.get("success");
		RecaptchaDto recaptchaDto = new RecaptchaDto();
		recaptchaDto.setSuccess(success);
		if(!success) {
		      recaptchaDto.setErrors((List)body.get("error-codes"));
		}
		return recaptchaDto;
	}
	
}
