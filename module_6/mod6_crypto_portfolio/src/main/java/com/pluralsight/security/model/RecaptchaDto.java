package com.pluralsight.security.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RecaptchaDto {

	private boolean success;
	private List<String> errors;
	
}
