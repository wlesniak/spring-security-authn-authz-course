package com.pluralsight.security.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.security.entity.CryptoUser;
import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.service.AdminService;
import com.pluralsight.security.service.PortfolioQueryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PortfolioApiController {
	
	private final PortfolioQueryService portfolioService;
	private final AdminService adminService;
	
	@GetMapping("/api/users")
	public List<CryptoUser> getUsers() {
		return this.adminService.getAllUsers();
	}	
	
	@GetMapping("/api/portfolios/{id}")
	public PortfolioPositionsDto getPortfolio(@PathVariable("id") String id) {
		return this.portfolioService.getPortfolioPositions(id);
	}
	
	@GetMapping("/api/portfolios/ids")
	public List<String> getPortfolioIds() {
		return this.portfolioService.getPortfolioIds();
	}
	
	
	@GetMapping("/api/users/{username}/portfolio")
	public PortfolioPositionsDto getPortfolioForUser(@PathVariable("username") String username ) {
		return this.portfolioService.getPortfolioPositionsForUser(username);
	}
	
	
}





//@PreAuthorize("hasRole('ADMIN')")