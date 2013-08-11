package com.smolnij.main;

import static com.smolnij.util.ClientFactory.createTrustworthyClient;
import static com.smolnij.util.ClientFactory.createUnreliableClient;
import static com.smolnij.util.PrintUtils.printResults;

import java.util.ArrayList;
import java.util.List;

import com.smolnij.calculator.InsuranceCalculator;
import com.smolnij.calculator.InsuranceCalculatorHardcode;
import com.smolnij.calculator.drools.InsuranceCalculatorDroolsRules;
import com.smolnij.calculator.drools.InsuranceCalculatorDroolsTables;
import com.smolnij.domain.Client;
import com.smolnij.util.PrintUtils;

/**
 * Example of one of the cases when you ought to change the approach.
 * 
 */
public class MainAll {
	public static void main(String[] args) {
		System.out.println("Please find below your result:");

		List<Client> clients = createClientList();
		
		List<InsuranceCalculator> calcList = createCalcList();
		
		for (InsuranceCalculator calc : calcList) {
			calc.calcInsurancePrice(clients);
			printResults(clients, calc.getClass());
			PrintUtils.printRules(calc.getFiredRules(), calc.getClass());
		}
	}

	private static List<InsuranceCalculator> createCalcList() {
		List<InsuranceCalculator> calcList = new ArrayList<InsuranceCalculator>(3);
		
		calcList.add(new InsuranceCalculatorHardcode());
		calcList.add(new InsuranceCalculatorDroolsRules());
		calcList.add(new InsuranceCalculatorDroolsTables());
		return calcList;
	}

	private static List<Client> createClientList() {
		List<Client> clients = new ArrayList<Client>(2);
		clients.add(createTrustworthyClient());
		clients.add(createUnreliableClient());
		return clients;
	}
}