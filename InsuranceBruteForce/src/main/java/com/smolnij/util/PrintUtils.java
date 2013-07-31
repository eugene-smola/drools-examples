package com.smolnij.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.smolnij.calculator.InsuranceCalculator;
import com.smolnij.domain.Client;
import com.smolnij.exception.UnsupportedProductException;

public class PrintUtils {
	
	public static BigDecimal getNicePriceQuietly(Client client, InsuranceCalculator calculator) {
		try {
			calculator.calcInsurancePrice(client);
		} catch (UnsupportedProductException e) {
			System.out.println("Sorry, no current products because ".concat(e.getMessage()));
			return BigDecimal.ZERO;
		}
		return client.getInsurancePrice().setScale(2, RoundingMode.CEILING);
	}
}