package com.pluralsight.security.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@Order(99)
public class H2SecConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
         .authorizeRequests().antMatchers("/h2-console/**").permitAll();
		http.csrf().disable();
		http.headers().frameOptions().disable();	}
	
}











//.cacheControl().disable()









//@Configuration
//@Order(2)
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {		
//		http
//		.authorizeRequests()
//			.anyRequest().authenticated()
//			.and()
//		.httpBasic().and().logout();
//	}
//	
//
//}
