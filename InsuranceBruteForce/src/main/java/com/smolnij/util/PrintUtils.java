package com.smolnij.util;

import java.math.RoundingMode;
import java.util.List;

import com.smolnij.domain.Client;

public class PrintUtils {

	public static void printResults(List<Client> clients, Class<?> clazz) {
		for (Client c : clients) {
			System.out.println("[" + clazz.getName() + "] " + c.getName() + " insuranse price: "
					+ c.getInsurancePrice().setScale(2, RoundingMode.HALF_UP));
		}
	}
}