package com.checkline.dpro;

import java.util.ArrayList;
import java.util.Collections;

public class ReadingSet implements Comparable<ReadingSet> {
	
	private double timeStart;
	private double timeEnd;
	private double finalValue;
	private double maxValue;
	private ArrayList<Reading> readings;
	
	public ReadingSet(double timeStart) {
		this.timeStart = timeStart;
		this.readings = new ArrayList<Reading>();
	}

	public void finalize() {
		this.finalize(System.nanoTime());
	}
	
	public void finalize(double time) {
		this.timeEnd = time;
		this.setFinalValue(this.readings.get(this.readings.size()-1).getValue());
		this.setMaxValue(this.findMaxValue());
	}
	
	private double findMaxValue() {
		return Collections.max(this.readings).getValue();
	}

	public void addReading(Reading reading) {
		this.readings.add(reading);
	}
	
	public void addReading(double value, double time) {
		this.addReading(new Reading(value, time));
	}
	
	public ArrayList<Reading> getReadings() {
		return this.readings;
	}
	
	public double getFinalValue() {
		return this.finalValue;
	}
	
	public void setFinalValue(double value) {
		this.finalValue = value;
	}
	
	public double getMaxValue() {
		return this.maxValue;
	}
	
	public void setMaxValue(double value) {
		this.maxValue = value;
	}
	
	public double getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(double timeStart) {
		this.timeStart = timeStart;
	}

	public double getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(double timeEnd) {
		this.timeEnd = timeEnd;
	}

	public int compareTo(ReadingSet r) {
		return Double.compare(this.getFinalValue(), r.getFinalValue());
	}

	@Override
	public String toString() {
		return "ReadingSet [timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", elapsedTime=" + ((timeEnd-timeStart) / 1000000000.0) + ", finalValue=" + finalValue
				+ ", maxValue=" + maxValue + ", readings=" + readings.toString() + "]";
	}	
		
}
