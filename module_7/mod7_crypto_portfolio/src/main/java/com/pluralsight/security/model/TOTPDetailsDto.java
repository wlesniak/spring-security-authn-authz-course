package com.pluralsight.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TOTPDetailsDto {

	private String qrUrl;
	
}