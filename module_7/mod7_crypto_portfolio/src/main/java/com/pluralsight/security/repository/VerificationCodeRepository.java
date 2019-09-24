package com.pluralsight.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pluralsight.security.entity.Verification;

public interface VerificationCodeRepository extends MongoRepository<Verification, String>{
	
	Verification findByUsername(String username);
	boolean existsByUsername(String username);
}
