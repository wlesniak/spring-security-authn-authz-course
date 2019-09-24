package com.pluralsight.security.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pluralsight.security.exceptions.InvalidTOTPVerificationCode;
import com.pluralsight.security.model.TotpCode;
import com.pluralsight.security.service.TOTPService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {

	private final TOTPService totpService;
	
	@GetMapping("/account")
	public String getAccount(Model model, Principal principal) {
		boolean userHasTotpEnabled = totpService.isTotpEnabled(principal.getName());		
		model.addAttribute("totpEnabled",userHasTotpEnabled);
		return "account";
	}
	
	@GetMapping("/setup-totp")
	public String getGoogleAuthenticatorQRUrl(Model model, Principal principal) {
		boolean userHasTotpEnabled = totpService.isTotpEnabled(principal.getName());
		if(!userHasTotpEnabled) {
			model.addAttribute("qrUrl",totpService.generateNewGoogleAuthQrUrl(principal.getName()));
			model.addAttribute("codeDto", new TotpCode());
		}
		model.addAttribute("totpEnabled",userHasTotpEnabled);
		return "account";
	}

	@PostMapping("/confirm-totp")
	public String confirmGoogleAuthenticatorSetup(Model model, Principal principal, @ModelAttribute("codeDto") TotpCode codeDto) {
		boolean userHasTotpEnabled = totpService.isTotpEnabled(principal.getName());
		if(!userHasTotpEnabled) {
			totpService.enableTOTPForUser(principal.getName(), Integer.valueOf(codeDto.getCode()));
			model.addAttribute("totpEnabled",true);
		}
		return "account";
	}
	
	@ExceptionHandler(InvalidTOTPVerificationCode.class)
	public String handleInvalidTOTPVerificationCode(Model model, Principal principal) {
		boolean userHasTotpEnabled = totpService.isTotpEnabled(principal.getName());
		model.addAttribute("totpEnabled",userHasTotpEnabled);
		model.addAttribute("confirmError",true);
		return "account";
	}
	
}