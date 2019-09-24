package com.pluralsight.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.CryptoUser;
import com.pluralsight.security.event.UserRegistrationEvent;
import com.pluralsight.security.model.UserDto;
import com.pluralsight.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

	private final UserRepository repository;
	private final PasswordEncoder encoder;
	private final ApplicationEventPublisher eventPublisher;
	private final boolean TOTP_ENABLED = false;
	@Value(value = "${disableEmailVerification}")
	private boolean disableEmailVerification;
	
	public void registerNewUser(UserDto user) {
		CryptoUser cryptUser = new CryptoUser(
				user.getUsername(), 
				user.getFirstname(), 
				user.getLastname(),
				user.getEmail(), 
				encoder.encode(user.getPassword()),
				encoder.encode(String.valueOf(user.getSecurityPin())),
				TOTP_ENABLED
		);
		cryptUser.setVerified(disableEmailVerification);
		repository.save(cryptUser);
		eventPublisher.publishEvent(new UserRegistrationEvent(cryptUser));
	}

}
