package com.pluralsight.security.service;

import java.util.List;

import com.pluralsight.security.model.ListTransactionsDto;
import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.model.SharedPortfolioDto;

public interface PortfolioQueryService {

	PortfolioPositionsDto getPortfolioPositions();
	PortfolioPositionsDto getPortfolioPositions(String id);
	ListTransactionsDto getPortfolioTransactions();
	List<String> getPortfolioIds();
	List<SharedPortfolioDto> getSharedPortfolios();
	PortfolioPositionsDto getPortfolioPositionsForUser(String username);
	
}
