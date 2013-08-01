package com.smolnij.calculator;

import java.math.BigDecimal;
import java.util.List;

import com.smolnij.domain.Client;

public interface InsuranceCalculator {
	BigDecimal BASE_COST = new BigDecimal(1000);
	int MAX_CAR_AGE = 10;
	int THRUSTWORTHY_AGE = 21;
	int VERY_ELDERLY_AGE = 70;
	BigDecimal ERROR_PRICE = new BigDecimal(-1);
	void calcInsurancePrice(List<Client> clients);
}