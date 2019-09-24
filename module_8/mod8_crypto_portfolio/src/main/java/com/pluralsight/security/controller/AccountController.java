package com.pluralsight.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pluralsight.security.annotations.CryptoPrincipal;
import com.pluralsight.security.exceptions.InvalidTOTPVerificationCode;
import com.pluralsight.security.model.TotpCode;
import com.pluralsight.security.service.TOTPService;
import com.pluralsight.security.userdetails.MFAUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {

	private final TOTPService totpService;
	
	@GetMapping("/account")
	public String getAccount(Model model,@AuthenticationPrincipal MFAUser user ) {
		if (user != null) { 
			model.addAttribute("totpEnabled",user.isTotpEnabled());
		} else {
			model.addAttribute("totpEnabled",true);
		}
		return "account";
	}
	
	@GetMapping("/setup-totp")
	public String getGoogleAuthenticatorQRUrl(Model model, @CryptoPrincipal MFAUser authPrincipal) {
		String username = authPrincipal.getUsername();
		boolean userHasTotpEnabled = authPrincipal.isTotpEnabled();
		if(!userHasTotpEnabled) {
			model.addAttribute("qrUrl",totpService.generateNewGoogleAuthQrUrl(username));
			model.addAttribute("codeDto", new TotpCode());
		}
		model.addAttribute("totpEnabled",userHasTotpEnabled);
		return "account";
	}	

	@PostMapping("/confirm-totp")
	public String confirmGoogleAuthenticatorSetup(Model model, @AuthenticationPrincipal MFAUser user, @ModelAttribute("codeDto") TotpCode codeDto) {
		boolean userHasTotpEnabled = user.isTotpEnabled();
		if(!userHasTotpEnabled) {
			totpService.enableTOTPForUser(user.getUsername(), Integer.valueOf(codeDto.getCode()));
			model.addAttribute("totpEnabled",true);
		}
		return "account";
	}
	
	@ExceptionHandler(InvalidTOTPVerificationCode.class)
	public String handleInvalidTOTPVerificationCode(Model model, @AuthenticationPrincipal MFAUser user) {
		boolean userHasTotpEnabled = user.isTotpEnabled();
		model.addAttribute("totpEnabled",userHasTotpEnabled);
		model.addAttribute("confirmError",true);
		return "account";
	}
	
}