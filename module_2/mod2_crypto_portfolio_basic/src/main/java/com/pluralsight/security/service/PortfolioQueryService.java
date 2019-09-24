package com.pluralsight.security.service;

import com.pluralsight.security.model.ListTransactionsDto;
import com.pluralsight.security.model.PortfolioPositionsDto;

public interface PortfolioQueryService {

	PortfolioPositionsDto getPortfolioPositions();
	ListTransactionsDto getPortfolioTransactions();
	
}
