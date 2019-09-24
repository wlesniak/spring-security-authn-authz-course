package com.pluralsight.security.service;

import com.pluralsight.security.model.UserDto;

public interface UserRegistrationService {

	void createUser(UserDto user);
	
}
