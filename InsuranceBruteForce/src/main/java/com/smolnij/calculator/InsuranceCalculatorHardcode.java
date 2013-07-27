package com.smolnij.calculator;

import static com.smolnij.domain.CarColor.BLUE;
import static com.smolnij.domain.CarColor.RED;
import static com.smolnij.domain.CarColor.YELLOW;

import java.math.BigDecimal;

import com.smolnij.domain.CarColor;
import com.smolnij.domain.Client;
import com.smolnij.exception.UnsupportedProductException;

public class InsuranceCalculatorHardcode implements InsuranceCalculator {

	private static final double BASE_COST = 1000;
	private static final int MAX_CAR_AGE = 10;
	private static final int THRUSTWORTHY_AGE = 21;
	private static final int VERY_ELDERLY_AGE = 70;

	public BigDecimal calcInsurancePrice(Client c) throws UnsupportedProductException {
		BigDecimal price = new BigDecimal(BASE_COST);
		int carAge = c.getCarAge();
		String carMake = c.getCarMake();
		CarColor carColor = c.getCarColor();
		boolean sportCar = c.isSportCar();
		int clientAge = c.getClientAge();
		int driverExperience = c.getDriverExperience();

		// Sportcar is a risk for insurance company
		if (sportCar) {
			price = addPercent(price, 31);
		}
		// Sportcar is a risk, but Porshe owners can afford it
		if (sportCar && "PORSCHE".equalsIgnoreCase(carMake)) {
			price = addPercent(price, 18);
		}
		// summer bargain for TOYOTA cars
		if ("TOYOTA".equals(carMake) && isNewCar(c)) {
			price = subtractPercent(price, 5);
		}
		// Company statistic shows that red and yellow cars are less likely to
		// get in the road accident
		if (carColor == RED || carColor == YELLOW) {
			price = subtractPercent(price, 11);
		} else if (carColor == BLUE) {
			price = subtractPercent(price, 7);
		}
		if (isBetween(0, 1, carAge)) {
			price = addPercent(price, 3);
		} else if (isBetween(1, 3, carAge)) {
			price = addPercent(price, 1);
		} else if (isBetween(3, 5, carAge)) {
			price = addPercent(price, 5);
		} else {
			if (carAge > MAX_CAR_AGE) {
				throw new UnsupportedProductException("No offer for car older than "
						+ MAX_CAR_AGE + "years");
			}
		}
		if (clientAge < THRUSTWORTHY_AGE) {
			price = addPercent(price, 12);
		}
		if (isBetween(THRUSTWORTHY_AGE, VERY_ELDERLY_AGE, clientAge)) {
			price = subtractPercent(price, 15);
		}
		if (clientAge > VERY_ELDERLY_AGE) {
			price = addPercent(price, 9);
		}
		
		if (driverExperience > 3) {
			price = subtractPercent(price, 20);
		}
		
		//TODO Add new rules here for Silver Card holders! 
		//TODO Reduce final price for Gold Card holders! 
		//TODO Reduce price for ABS+ESP equipped cars! 
		//TODO Add new rules based on driver's accident history!
		//TODO WHATEVER BUSINESS WANT EACH NEW MONTH!
		//Think of how to test that and maintain the tests
		//Redeploy, Redeploy, Redeploy, Redeploy 
		return price;
	}

	private static boolean isNewCar(Client c) {
		return c.getCarAge() == 0;
	}

	public static boolean isBetween(double start, double end, double number) {
		return end > start ? number >= start && number < end : number >= end && number < start;
	}

	private static BigDecimal subtractPercent(BigDecimal price, int amount) {
		return price.subtract(getPercentageAmount(price, amount));
	}

	private static BigDecimal addPercent(BigDecimal price, double amount) {
		return price.add(price.multiply(new BigDecimal(amount / 100)));
	}

	private static BigDecimal getPercentageAmount(BigDecimal price, int amount) {
		return price.multiply(new BigDecimal(amount / 100));
	}
}