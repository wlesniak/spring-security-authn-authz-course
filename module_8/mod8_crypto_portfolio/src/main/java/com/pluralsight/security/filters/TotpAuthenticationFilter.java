package com.pluralsight.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.pluralsight.security.model.Authorities;
import com.pluralsight.security.service.TOTPService;

@Component
public class TotpAuthenticationFilter extends GenericFilterBean {
	
	private final TOTPService totpService;
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private String onSuccessUrl = "/portfolio";
	private String onfailureUrl = "/totp-login-error";
	
	public TotpAuthenticationFilter(TOTPService totpService) {
		this.totpService=totpService;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String code = obtainCode(request);
		if(!requiresTotpAuthentication(authentication) || code == null) {
			chain.doFilter(request, response);
			return;
		}
		if(codeIsValid(authentication.getName(),code)) {
			Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
			authorities.remove(Authorities.TOTP_AUTH_AUTHORITY);
			authorities.add(Authorities.ROLE_USER);
			authentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),buildAuthorities(authorities));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			redirectStrategy.sendRedirect((HttpServletRequest)request, (HttpServletResponse)response, onSuccessUrl);
		} else {
			redirectStrategy.sendRedirect((HttpServletRequest)request, (HttpServletResponse)response, onfailureUrl);
		}
	}

	private boolean requiresTotpAuthentication(Authentication authentication) {
		if (authentication == null) {
			return false;
		}
		Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		boolean hasTotpAutheority = authorities.contains(Authorities.TOTP_AUTH_AUTHORITY);
		return hasTotpAutheority && authentication.isAuthenticated();
	}
	
	private boolean codeIsValid(String username, String code) {
		return code != null && totpService.verifyCode(username, Integer.valueOf(code));
	}
	
	private String obtainCode(ServletRequest request) {
		return request.getParameter("totp_code");
	}
	
	private List<GrantedAuthority> buildAuthorities(Collection<String> authorities) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);
		for(String authority : authorities) {
			authList.add(new SimpleGrantedAuthority(authority));
		}
		return authList;
	}
	
}
