package com.smonij.util;

import static com.smolnij.hc.util.MathUtils.addPercent;
import static com.smolnij.hc.util.MathUtils.isBetweenInclusive;
import static com.smolnij.hc.util.MathUtils.subtractPercent;
import static com.smolnij.test.TestUtils.PRECISION;
import static com.smolnij.test.TestUtils.doubleVal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MathUtilsTest {

	@Test
	public void addPercentTest() {
		assertEquals(105d, doubleVal(addPercent(new BigDecimal(100), 5)), PRECISION);
	}
	
	@Test
	public void subtractPercentTest() {
		assertEquals(95d, doubleVal(subtractPercent(new BigDecimal(100), 5)), PRECISION);
	}

	@Test
	public void isBetweenTest() {
		assertTrue(isBetweenInclusive(0, 1, 0.001));
		assertTrue(isBetweenInclusive(0, 1, 1));
		assertTrue(isBetweenInclusive(0, 1, 0));
		
		assertFalse(isBetweenInclusive(0, 1, 1.1));
		assertFalse(isBetweenInclusive(0, 1, -1));
	}
}