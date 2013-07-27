package com.smolnij.calculator;

import java.math.BigDecimal;

import com.smolnij.domain.Client;
import com.smolnij.exception.UnsupportedProductException;

public interface InsuranceCalculator {
	BigDecimal calcInsurancePrice(Client c) throws UnsupportedProductException;
}