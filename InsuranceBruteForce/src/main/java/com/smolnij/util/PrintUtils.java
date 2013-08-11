package com.smolnij.util;

import java.math.RoundingMode;
import java.util.List;

import com.smolnij.domain.Client;


public class PrintUtils {

	
	public static void printResults(List<Client> clients, Class<?> clazz) {
		for (Client client : clients) {
			System.out.println("[" + clazz.getName() + "] " + client.getName() + " insuranse price: "
					+ client.getInsurancePrice().setScale(2, RoundingMode.HALF_UP));
		}
	}
	public static void printRules(List<String> rules, Class<?> clazz) {
		System.out.println("[" + clazz.getName() + "] applied rules: ");
		for (String rule : rules) {
			System.out.println(rule);
		}
	}
}