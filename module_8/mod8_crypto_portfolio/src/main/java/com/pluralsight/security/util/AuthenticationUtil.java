package com.pluralsight.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class AuthenticationUtil {
	
	public static String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication instanceof OAuth2AuthenticationToken) {
			return authentication.getName();
		}		
		UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername();
	}
}
