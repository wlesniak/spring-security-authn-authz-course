package com.pluralsight.security.hacking;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptWorkFactor {

	public static final void main(String[] args) {
		String password = "Pa$$word@123";
		for (int i = 10; i < 19; i++) {
			long startTime = System.currentTimeMillis();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(i);
			System.out.println(encoder.encode(password));
			System.out.println("Strength: "+i+" - ("+(System.currentTimeMillis() - startTime)+" ms)");
			
		}
		
	}
	
}
