package com.pluralsight.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pluralsight.security.entity.CryptoUser;
import com.pluralsight.security.repository.UserRepository;
import com.pluralsight.security.service.VerificationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class VerificationController {

	private final VerificationService verificationService;
	private final UserRepository userRepository;
	
	@GetMapping("/verify/email")
	public String verifyEmail(@RequestParam String id) {
		String username = verificationService.getUsernameForId(id);
		if(username != null) {
			CryptoUser user = userRepository.findByUsername(username);
			user.setVerified(true);
			userRepository.save(user);
		}
		return "redirect:/login-verified";
	}
	
}
