package com.pluralsight.security;

import static com.pluralsight.security.entity.Type.BUY;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import com.pluralsight.security.entity.CryptoCurrency;
import com.pluralsight.security.entity.CryptoUser;
import com.pluralsight.security.entity.Portfolio;
import com.pluralsight.security.entity.SupportQuery;
import com.pluralsight.security.entity.Transaction;
import com.pluralsight.security.repository.CryptoCurrencyRepository;
import com.pluralsight.security.repository.PortfolioRepository;
import com.pluralsight.security.repository.SupportQueryRepository;
import com.pluralsight.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class WebApplication {

	private final UserRepository userRespository;
	private final PortfolioRepository portfolioRepository;
	private final CryptoCurrencyRepository cryptoRepository;
	private final SupportQueryRepository supportRepository;
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void intializeUserData() {		
		List<Transaction> transactions = new ArrayList<Transaction>();
		CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
		CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");
		cryptoRepository.save(bitcoin);
		cryptoRepository.save(litecoin);
		transactions.add(new Transaction(bitcoin, BUY,new BigDecimal(3.1), new BigDecimal(15000.00), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY,new BigDecimal(20.1), new BigDecimal(13000.00), System.currentTimeMillis()));
		transactions.add(new Transaction(litecoin, BUY,new BigDecimal(200.1), new BigDecimal(130000.00), System.currentTimeMillis()));
		userRespository.save(new CryptoUser("snakamoto", "Satoshi", "Nakamoto", "snakamoto@gmail.com","{noop}pa55word"));
		userRespository.save(new CryptoUser("bob", "Bob", "Jones", "bjones@gmail.com","{noop}password"));
		userRespository.save(new CryptoUser("user", "user", "Default", "user@gmail.com","{noop}password"));
		portfolioRepository.save(new Portfolio("snakamoto", transactions));
		portfolioRepository.save(new Portfolio("bob", new ArrayList<>()));
		portfolioRepository.save(new Portfolio("user", new ArrayList<>()));
		SupportQuery query = new SupportQuery("snakamoto", "Cannot remove transaction");
		query.addPost("Please Help","snakamoto");
		supportRepository.save(query);		
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
	
	@Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setRedirectPort(8443);
        return connector;
    }
	
}