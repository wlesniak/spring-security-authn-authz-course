package com.pluralsight.security.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Document
@RequiredArgsConstructor
@Getter
public class TOTPDetails {
	@Id
	private String id;
	@Indexed(unique=true)
	private final String username;
	private final String secret;
	
}
