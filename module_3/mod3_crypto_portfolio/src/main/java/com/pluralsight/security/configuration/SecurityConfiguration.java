package com.pluralsight.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(101)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().formLogin();
	}
	
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
