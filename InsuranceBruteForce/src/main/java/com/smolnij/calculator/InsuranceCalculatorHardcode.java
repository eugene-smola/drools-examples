package com.smolnij.calculator;

import static com.smolnij.domain.CarColor.BLUE;
import static com.smolnij.domain.CarColor.RED;
import static com.smolnij.domain.CarColor.YELLOW;
import static com.smolnij.util.MathUtils.addPercent;
import static com.smolnij.util.MathUtils.isBetweenInclusive;
import static com.smolnij.util.MathUtils.subtractPercent;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smolnij.calculator.listener.FlowExecutionListener;
import com.smolnij.calculator.listener.RulesApplianceListener;
import com.smolnij.domain.CarColor;
import com.smolnij.domain.Client;
import com.smolnij.exception.UnsupportedProductException;

public class InsuranceCalculatorHardcode implements InsuranceCalculator {
	private Logger LOG = LoggerFactory.getLogger(getClass());
	private FlowExecutionListener listener;
	
	public InsuranceCalculatorHardcode() {
		listener = new RulesApplianceListener();
	}
	
	public void calcInsurancePrice(List<Client> clients) {
		for (Client c : clients) {
			try {
				calcInsurancePrice(c);
			} catch (UnsupportedProductException e) {
				LOG.error("Was not able to calculate insurance price for {}. Reason: {}", c.getName(), e.getMessage());
				c.setInsurancePrice(ERROR_PRICE);
			}
		}
	}
	private void calcInsurancePrice(Client c) throws UnsupportedProductException {
		BigDecimal price = BASE_COST;
		notifyListener("Base Cost");
		int carAge = c.getCarAge();
		String carMake = c.getCarMake();
		CarColor carColor = c.getCarColor();
		boolean sportCar = c.isSportCar();
		int clientAge = c.getClientAge();
		int driverExperience = c.getDriverExperience();

		// Sportcar is a risk for insurance company
		if (sportCar) {
			price = addPercent(price, 31);
			notifyListener("Sportcar Fee");
		}
		// Sportcar is a risk, but Porshe owners can afford it
		if (sportCar && "PORSCHE".equalsIgnoreCase(carMake)) {
			price = addPercent(price, 18);
			notifyListener("Sportcar porsche fee");
		}
		// summer bargain for TOYOTA cars
		if ("TOYOTA".equalsIgnoreCase(carMake) && c.isNewCar()) {
			price = subtractPercent(price, 5);
			notifyListener("New toyota bargain");
		}
		// Company statistic shows that red and yellow cars are less likely to
		// get in the road accident
		if (carColor == RED || carColor == YELLOW) {
			price = subtractPercent(price, 11);
			notifyListener("Safest colours");
		} else if (carColor == BLUE) {
			price = subtractPercent(price, 7);
			notifyListener("Safe colour");
		}
		if (isBetweenInclusive(0, 1, carAge)) {
			price = addPercent(price, 3);
			notifyListener("New Car");
		} else if (isBetweenInclusive(2, 3, carAge)) {
			price = addPercent(price, 1);
			notifyListener("Mid Car");
		} else if (isBetweenInclusive(4, MAX_CAR_AGE, carAge)) {
			price = addPercent(price, 5);
			notifyListener("Old Car");
		} else {
			if (carAge > MAX_CAR_AGE) {
				notifyListener("Oldest Car");
				throw new UnsupportedProductException("No offer for car older than "
						+ MAX_CAR_AGE + " years");
			}
		}
		if (isBetweenInclusive(0, THRUSTWORTHY_AGE-1, clientAge )) {
			price = addPercent(price, 12);
			notifyListener("Too young");
		}
		if (isBetweenInclusive(THRUSTWORTHY_AGE, VERY_ELDERLY_AGE-1, clientAge)) {
			price = subtractPercent(price, 15);
			notifyListener("Mid-Age");
		}
		if (clientAge >= VERY_ELDERLY_AGE) {
			price = addPercent(price, 9);
			notifyListener("Elderly");
		}
		
		if (driverExperience > 3) {
			notifyListener("Driver Experience");
			price = subtractPercent(price, 20);
		}
		
		//TODO Add new rules here for Silver Card holders! 
		//TODO Reduce final price for Gold Card holders! 
		//TODO Reduce price for ABS+ESP equipped cars! 
		//TODO Add new rules based on driver's accident history!
		//TODO WHATEVER BUSINESS WANT EACH NEW MONTH!
		//Think of how to test that and maintain the tests
		//Redeploy, Redeploy, Redeploy, Redeploy 
		c.setInsurancePrice(price);
	}
	private void notifyListener(String ruleName) {
		listener.ruleApplied(ruleName);
	}

	@Override
	public List<String> getFiredRules() {
		return listener.getAppliedRules();
	}
}