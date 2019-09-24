package com.pluralsight.security.entity;

import static com.pluralsight.security.entity.Type.BUY;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class PortfolioTest {

	@Test
	public void testAddTransaction() {
		//Given
		Portfolio portfolio = new Portfolio("snakamoto", new ArrayList<>(1));
		CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
		//When
		portfolio.addTransaction(new Transaction(bitcoin, BUY, new BigDecimal(3.1), new BigDecimal(15000.00), System.currentTimeMillis()));
		//Then
		assertEquals(1, portfolio.getTransactions().size());
	}

	@Test
	public void testGetAllTransactionsForCoin() {
		//Given
		List<Transaction> transactions = new ArrayList<Transaction>();
		CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
		CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");
		transactions.add(new Transaction(bitcoin, BUY, new BigDecimal(3.1), new BigDecimal(15000.00), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY, new BigDecimal(20.1), new BigDecimal(13000.00), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY, new BigDecimal(200.1), new BigDecimal(130000.00), System.currentTimeMillis()));
		//When
		Portfolio portfolio = new Portfolio("snakamoto", transactions);
		//Then
		assertEquals(2, portfolio.getTransactionsForCoin(litecoin.getSymbol()).size());
		assertEquals(1, portfolio.getTransactionsForCoin(bitcoin.getSymbol()).size());
	}
	
	@Test(expected=NullPointerException.class)
	public void testUsernameIsMandatory() {
		Portfolio portfolio = new Portfolio(null, Collections.EMPTY_LIST);
	}
	
}
