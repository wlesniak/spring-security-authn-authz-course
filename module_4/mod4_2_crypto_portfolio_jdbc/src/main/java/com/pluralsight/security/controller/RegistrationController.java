package com.pluralsight.security.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pluralsight.security.entity.CryptoUser;
import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.model.UserDto;
import com.pluralsight.security.repository.PortfolioRepository;
import com.pluralsight.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

	private final UserRepository repository;
	private final PortfolioRepository portfolioRepository;
	private final PasswordEncoder encoder;
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user",new UserDto());
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		CryptoUser cryptUser = new CryptoUser(user.getUsername(), user.getFirstname(), user.getLastname(),
				user.getEmail(), encoder.encode(user.getPassword()));
		repository.save(cryptUser);
		portfolioRepository.save(new Portfolio(user.getUsername(), new ArrayList<>()));
		return "redirect:register?success";
	}
	
}
