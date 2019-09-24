package com.pluralsight.security.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PortfolioRepo {

	private final JdbcTemplate template;
	
	public List<Map<String, Object>> getTransactions() {
		return template.queryForList("SELECT COIN, TYPE, QUANTITY, PRICE FROM TRANSACTIONS WHERE USERNAME = 'snakamoto'");
	}
	
}
