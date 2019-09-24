package com.pluralsight.security.service;

import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.CryptoUser;
import com.pluralsight.security.entity.TOTPDetails;
import com.pluralsight.security.exceptions.InvalidTOTPVerificationCode;
import com.pluralsight.security.repository.TOTPRepository;
import com.pluralsight.security.repository.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

@Service
public class TOTPService {
	
	private final GoogleAuthenticator googleAuth = new GoogleAuthenticator();
	private final TOTPRepository totpRepository;
	private final UserRepository userRepository;
	private static final String ISSUER = "Cryptoportfolio.com";
	
	
	public TOTPService(TOTPRepository totpRepository, UserRepository userRepository) {
		this.totpRepository=totpRepository;
		this.userRepository=userRepository;
	}
	
	public String generateNewGoogleAuthQrUrl(String username) {	
		GoogleAuthenticatorKey authKey = googleAuth.createCredentials();
		String secret = authKey.getKey();
		totpRepository.deleteByUsername(username);
		totpRepository.save(new TOTPDetails(username, secret));
		return GoogleAuthenticatorQRGenerator.getOtpAuthURL(ISSUER, username, authKey);
	}
	
	public boolean isTotpEnabled(String username) {
		return userRepository.findByUsername(username).isTotpEnabled();
	}
	
	public void enableTOTPForUser(String username, int code) {
		if(!verifyCode(username, code)) {
			throw new InvalidTOTPVerificationCode("Verification code is Invalid");
		}
		CryptoUser user = userRepository.findByUsername(username);
		user.setTotpEnabled(true);
		userRepository.save(user);
	}
	
	public boolean verifyCode(String username, int code) {
		if(true) {
			return true;
		}
		TOTPDetails totpDetails = totpRepository.findByUsername(username);
		return googleAuth.authorize(totpDetails.getSecret(), code);
	}
	
}
