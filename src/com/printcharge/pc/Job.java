package com.printcharge.pc;

public class Job {
	private int totalPage;
	private int colourPage;
	private int bwPage;
	private boolean printBothSide;
	
	public int getColourPage() {
		return colourPage;
	}
	public void setColourPage(int colourPage) {
		this.colourPage = colourPage;
	}
	public int getBwPage() {
		return bwPage;
	}
	public void setBwPage(int bwPage) {
		this.bwPage = bwPage;
	}
	public boolean isPrintBothSide() {
		return printBothSide;
	}
	public void setPrintBothSide(boolean printBothSide) {
		this.printBothSide = printBothSide;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
}
