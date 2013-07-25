package com.smolnij.domain;

public class Client {
	
	private int carAge; //years
	private int clientAge;
	private int driverExperience;
	private CarColor carColor;
	private boolean sportCar;
	private String carMake;
	private double carPrice;
	
	public int getCarAge() {
		return carAge;
	}
	public void setCarAge(int carAge) {
		this.carAge = carAge;
	}
	public int getClientAge() {
		return clientAge;
	}
	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}
	public int getDriverExperience() {
		return driverExperience;
	}
	public void setDriverExperience(int driverExperience) {
		this.driverExperience = driverExperience;
	}
	public CarColor getCarColor() {
		return carColor;
	}
	public void setCarColor(CarColor carColor) {
		this.carColor = carColor;
	}
	public boolean isSportCar() {
		return sportCar;
	}
	public void setSportCar(boolean sportCar) {
		this.sportCar = sportCar;
	}
	public String getCarMake() {
		return carMake;
	}
	public void setCarMake(String carMake) {
		this.carMake = carMake;
	}
	public double getCarPrice() {
		return carPrice;
	}
	public void setCarPrice(double carPrice) {
		this.carPrice = carPrice;
	}
}