package com.smolnij.main;

import static com.smolnij.util.ClientFactory.createTrustworthyClient;
import static com.smolnij.util.ClientFactory.createUnreliableClient;
import static com.smolnij.util.PrintUtils.getNicePriceQuietly;

import com.smolnij.calculator.InsuranceCalculator;
import com.smolnij.calculator.InsuranceCalculatorDrools;
import com.smolnij.domain.CarColor;
import com.smolnij.domain.Client;

/**
 * Example of one of the cases when you ought to change the approach.
 */
public class MainDrools {
	public static void main(String[] args) {
		System.out.println("Please find below your result:");

		Client trustworthyClient = createTrustworthyClient();
		Client unreliableClient = createUnreliableClient();
		trustworthyClient.setCarColor(CarColor.RED);
		InsuranceCalculator calcDrools = new InsuranceCalculatorDrools();

		System.out.println("[Drools] Trustworthy client insurance price: "
				+ getNicePriceQuietly(trustworthyClient, calcDrools));
		System.out.println("[Drools] Unreliable client insurance price: "
				+ getNicePriceQuietly(unreliableClient, calcDrools));
	}
}