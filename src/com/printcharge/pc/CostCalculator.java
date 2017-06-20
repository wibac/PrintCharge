package com.printcharge.pc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class CostCalculator {
	private static final String PROPFILE = "setup.properties";
	private static Properties properties = null;
	
	public static void main(String args[]){
		CostCalculator costCalculator = new CostCalculator();
		try {
			costCalculator.setup();
			Path path = Paths.get(properties.getProperty("uri"));
			BufferedReader reader = Files.newBufferedReader(path);
			String line = null;
			int totalCost = 0;	
			while((line=reader.readLine())!=null){				
				totalCost = totalCost + costCalculator.calculateCost(line);// calculate cost and add to total
			}
			System.out.println("Total cost is " + (totalCost/100.0));
		} catch (IOException e) {
			System.out.println("Problem in reading input.");
			e.printStackTrace();
		}
	}
	
	/*
	 * Calculates the cost of printing for a particular job
	 */
	int calculateCost(String line){
		int cost = 0;
		String job[]=line.split(",");
		String pageSize="a4";//this will be taken from input when more page sizes are added
		if(job[2].trim().equals("false")){// one side
			cost = Integer.parseInt(job[1].trim()) * Integer.parseInt(properties.getProperty(pageSize+"colourOneSide"));
			cost = cost + (Integer.parseInt(job[0].trim())-Integer.parseInt(job[1].trim())) * Integer.parseInt(properties.getProperty(pageSize+"bwOneSide"));
		}else{//both side
			cost = Integer.parseInt(job[1].trim()) * Integer.parseInt(properties.getProperty(pageSize+"colourBothSide"));
			cost = cost + (Integer.parseInt(job[0].trim())-Integer.parseInt(job[1].trim())) * Integer.parseInt(properties.getProperty(pageSize+"bwBothSide"));
		}
		System.out.println("Cost for " + job[1] + " colour pages and " + 
		(Integer.parseInt(job[0].trim())-Integer.parseInt(job[1].trim()) + " b&w pages is " + cost));
		return cost;
	}
	
	/*
	 * Loads property file that contains printing costs as well as the path for the input file. 
	 */
	void setup() throws IOException{
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
