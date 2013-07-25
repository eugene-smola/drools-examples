package com.smolnij.util;

import com.smolnij.domain.CarColor;
import com.smolnij.domain.Client;

public class ClientFactory {
	
	public static Client createTrustworthyClient() {
		Client client = new Client();
		client.setCarAge(1);
		client.setCarColor(CarColor.YELLOW);
		client.setCarMake("TOYOTA");
		client.setCarPrice(25000);
		client.setClientAge(37);
		client.setDriverExperience(8);
		client.setSportCar(false);
		return client;
	}
	
	public static Client createUnreliableClient() {
		Client client = new Client();
		client.setCarAge(0);
		client.setCarColor(CarColor.BLACK);
		client.setCarMake("FORD");
		client.setCarPrice(35000);
		client.setClientAge(20);
		client.setDriverExperience(1);
		client.setSportCar(true);
		return client;
	}
	
	

}