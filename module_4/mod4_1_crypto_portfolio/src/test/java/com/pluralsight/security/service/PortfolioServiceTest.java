package com.pluralsight.security.service;

import static com.pluralsight.security.entity.Type.BUY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.pluralsight.security.entity.CryptoCurrency;
import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.Transaction;
import com.pluralsight.security.entity.Type;
import com.pluralsight.security.model.CryptoCurrencyDto;
import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.model.PositionDto;
import com.pluralsight.security.repository.PortfolioRepository;

@RunWith(MockitoJUnitRunner.class)
public class PortfolioServiceTest {
	
	@Mock
	private PortfolioRepository portfolioRepostiory;
	@Mock
	private PricingService pricingService;
	@Mock
	private CurrencyQueryService currencyService;
	@InjectMocks
	private PortfolioQueryServiceNoSql portfolioService;	
	Portfolio portfolio = null;
	private List<CryptoCurrencyDto> cryptos = new ArrayList<>();
	private CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
	private CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");
	
	@Before
	public void setup() {
		cryptos.clear();
		cryptos.add(new CryptoCurrencyDto(bitcoin.getSymbol(), bitcoin.getName()));
		cryptos.add(new CryptoCurrencyDto(litecoin.getSymbol(), litecoin.getName()));
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(bitcoin, BUY, new BigDecimal("3.1"), new BigDecimal("15000.00"), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY, new BigDecimal("20.1"), new BigDecimal("200.00"), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY, new BigDecimal("200.1"), new BigDecimal("100.00"), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, Type.SELL, new BigDecimal("201.1"), new BigDecimal("150.00"), System.currentTimeMillis()));
		portfolio = new Portfolio("snakamoto", transactions);
		User principle =  mock(User.class);
		Authentication authentication = mock(Authentication.class);
		SecurityContext context = mock(SecurityContext.class);
		when(context.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(context);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principle);
		when(principle.getUsername()).thenReturn("snakamoto");
	}
	
	@Test
	public void testGetPortfolioPositions() {
		when(currencyService.getSupportedCryptoCurrencies()).thenReturn(cryptos);
		when(portfolioRepostiory.findByUsername("snakamoto")).thenReturn(portfolio);
		when(pricingService.getCurrentPriceForCrypto(Mockito.anyString())).thenReturn(BigDecimal.TEN);
		PortfolioPositionsDto repsonse = portfolioService.getPortfolioPositions();
		PositionDto position = repsonse.getPositionForCrypto(new CryptoCurrencyDto(bitcoin.getSymbol(), bitcoin.getName()));
		assertTrue(BigDecimal.valueOf(3.1).compareTo(position.getQuantity()) == 0);
		assertTrue(BigDecimal.valueOf(31).compareTo(position.getValue()) == 0);
		position = repsonse.getPositionForCrypto(new CryptoCurrencyDto(litecoin.getSymbol(), litecoin.getName()));
		assertTrue(BigDecimal.valueOf(19.1).compareTo(position.getQuantity()) == 0);
		assertTrue(BigDecimal.valueOf(191).compareTo(position.getValue()) == 0);
	}
	
	@Test
	public void testAddAndDeleteTransaction() {
		Transaction transaction = new Transaction(bitcoin, BUY, new BigDecimal("3.6"), new BigDecimal("1500.00"), System.currentTimeMillis());
		assertFalse(this.portfolio.getTransactions().contains(transaction));
		this.portfolio.addTransaction(transaction);
		assertEquals(5, portfolio.getTransactions().size());
		assertTrue(this.portfolio.getTransactions().contains(transaction));
		this.portfolio.deleteTransaction(transaction);
		assertEquals(4, portfolio.getTransactions().size());
		assertFalse(this.portfolio.getTransactions().contains(transaction));
	}
	
}
