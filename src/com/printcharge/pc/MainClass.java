package com.printcharge.pc;

public class MainClass {
	public static void main(String args[]) {
		CostCalculator costCalculator = new CostCalculator();
		try {
			costCalculator.setup();
			costCalculator.calculateTotalCost();
		} catch (Exception e) {
			System.out.println("Program threw an error.");
			e.printStackTrace();
		}
	}
}
