package com.printcharge.pc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CostCalculatorTest {

	CostCalculator costCalculator;
	
	@Before
	public void setup() throws Exception{
		costCalculator = new CostCalculator();
		costCalculator.setup();
	}
	
	@Test
	public void testSingleSideOneColourZeroBlack() {
		String input="1,1,false";
		assertEquals(25, costCalculator.calculateCost(input));
	}

	@Test
	public void testSingleSideOneColourOneBlack() {
		String input="2,1,false";
		assertEquals(40, costCalculator.calculateCost(input));
	}
	
	@Test
	public void testSingleSideTwoColourOneBlack() {
		String input="3,2,false";
		assertEquals(65, costCalculator.calculateCost(input));
	}
	
	@Test
	public void testBothSideOneColourZeroBlack() {
		String input="1,1,true";
		assertEquals(20, costCalculator.calculateCost(input));
	}
	
	@Test
	public void testBothSideOneColourOneBlack() {
		String input="2,1,true";
		assertEquals(30, costCalculator.calculateCost(input));
	}
	
	@Test
	public void testBothSideTwoColourOneBlack() {
		String input="3,2,true";
		assertEquals(50, costCalculator.calculateCost(input));
	}
	
	@Ignore("yet to implement")
	@Test
	public void testInsufficientInput() {
		String input="2,1";
		
	}
	
	@Ignore("yet to implement")
	@Test
	public void testIncorrectSideInput() {
		String input="3,2,yes";
		assertEquals(65, costCalculator.calculateCost(input));
	}
	
	@Ignore("yet to implement")
	@Test
	public void testIncorrectPageInput() {
		String input="2,one,false";
		assertEquals(40, costCalculator.calculateCost(input));
	}
	
	@Ignore("yet to implement")
	@Test
	public void testIncorrectNumberOfPages() {
		String input="3,5,false";
		assertEquals(65, costCalculator.calculateCost(input));
	}
	
	@Ignore("yet to implement")
	@Test
	public void testCaseSensitiveInput() {
		String input="2,1,TRUE";
		assertEquals(40, costCalculator.calculateCost(input));
	}
}
