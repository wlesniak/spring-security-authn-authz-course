package com.pluralsight.security.event;

import org.springframework.context.ApplicationEvent;

import com.pluralsight.security.entity.CryptoUser;

import lombok.Getter;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {

	private static final long serialVersionUID = -4113549487933175429L;
	private final CryptoUser user;
	
	public UserRegistrationEvent(CryptoUser user) {
		super(user);
		this.user=user;
		
	}

}
