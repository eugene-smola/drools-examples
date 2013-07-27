package com.smolnij.main;

import static com.smolnij.util.ClientFactory.createTrustworthyClient;
import static com.smolnij.util.ClientFactory.createUnreliableClient;
import static com.smolnij.util.PrintUtils.getNicePriceQuietly;

import com.smolnij.calculator.InsuranceCalculator;
import com.smolnij.calculator.InsuranceCalculatorHardcode;
import com.smolnij.domain.Client;

/**
 * Example of one of the cases when you ought to change the approach.
 * 
 */
public class MainHardcode {
	public static void main(String[] args) {
		System.out.println("Please find below your result:");

		Client trustworthyClient = createTrustworthyClient();
		Client unreliableClient = createUnreliableClient();

		InsuranceCalculator calcHardcode = new InsuranceCalculatorHardcode();

		System.out.println("[Hardcode] Trustworthy client insurance price: "
				+ getNicePriceQuietly(trustworthyClient, calcHardcode));
		System.out.println("[Hardcode] Unreliable client insurance price: "
				+ getNicePriceQuietly(unreliableClient, calcHardcode));
	}
}