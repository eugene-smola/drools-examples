package com.smolnij.main;

import static com.smolnij.util.ClientFactory.createTrustworthyClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.smolnij.calculator.InsuranceCalculator;
import com.smolnij.domain.Client;
import com.smolnij.exception.UnsupportedProductException;
import com.smolnij.util.ClientFactory;

/**
 * Example of one of the cases when you ought to change the approach.
 * 
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("Please find below your result:");
		
		Client trustworthyClient = createTrustworthyClient();
		Client unreliableClient = ClientFactory.createUnreliableClient();
		
		System.out.println("Trustworthy client insurance price: " + getNicePriceQuietly(trustworthyClient));
		System.out.println("Unreliable client insurance price: " + getNicePriceQuietly(unreliableClient));
	}

	private static BigDecimal getNicePriceQuietly(Client client) {
		BigDecimal clientInsurancePrice = BigDecimal.ZERO;
		try {
			clientInsurancePrice = InsuranceCalculator.calcInsurancePrice(client);
		} catch (UnsupportedProductException e) {
			System.out.println("Sorry, no current products because ".concat(e.getMessage()));
			return BigDecimal.ZERO;
		}
		return clientInsurancePrice.setScale(2, RoundingMode.CEILING);
	}
}