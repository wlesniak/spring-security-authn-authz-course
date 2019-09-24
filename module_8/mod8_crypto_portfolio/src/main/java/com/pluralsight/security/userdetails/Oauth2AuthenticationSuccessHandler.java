package com.pluralsight.security.userdetails;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.pluralsight.security.service.PortfolioCommandService;

import lombok.RequiredArgsConstructor;

@Component("oauth2authSuccessHandler")
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private final PortfolioCommandService portfolioService;
	private final RedirectStrategy redirectStrategy;
	private final OAuth2AuthorizedClientService authorizedClientService;
	 
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if(!this.portfolioService.userHasAportfolio(authentication.getName())) {
			this.portfolioService.createNewPortfolio(authentication.getName());
			if (authentication instanceof OAuth2AuthenticationToken) {
				OAuth2AuthenticationToken oToken = (OAuth2AuthenticationToken)authentication;
				//String name = oToken.getPrincipal().getAttributes().get("name").toString();
				//String email = oToken.getPrincipal().getAttributes().get("email").toString();			}
		}
		this.redirectStrategy.sendRedirect(request, response, "/portfolio");
	}
	}
}
