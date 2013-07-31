package com.smolnij.calculator;

import java.math.BigDecimal;

import com.smolnij.domain.Client;
import com.smolnij.exception.UnsupportedProductException;

public interface InsuranceCalculator {
	BigDecimal BASE_COST = new BigDecimal(1000);
	int MAX_CAR_AGE = 10;
	int THRUSTWORTHY_AGE = 21;
	int VERY_ELDERLY_AGE = 70;
	void calcInsurancePrice(Client c) throws UnsupportedProductException;
}