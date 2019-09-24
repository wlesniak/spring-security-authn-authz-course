package com.pluralsight.security.service;

import java.math.BigDecimal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.pluralsight.security.entity.CryptoCurrency;
import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.Transaction;
import com.pluralsight.security.entity.Type;
import com.pluralsight.security.model.AddTransactionToPortfolioDto;
import com.pluralsight.security.repository.CryptoCurrencyRepository;
import com.pluralsight.security.repository.PortfolioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PortfolioCommandServiceNoSql implements PortfolioCommandService {

	private final PortfolioRepository portfolioRepostiory;
	private final CryptoCurrencyRepository currencyRepository;
	
	@Override
	public void addTransactionToPortfolio(AddTransactionToPortfolioDto request) {
		Portfolio portfolio = portfolioRepostiory.findByUsername(getUsername());
		Transaction transaction = createTransactionEntity(request);
		portfolio.addTransaction(transaction);
		portfolioRepostiory.save(portfolio);
	}
	
	@Override
	public void removeTransactionFromPortfolio(String transactionId) {
		Portfolio portfolio = portfolioRepostiory.findByUsername(getUsername());
		Transaction transacion = portfolio.getTransactionById(transactionId);
		portfolio.deleteTransaction(transacion);
		portfolioRepostiory.save(portfolio);
	}
	
	private String getUsername() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ((User)principle).getUsername();
	}
	
	private Transaction createTransactionEntity(AddTransactionToPortfolioDto request) {
		CryptoCurrency crpytoCurrency = currencyRepository.findBySymbol(request.getCryptoSymbol());
		Type type = Type.valueOf(request.getType());
		BigDecimal quantity = new BigDecimal(request.getQuantity());
		BigDecimal price = new BigDecimal(request.getPrice());
		Transaction transaction = new Transaction(crpytoCurrency, type, quantity, price,System.currentTimeMillis());
		return transaction;
	}

}
