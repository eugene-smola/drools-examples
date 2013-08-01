package com.smolnij.main;

import static com.smolnij.util.ClientFactory.createTrustworthyClient;
import static com.smolnij.util.ClientFactory.createUnreliableClient;
import static com.smolnij.util.PrintUtils.printResults;

import java.util.ArrayList;
import java.util.List;

import com.smolnij.calculator.InsuranceCalculator;
import com.smolnij.calculator.InsuranceCalculatorDrools;
import com.smolnij.calculator.InsuranceCalculatorHardcode;
import com.smolnij.domain.Client;

/**
 * Example of one of the cases when you ought to change the approach.
 * 
 */
public class MainBoth {
	public static void main(String[] args) {
		System.out.println("Please find below your result:");

		List<Client> clients = new ArrayList<Client>(2);
		Client trustworthyClient = createTrustworthyClient();
		Client unreliableClient = createUnreliableClient();
		clients.add(trustworthyClient);
		clients.add(unreliableClient);
		InsuranceCalculator calcHardcode = new InsuranceCalculatorHardcode();
		InsuranceCalculator calcDrools = new InsuranceCalculatorDrools();
		
		
		calcHardcode.calcInsurancePrice(clients);
		printResults(clients, calcHardcode.getClass());
		
		calcDrools.calcInsurancePrice(clients);
		printResults(clients, calcDrools.getClass());
	}
}