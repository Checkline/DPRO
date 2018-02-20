package com.checkline.dpro;

public class Reading implements Comparable<Reading> {
	
	private double value;
	private double timeTaken;
	
	public Reading() {
		this.value = 0d;
		this.timeTaken = 0d;
	}
	
	public Reading(double value, double timeTaken) {
		this.value = value;
		this.timeTaken = timeTaken;
	}
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(double timeTaken) {
		this.timeTaken = timeTaken;
	}

	public int compareTo(Reading r) {
		return Double.compare(this.getValue(), r.getValue());
	}

	@Override
	public String toString() {
		return "Reading [value=" + value + ", timeTaken=" + timeTaken + "]";
	}	
	
}
