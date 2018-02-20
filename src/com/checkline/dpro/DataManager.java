package com.checkline.dpro;

import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataManager implements Runnable {
	
	protected DPro dPro;
	protected BlockingQueue<String> bq;
	protected long timeBetweenCaptures = 2000000000; //2 seconds
	protected long threadStartTime = 0;
	protected long startTime = 0;
	protected double threshold = 1;
	protected boolean countDown = false;
	protected boolean readingReset = false;
	protected boolean running = false;
	
	private String regexPattern = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])(,)(\\s+)(IN|MM)"; // Matches strings like '#.#, IN', '##.#, IN', '###.#, MM' etc.
	private Pattern p;
	private Matcher m;
	
    public DataManager(DPro dPro, BlockingQueue<String> bq) {
    	this.dPro = dPro;
        this.bq = bq;
        this.p = Pattern.compile(this.regexPattern,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    }
    
	public void setDelay(long d) {
		this.timeBetweenCaptures = d;
	}
	
    public void startThread() {
    	this.running = true;
    	this.threadStartTime = System.nanoTime();
    }
    
    public void stopThread() {
    	this.running = false;
    }
    
    @Override
    public void run() {
    	String out = "";
    	double number = 0;
    	long now = 0;
    	ReadingSet currentReading = null;
    	
    	while (this.running) {
			if ((out = bq.poll()) != null) {
				out = out.trim();
				this.m = this.p.matcher(out);
				if (this.m.find()) {
					now = System.nanoTime();
					number = Double.parseDouble(out.substring(0,out.indexOf(",")).replaceAll("\\s",""));	
					if (number > this.threshold && this.countDown == false && this.readingReset == true) {
						//gauge is starting to send data above threshold, mark the start time and flag the countDown and reset
						this.countDown = true;
						this.readingReset = false;
						this.startTime = System.nanoTime();
						currentReading = new ReadingSet(this.startTime);
						currentReading.addReading(new Reading(number, now));
						System.out.println("Reading Started");
						this.dPro.logMessage("Reading Started.");
					}
					else if (this.countDown == true && ((now - this.startTime) >= this.timeBetweenCaptures)) {
						//we're in the countdown wait until it has been timeBetweenCaputres from start to output the number
						currentReading.addReading(new Reading(number, now));
						currentReading.finalize(now);
						this.dPro.addReading(currentReading);
						System.out.println("Reading Ended");
						this.dPro.logMessage("Reading Ended.");
						this.countDown = false;
						this.startTime = 0;
					}
					else if (this.countDown == true && ((now - this.startTime) <= this.timeBetweenCaptures)) {
						currentReading.addReading(new Reading(number, now));
					}
					else if (number <= this.threshold && this.readingReset == false) {
						//the number is below the threshold and hasn't been reset
						this.readingReset = true;
						this.countDown = false;
						this.startTime = 0;
						System.out.println("Reset");
						this.dPro.logMessage("Gauge Reset.");
					}
					
					System.out.println(out + "+ " + ((now - this.threadStartTime) / 1000000000.0));
				}
				
			}
		}
    }
    
}
