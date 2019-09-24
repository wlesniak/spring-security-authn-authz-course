package com.pluralsight.security.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.permissions.DelegatingPermissionEvaluator;
import com.pluralsight.security.permissions.PortfolioPermissionEvaluator;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration{
	
	private final PortfolioPermissionEvaluator portfolioEvaluator;
	
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		Map<String, PermissionEvaluator> evaluatorMap = new HashMap<>();
		evaluatorMap.put(Portfolio.class.getName(), portfolioEvaluator);
		PermissionEvaluator delegatingEvaluator = new DelegatingPermissionEvaluator(evaluatorMap);
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(delegatingEvaluator);
		return expressionHandler;
	}
	
}
