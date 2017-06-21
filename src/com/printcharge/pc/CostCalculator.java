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
	
	/**
	 * gets the file path and reads a file on that path
	 */
	BufferedReader readFile(){
		Path path = Paths.get(properties.getProperty("uri"));
		BufferedReader reader=null;
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
	void calculateTotalCost() {
		try {
			BufferedReader reader = readFile();
			if(reader==null)
				return;
			String line = null;
			int totalCost = 0;
			while ((line = reader.readLine()) != null) {
				totalCost = totalCost + calculateCost(line);// calculate cost and add to total
			}
			System.out.println("Total cost is $" + String.format("%.2f", (totalCost / 100.0)));
		} catch (IOException e) {
			System.out.println("error in reading file");
			e.printStackTrace();
		}
	}

	/**
	 * Calculates the cost of printing for a particular job
	 */
	int calculateCost(String line) {
		int cost = 0;
		String job[] = line.split(",");
		String pageSize = "a4";// this will be taken from input when more page sizes are added
		int colourPageCount=0;
		int totalPageCount=0;
		if(job.length<3){ //check if the input has 3 required components
			System.out.println("Invalid input");
			return cost;
		}
		try{
			//check if page count is integer
			totalPageCount = Integer.parseInt(job[0].trim());
			colourPageCount = Integer.parseInt(job[1].trim());
			if((totalPageCount<colourPageCount)||(totalPageCount<0) || (colourPageCount<0)){ //check if page count is correct
				System.out.println("Invalid input");
				return cost;
			}
		}catch(NumberFormatException nfe){
			System.out.println("Invalid input");
			return cost;
		}
		if (job[2].trim().equalsIgnoreCase("false")) {// one side
			cost = colourPageCount
					* Integer.parseInt(properties.getProperty(pageSize + "colourOneSide"));
			cost = cost + (totalPageCount - colourPageCount)
					* Integer.parseInt(properties.getProperty(pageSize + "bwOneSide"));
		} else if(job[2].trim().equalsIgnoreCase("true")){// both side
			cost = colourPageCount
					* Integer.parseInt(properties.getProperty(pageSize + "colourBothSide"));
			cost = cost + (totalPageCount - colourPageCount)
					* Integer.parseInt(properties.getProperty(pageSize + "bwBothSide"));
		}
		System.out.println("Cost for " + job[1] + " colour pages and "
				+ (Integer.parseInt(job[0].trim()) - Integer.parseInt(job[1].trim()) + " b&w pages is " + cost));
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
