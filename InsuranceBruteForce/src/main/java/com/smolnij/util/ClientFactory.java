package com.smolnij.util;

import com.smolnij.domain.CarColor;
import com.smolnij.domain.Client;

public class ClientFactory {
	
	public static Client createTrustworthyClient() {
		Client client = new Client("Trustwothy client");
		client.setCarAge(1);
		client.setCarColor(CarColor.YELLOW);
		client.setCarMake("TOYOTA");
		client.setClientAge(37);
		client.setDriverExperience(8);
		client.setSportCar(false);
		return client;
	}
	
	public static Client createUnreliableClient() {
		Client client = new Client("Unreliable client");
		client.setCarAge(0);
		client.setCarColor(CarColor.BLACK);
		client.setCarMake("FORD");
		client.setClientAge(20);
		client.setDriverExperience(1);
		client.setSportCar(true);
		return client;
	}
	
	

}