package com.smolnij.test;

import java.math.BigDecimal;

public class TestUtils {
	
	public static double PRECISION = 0.00001;
	
	public static double doubleVal(BigDecimal number) {
		return number.doubleValue();
	}
}