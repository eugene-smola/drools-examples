package com.smolnij.calculator;

import static com.smolnij.calculator.InsuranceCalculator.BASE_COST;
import static com.smolnij.calculator.InsuranceCalculator.ERROR_PRICE;
import static com.smolnij.calculator.InsuranceCalculator.MAX_CAR_AGE;
import static com.smolnij.calculator.InsuranceCalculator.THRUSTWORTHY_AGE;
import static com.smolnij.calculator.InsuranceCalculator.VERY_ELDERLY_AGE;
import static com.smolnij.util.MathUtils.addPercent;
import static com.smolnij.util.MathUtils.subtractPercent;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
	InsuranceCalculator[] candidates = { new InsuranceCalculatorHardcode() 
//	};
	, new InsuranceCalculatorDrools() };
	private Client eligibleClient;
	private Client nonEligibleClient;
	private List<Client> clients;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		
		//client to check that rule fired
		eligibleClient = new Client("Eligible test client");
		eligibleClient.setCarAge(-1);
		eligibleClient.setClientAge(-1);
		
		//client to check that rule not fired 
		nonEligibleClient = new Client("Non-eligible test client");
		nonEligibleClient.setCarAge(-1);
		nonEligibleClient.setClientAge(-1);
		
		clients = new ArrayList<Client>(2);
		clients.add(eligibleClient);
		clients.add(nonEligibleClient);
	}

	@Theory
	public void reenterability(InsuranceCalculator calc) throws Exception {
		calc.calcInsurancePrice(clients);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(BASE_COST);
	}
	
	@Theory
	public void basePrice(InsuranceCalculator calc) throws Exception {
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(BASE_COST);
	}

	@Theory
	public void sportCar(InsuranceCalculator calc) throws Exception {
		eligibleClient.setSportCar(true);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(getSportcarFee());
		assertNonEligiblePriceEqualsToBase();
	}

	@Theory
	public void sportCarPorsche(InsuranceCalculator calc) throws Exception {
		boolean sportCar = true;
		
		eligibleClient.setSportCar(sportCar);
		eligibleClient.setCarMake("PorschE");
		
		calc.calcInsurancePrice(clients);
		
		assertEligiblePriceEqualsTo(addPercent(getSportcarFee(), 18));
		assertNonEligiblePriceEqualsToBase();
	}
	
	@Theory
	public void newCar (InsuranceCalculator calc) throws Exception {
		eligibleClient.setCarAge(0);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(getNewCarFee());
		assertNonEligiblePriceEqualsToBase();
	}
	
	@Theory
	public void toyotaBargain (InsuranceCalculator calc) throws Exception {
		eligibleClient.setCarMake("ToyotA");
		eligibleClient.setCarAge(0);
		
		calc.calcInsurancePrice(clients);
		
		assertEligiblePriceEqualsTo(subtractPercent(getNewCarFee(), 5));
		assertNonEligiblePriceEqualsToBase();
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
		
		eligibleClient.setCarAge(MAX_CAR_AGE + 1);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(ERROR_PRICE);
		assertNonEligiblePriceEqualsToBase();
	}
	
	@Theory
	public void clientAge (InsuranceCalculator calc) throws Exception {
		
		int clientAge = 15;
		BigDecimal fee = addToBaseCost(12);
		
		assertClientAgeFeeApplied(calc, clientAge, fee);
		assertClientAgeFeeApplied(calc, THRUSTWORTHY_AGE, subtractPercent(BASE_COST, 15));
		assertClientAgeFeeApplied(calc, VERY_ELDERLY_AGE, addToBaseCost(9));
	}
	@Theory
	public void driverExperience (InsuranceCalculator calc) throws Exception {
		eligibleClient.setDriverExperience(2);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(BASE_COST);
		
		eligibleClient.setDriverExperience(4);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(subtractPercent(BASE_COST, 20));
	}
	
	private BigDecimal addToBaseCost(int amount) {
		return addPercent(BASE_COST, amount);
	}

	private void assertClientAgeFeeApplied(InsuranceCalculator calc, int clientAge, BigDecimal fee)
			throws UnsupportedProductException {
		eligibleClient.setClientAge(clientAge);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(fee);
	}

	private void assertCarAgeFeeApplied(InsuranceCalculator calc, int amount, int carAge)
			throws UnsupportedProductException {
		eligibleClient.setCarAge(carAge);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(addToBaseCost(amount));
		assertNonEligiblePriceEqualsToBase();
	}

	private void assertNewCarFeeApplied(InsuranceCalculator calc, int carAge)
			throws UnsupportedProductException {
		eligibleClient.setCarAge(carAge);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(getNewCarFee());
		assertNonEligiblePriceEqualsToBase();
	}
	
	private void assertColorFeeApplied(InsuranceCalculator calc, CarColor colorRed, int amountRed)
			throws UnsupportedProductException {
		eligibleClient.setCarColor(colorRed);
		calc.calcInsurancePrice(clients);
		assertEligiblePriceEqualsTo(subtractPercent(BASE_COST, amountRed));
		assertNonEligiblePriceEqualsToBase();
	}

	private void assertNonEligiblePriceEqualsToBase() {
		assertEquals(BASE_COST, nonEligibleClient.getInsurancePrice());
	}
	private void assertEligiblePriceEqualsTo(BigDecimal expected) {
		assertEquals(expected, eligibleClient.getInsurancePrice());
	}

	private BigDecimal getSportcarFee() {
		return addToBaseCost(31);
	}
	private BigDecimal getNewCarFee() {
		return addToBaseCost(3);
	}
}