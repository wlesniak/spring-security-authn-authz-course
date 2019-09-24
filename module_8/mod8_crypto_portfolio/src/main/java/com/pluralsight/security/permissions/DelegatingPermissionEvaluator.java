package com.pluralsight.security.permissions;

import java.io.Serializable;
import java.util.Map;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DelegatingPermissionEvaluator implements PermissionEvaluator{

	private final Map<String, PermissionEvaluator> evaluatorMap;
	
	public void addEvaluator(String targetName, PermissionEvaluator evaluator) {
		this.evaluatorMap.put(targetName, evaluator);
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		PermissionEvaluator evaluator = this.evaluatorMap.get(targetDomainObject.getClass().getName());
		if(evaluator == null) {
			return false;
		}
		return evaluator.hasPermission(authentication, targetDomainObject, permission);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		PermissionEvaluator evaluator = this.evaluatorMap.get(targetType);
		if(evaluator == null) {
			return false;
		}
		return evaluator.hasPermission(authentication, targetId, targetType, permission);
	}

}
