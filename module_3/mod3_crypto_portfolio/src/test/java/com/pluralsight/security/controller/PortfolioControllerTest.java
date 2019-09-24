//package com.pluralsight.security.controller;
//
//import static org.hamcrest.CoreMatchers.containsString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBeans;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.test.context.web.ServletTestExecutionListener;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.pluralsight.security.model.AddTransactionToPortfolioDto;
//import com.pluralsight.security.model.PortfolioPositionsDto;
//import com.pluralsight.security.model.Position;
//import com.pluralsight.security.repository.CryptoCurrencyRepository;
//import com.pluralsight.security.repository.PortfolioRepository;
//import com.pluralsight.security.repository.UserRepository;
//import com.pluralsight.security.service.PortfolioService;
//import static com.pluralsight.security.TestConstants.bitcoin;
//import static com.pluralsight.security.TestConstants.litecoin;
//
//
////@RunWith(SpringRunner.class)
//@WebMvcTest(PortfolioController.class)
//@WithMockUser(username="bob",roles={"USER"})
//@MockBeans({@MockBean(UserRepository.class),
//	        @MockBean(PortfolioRepository.class),
//			@MockBean(CryptoCurrencyRepository.class)})
//public class PortfolioControllerTest {
//
//	@Autowired
//	private MockMvc mocMvc;
//	@MockBean
//	private PortfolioService portfolioService;
//	
//	public void testGetPortfolio() throws Exception {
//		List<Position> positions = new ArrayList<>();
//		positions.add(new Position(bitcoin,BigDecimal.TEN, BigDecimal.ONE));
//		positions.add(new Position(litecoin,BigDecimal.TEN, BigDecimal.ONE));
//		PortfolioPositionsDto response = new PortfolioPositionsDto("bob", "smith", positions,null);
//		when(portfolioService.getPortfolioPositions(Mockito.anyString())).thenReturn(response);
//		this.mocMvc.perform(get("/portfolio")).andExpect(content().string(containsString("<h3>Positions</h3>")));
//	}
//	
//
//	public void testAddTransactionToPortfolio() throws Exception {
//		List<Position> positions = new ArrayList<>();
//		positions.add(new Position(bitcoin,BigDecimal.TEN, BigDecimal.ONE));
//		positions.add(new Position(litecoin,BigDecimal.TEN, BigDecimal.ONE));
//		PortfolioPositionsDto response = new PortfolioPositionsDto("bob", "smith", positions,null);
//		when(portfolioService.getPortfolioPositions(Mockito.anyString())).thenReturn(response);
//		this.mocMvc.perform(post("/portfolio/transaction").requestAttr("request", new AddTransactionToPortfolioDto("BTC","10","10","BUY"))).andExpect(content().string(containsString("<h3>Positions</h3>")));
//	}
//	
//}
