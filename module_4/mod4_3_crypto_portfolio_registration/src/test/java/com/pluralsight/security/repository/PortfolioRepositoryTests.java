package com.pluralsight.security.repository;

import static com.pluralsight.security.entity.Type.BUY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pluralsight.security.entity.CryptoCurrency;
import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.Transaction;

@RunWith(SpringRunner.class)
@DataMongoTest
public class PortfolioRepositoryTests {

	@Autowired
	private PortfolioRepository portfolioRepository;
	@Autowired
	private CryptoCurrencyRepository cryptoRepository;

	@Before
	public void setup() {
		CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
		CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");
		cryptoRepository.deleteAll();
		portfolioRepository.deleteAll();
		cryptoRepository.save(bitcoin);
		cryptoRepository.save(litecoin);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(bitcoin, BUY,new BigDecimal(3.1), new BigDecimal(15000.00), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY,new BigDecimal(20.1), new BigDecimal(13000.00), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY,new BigDecimal(200.1), new BigDecimal(13000.00), System.currentTimeMillis()));
		portfolioRepository.save(new Portfolio("snakamoto", transactions));
	}
	
	@Test
	public void findPortfolioByUsername() {
		//Given
		CryptoCurrency bitcoin = cryptoRepository.findBySymbol("BTC");
		CryptoCurrency litecoin = cryptoRepository.findBySymbol("LTC");
		//When
		Portfolio portfolio = portfolioRepository.findByUsername("snakamoto");
		//Then
		assertEquals(3, portfolio.getTransactions().size());
		List<Transaction> bitcoinTransaction = portfolio.getTransactionsForCoin(bitcoin.getSymbol());
		List<Transaction> litecoinTransaction = portfolio.getTransactionsForCoin(litecoin.getSymbol());
		assertEquals(1,bitcoinTransaction.size());
		assertEquals(2,litecoinTransaction.size());
		assertEquals(new BigDecimal(3.1), bitcoinTransaction.get(0).getQuantity());
	}
	
	@Test
	public void testDeleteTransactionFromPortfolio() {
		Portfolio portfolio = portfolioRepository.findByUsername("snakamoto");
		Transaction randomTransaction = portfolio.getTransactions().get(0);
		assertTrue(portfolio.getTransactions().contains(randomTransaction));
		portfolio.deleteTransaction(randomTransaction);
		assertEquals(2, portfolio.getTransactions().size());
		portfolioRepository.save(portfolio);
		portfolio = portfolioRepository.findByUsername("snakamoto");
		assertEquals(2, portfolio.getTransactions().size());
		assertFalse(portfolio.getTransactions().contains(randomTransaction));
	}
	
}
