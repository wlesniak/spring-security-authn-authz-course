package com.pluralsight.security.permissions;

import static com.pluralsight.security.util.AuthenticationUtil.getUsername;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.PortfolioAccessControl;
import com.pluralsight.security.repository.PortfolioAccessControlRepository;
import com.pluralsight.security.repository.PortfolioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PortfolioPermissionEvaluator implements PermissionEvaluator{

	private final PortfolioAccessControlRepository portfolioAccessControlRepo;
	private final PortfolioRepository portfolioRepository;
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if (targetDomainObject != null || (targetDomainObject instanceof Portfolio) ) {
			String principalUsername = getUsername();
			Portfolio portfolio = (Portfolio)targetDomainObject;
			boolean userIsPortfolioOwner = principalUsername.equals(portfolio.getUsername());
			return (userIsPortfolioOwner || userHasReadPermissionForTargetPortfolio(principalUsername, portfolio.getId()));
		}
		return false;
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		if(targetId != null) {
			User user = (User)authentication.getPrincipal();
			Portfolio portfolio = this.portfolioRepository.findById(targetId.toString()).get();
			boolean userIsPortfolioOwner = user.getUsername().equals(portfolio.getUsername());
			return (userIsPortfolioOwner || userHasReadPermissionForTargetPortfolio(user.getUsername(), targetId.toString()));
		}
		return false;
	}
	
	private boolean userHasReadPermissionForTargetPortfolio(String username, String portfolioId) {
		List<PortfolioAccessControl> userPortfolioAC = this.portfolioAccessControlRepo.findAllByUsername(username);
		if(userPortfolioAC != null) {
			return userPortfolioAC.stream().anyMatch(ac -> ac.getPortfolioId().equals(portfolioId));
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(Portfolio.class.getName());
	}
	
}
