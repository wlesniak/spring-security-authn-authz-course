package com.pluralsight.security.userdetails;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.pluralsight.security.model.Authorities;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if(requiresTotpAuthentication(authentication)) {
			redirectStrategy.sendRedirect(request, response, "/totp-login");
		} else {
			redirectStrategy.sendRedirect(request, response, "/portfolio");
		}
	}

	private boolean requiresTotpAuthentication(Authentication authentication) {
		Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		return authorities.contains(Authorities.TOTP_AUTH_AUTHORITY);
	}
	
}
