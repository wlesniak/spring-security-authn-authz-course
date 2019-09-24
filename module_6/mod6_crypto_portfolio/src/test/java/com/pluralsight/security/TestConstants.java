package com.pluralsight.security;

import com.pluralsight.security.entity.CryptoCurrency;

public interface TestConstants {

	final CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
	final CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");

	
}
