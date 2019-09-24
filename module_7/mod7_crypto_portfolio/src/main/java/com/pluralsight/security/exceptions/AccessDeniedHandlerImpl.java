package com.pluralsight.security.exceptions;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.pluralsight.security.model.Authorities;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

	private final RedirectStrategy redirectStrategy;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(requiresTotpAuthentication(authentication)) {
			redirectStrategy.sendRedirect(request, response, "/totp-login");
		}
		else {
			redirectStrategy.sendRedirect(request, response, "/error");
		}
		
	}

	private boolean requiresTotpAuthentication(Authentication authentication) {
		if(authentication == null || !authentication.isAuthenticated()) {
			return false;
		}
		Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		return authorities.contains(Authorities.TOTP_AUTH_AUTHORITY);
	}
	
}
