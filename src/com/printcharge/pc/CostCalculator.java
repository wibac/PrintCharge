package com.printcharge.pc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class CostCalculator {
	private static final String PROPFILE = "setup.properties";
	private static Properties properties = null;
	private Job job = null;

	public void setJob(){
		if(job==null){
			job = new Job();
		}
	}
	/**
	 * gets the file path and reads a file on that path
	 */
	BufferedReader readFile() {
		Path path = Paths.get(properties.getProperty("uri"));
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(path);
		} catch (IOException e) {
			System.out.println("error while opening file");
			e.printStackTrace();
		}
		return reader;
	}

	/**
	 * reads the file and maintains total cost
	 */
	void calculateTotalCost() throws IOException {
		try {
			BufferedReader reader = readFile();
			if (reader == null)
				return;
			setJob();
			String line = null;
			int totalCost = 0;
			while ((line = reader.readLine()) != null) {
				// calculate cost and add to total
				totalCost = totalCost + calculateCost(line);
			}
			System.out.println("Total cost is $" + String.format("%.2f", (totalCost / 100.0)));
		} catch (Exception e) {
			System.out.println("Error. Check Input");
		}
	}

	/**
	 * validates the input and returns Job
	 */
	private Job initializeAndValidateJob(String input) {
		try {
			String inputArr[] = input.split(",");
			// check if the input has 3 required
			if (inputArr.length < 3) {
				throw new IllegalArgumentException();
			}
			// check if page count is integer
			job.setTotalPage(Integer.parseInt(inputArr[0].trim()));
			job.setColourPage(Integer.parseInt(inputArr[1].trim()));
			// check if page count is correct
			if ((job.getTotalPage() < job.getColourPage()) || (job.getTotalPage() < 0) || (job.getColourPage() < 0)) {
				throw new IllegalArgumentException();
			}
			job.setBwPage(job.getTotalPage() - job.getColourPage());

			if (inputArr[2].trim().equalsIgnoreCase("false")) {
				job.setPrintBothSide(false);
			} else if (inputArr[2].trim().equalsIgnoreCase("true")) {
				job.setPrintBothSide(true);
			} else {
				throw new IllegalArgumentException();
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		return job;
	}

	/**
	 * Calculates the cost of printing for a particular job
	 */
	int calculateCost(String input) {
		int cost = 0;
		// this will be taken from input when more page sizes are added
		String pageSize = "a4";
		initializeAndValidateJob(input);
		try {

			if (!job.isPrintBothSide()) {// one side
				cost = job.getColourPage() * Integer.parseInt(properties.getProperty(pageSize + "colourOneSide"));
				cost = cost + (job.getBwPage())
						* Integer.parseInt(properties.getProperty(pageSize + "bwOneSide"));
			} else {// both side
				cost = job.getColourPage() * Integer.parseInt(properties.getProperty(pageSize + "colourBothSide"));
				cost = cost + (job.getBwPage())
						* Integer.parseInt(properties.getProperty(pageSize + "bwBothSide"));
			}
		} catch (NumberFormatException nfe) {
			// System.out.println("Invalid input");
			throw nfe;
		}
		System.out.println("Cost for " + job.getColourPage() + " colour pages and "
				+ job.getBwPage() + " b&w pages is " + cost);
		return cost;
	}

	/**
	 * Loads property file that contains printing costs as well as the path for
	 * the input file.
	 */
	void setup() throws IOException {
		File file = new File(PROPFILE);
		properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		} catch (IOException e) {
			System.out.println("Failed to load settings. Check if properties are set correctly");
			e.printStackTrace();
			throw e;
		}
	}
}
