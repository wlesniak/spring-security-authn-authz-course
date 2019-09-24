package com.pluralsight.security.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.Verification;
import com.pluralsight.security.repository.VerificationCodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationService {
	
	private final VerificationCodeRepository repository;
		
	public String getVerifictionIdByUsername(String username) {
		Verification verification = repository.findByUsername(username);
		if(verification != null) {
			return verification.getId();
		}
		return null;
	}
	
	public String createVerification(String username) {
		if (!repository.existsByUsername(username)) {
			Verification verification = new Verification(username);
			verification = repository.save(verification);
			return verification.getId();
		}
		return getVerifictionIdByUsername(username);
	}
	
	public String getUsernameForId(String id) {
		Optional<Verification> verification = repository.findById(id);
		if(verification.isPresent()) {
			return verification.get().getUsername();
		}
		return null;
	}
	
}
