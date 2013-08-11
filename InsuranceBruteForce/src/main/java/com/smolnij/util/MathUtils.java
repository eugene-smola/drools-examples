package com.smolnij.util;

import java.math.BigDecimal;

public class MathUtils {
	
	public static boolean isBetweenInclusive(int start, int end, int number) {
		return end > start ? number >= start && number <= end : number >= end && number <= start;
	}

	public static BigDecimal subtractPercent(BigDecimal price, double amount) {
		return price.subtract(getPercentageValue(price, amount));
	}
	
	public static BigDecimal addPercent(BigDecimal price, double amount) {
		return price.add(getPercentageValue(price, amount));
	}

	private static BigDecimal getPercentageValue(BigDecimal price, double amount) {
		return price.multiply(new BigDecimal(amount / 100));
	}
}