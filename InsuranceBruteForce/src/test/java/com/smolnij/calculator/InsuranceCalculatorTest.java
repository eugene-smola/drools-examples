package com.smolnij.calculator;

import static com.smolnij.calculator.InsuranceCalculator.BASE_COST;
import static com.smolnij.calculator.InsuranceCalculator.MAX_CAR_AGE;
import static com.smolnij.calculator.InsuranceCalculator.THRUSTWORTHY_AGE;
import static com.smolnij.calculator.InsuranceCalculator.VERY_ELDERLY_AGE;
import static com.smolnij.util.MathUtils.addPercent;
import static com.smolnij.util.MathUtils.subtractPercent;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.smolnij.domain.CarColor;
import com.smolnij.domain.Client;
import com.smolnij.exception.UnsupportedProductException;

@RunWith(Theories.class)
public class InsuranceCalculatorTest {

	public static @DataPoints
	InsuranceCalculator[] candidates = { new InsuranceCalculatorHardcode() };
	//, new InsuranceCalculatorDrools() };
	private Client client;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		client = new Client();
		client.setCarAge(-1);
		client.setClientAge(-1);
	}

	@Theory
	public void reenterability(InsuranceCalculator calc) throws Exception {
		calc.calcInsurancePrice(client);
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(BASE_COST);
	}
	
	@Theory
	public void basePrice(InsuranceCalculator calc) throws Exception {
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(BASE_COST);
	}

	@Theory
	public void sportCar(InsuranceCalculator calc) throws Exception {
		client.setSportCar(true);
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(getSportcarFee());
	}

	@Theory
	public void sportCarPorsche(InsuranceCalculator calc) throws Exception {
		client.setSportCar(true);
		client.setCarMake("PorschE");
		calc.calcInsurancePrice(client);
		
		assertPriceEqualsTo(addPercent(getSportcarFee(), 18));
	}
	
	@Theory
	public void newCar (InsuranceCalculator calc) throws Exception {
		client.setCarAge(0);
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(getNewCarFee());
		
	}
	
	@Theory
	public void toyotaBargain (InsuranceCalculator calc) throws Exception {
		client.setCarMake("ToyotA");
		client.setCarAge(0);
		calc.calcInsurancePrice(client);
		
		assertPriceEqualsTo(subtractPercent(getNewCarFee(), 5));
	}
	
	@Theory
	public void carColor (InsuranceCalculator calc) throws Exception {
		assertColorFeeApplied(calc, CarColor.RED, 11);
		assertColorFeeApplied(calc, CarColor.YELLOW, 11);
		assertColorFeeApplied(calc, CarColor.BLUE, 7);
	}
	@Theory
	public void newCarFee (InsuranceCalculator calc) throws Exception {
		assertNewCarFeeApplied(calc, 0);
		assertNewCarFeeApplied(calc, 1);
	}
	
	@Theory
	public void carAgeFee (InsuranceCalculator calc) throws Exception {
		int amountMiddle = 1;
		assertCarAgeFeeApplied(calc, amountMiddle, 2);
		assertCarAgeFeeApplied(calc, amountMiddle, 3);
		
		int amountOld = 5;
		assertCarAgeFeeApplied(calc, amountOld, 4);
		assertCarAgeFeeApplied(calc, amountOld, 5);
		assertCarAgeFeeApplied(calc, amountOld, MAX_CAR_AGE);
	}
	@Theory
	public void carAgeVeryOld (InsuranceCalculator calc) throws Exception {
		thrown.expect(UnsupportedProductException.class);
		client.setCarAge(MAX_CAR_AGE + 1);
		calc.calcInsurancePrice(client);
	}
	
	@Theory
	public void clienAge (InsuranceCalculator calc) throws Exception {
		
		int clientAge = 15;
		BigDecimal fee = addToBaseCost(12);
		
		assertClientAgeFeeApplied(calc, clientAge, fee);
		assertClientAgeFeeApplied(calc, THRUSTWORTHY_AGE, subtractPercent(BASE_COST, 15));
		assertClientAgeFeeApplied(calc, VERY_ELDERLY_AGE, addToBaseCost(9));
	}
	@Theory
	public void driverExperience (InsuranceCalculator calc) throws Exception {
		client.setDriverExperience(2);
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(BASE_COST);
		
		client.setDriverExperience(4);
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(subtractPercent(BASE_COST, 20));
	}
	
	private BigDecimal addToBaseCost(int amount) {
		return addPercent(BASE_COST, amount);
	}

	private void assertClientAgeFeeApplied(InsuranceCalculator calc, int clientAge, BigDecimal fee)
			throws UnsupportedProductException {
		client.setClientAge(clientAge);
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(fee);
	}

	private void assertCarAgeFeeApplied(InsuranceCalculator calc, int amount, double carAge)
			throws UnsupportedProductException {
		client.setCarAge((int) Math.floor(carAge));
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(addToBaseCost(amount));
	}

	private void assertNewCarFeeApplied(InsuranceCalculator calc, int carAge)
			throws UnsupportedProductException {
		client.setCarAge(carAge);
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(getNewCarFee());
	}
	
	private void assertColorFeeApplied(InsuranceCalculator calc, CarColor colorRed, int amountRed)
			throws UnsupportedProductException {
		client.setCarColor(colorRed);
		calc.calcInsurancePrice(client);
		assertPriceEqualsTo(subtractPercent(BASE_COST, amountRed));
	}

	private void assertPriceEqualsTo(BigDecimal expected) {
		assertEquals(expected, client.getInsurancePrice());
	}

	private BigDecimal getSportcarFee() {
		return addToBaseCost(31);
	}
	private BigDecimal getNewCarFee() {
		return addToBaseCost(3);
	}
}