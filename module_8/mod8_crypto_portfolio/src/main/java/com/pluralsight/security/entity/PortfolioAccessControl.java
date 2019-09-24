package com.pluralsight.security.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Document
@Getter
@RequiredArgsConstructor
public class PortfolioAccessControl {

	private final String username;
	private final String portfolioId;
	private final String type;
	
}
