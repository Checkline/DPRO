package com.checkline.dpro;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class DPro {
	
	protected DProUI ui;
	protected SerialManager serialManager;
	protected DataManager dataManager;
	protected Thread dmThread;
	protected boolean running = false;
	protected PortChooser port;
	protected double delay;
	protected BlockingQueue<String> bq;
	protected ArrayList<ReadingSet> readings;
	protected AudioInputStream audioInputStream;
	protected Clip clip;
	protected double high = 0.00;
	protected double low = 0.00;
	protected double avg = 0.00;
	protected double stddev = 0.00;
	
	
	public DPro(String version) {
		this.ui = new DProUI(this, version);
		this.ui.setVisible(true);
		this.ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.port = new PortChooser(null, this);
		this.delay = 2.0;
		this.bq = new LinkedBlockingQueue<String>();
		this.serialManager = new SerialManager(this.bq);
		this.dataManager = new DataManager(this, this.bq);
		this.dmThread = null;
		this.readings = new ArrayList<ReadingSet>();
		InputStream is = new BufferedInputStream(getClass().getResourceAsStream("/audio/readingDone.wav"));
		try {
			this.audioInputStream = AudioSystem.getAudioInputStream(is);
			this.clip = AudioSystem.getClip();
			this.clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReadingSet test = new ReadingSet(28637558142101d);
		test.addReading(new Reading(40, 28637558124217d));
		test.addReading(new Reading(68.3, 28637855873841d));
		test.addReading(new Reading(71.5, 28638054417705d));
		test.addReading(new Reading(70.7, 28638353700776d));
		test.addReading(new Reading(70.5, 28639580997926d));
		test.finalize(28639580997926d);
		this.addReading(test);
		
		test = new ReadingSet(28637558142101d);
		test.addReading(new Reading(61, 28637558124217d));
		test.addReading(new Reading(72.4, 28637855873841d));
		test.addReading(new Reading(78.1, 28638054417705d));
		test.addReading(new Reading(74.7, 28638353700776d));
		test.addReading(new Reading(73.5, 28639580997926d));
		test.finalize(28639580997926d);
		this.addReading(test);
		
	}

	public void setDelay(double d) {
		if (this.isRunning()) {
			JOptionPane.showMessageDialog(null, "Please stop the current running test before changing the delay.");
		}
		else {
			this.delay = this.round(d, 1);
			this.dataManager.setDelay((long) (this.delay*1000000000));
			this.ui.setDelayText(this.delay);
			this.logMessage("Delay has been changed to " + this.delay);
		}
	}

	public void startRunning() {
		if (this.serialManager.hasChosenPort()) {
			this.running = true;
			this.ui.getStartButton().setText("Stop");
			try {
				this.dmThread = new Thread(this.dataManager);
				this.dataManager.startThread();
				this.dmThread.start();
				this.serialManager.startRunning();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Running");
		}
		else {
			JOptionPane.showMessageDialog(null, "Please Select a Port first");
		}
	}

	public void stopRunning() {
		this.running = false;
		this.ui.getStartButton().setText("Start");
		try {
			this.serialManager.stopRunning();
			this.dataManager.stopThread();
			this.dmThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Stopped");
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public void portSettings() {
		this.port.setVisible(true);
	}
	
	private ArrayList<Double> getReadingsAsDouble() {
		ArrayList<Double> doubleList = new ArrayList<Double>();
		for (ReadingSet reading : this.getReadings()) {
			doubleList.add(reading.getFinalValue());
		}
		return doubleList;
	}
	
	public void logMessage(String m) {
		JTextArea log = this.ui.getLogArea();
		log.append(m+"\n");
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}	
	
	public void addReading(ReadingSet reading) {
		this.readings.add(reading);
		this.ui.addReading(this.round(reading.getFinalValue(), 1));
		this.calculateStatistics();
		this.updateStatistics();
		this.playReadingSavedSound();
	}
	
	public void playReadingSavedSound() {
		this.clip.stop();
		this.clip.setMicrosecondPosition(0);
		this.clip.start();		
	}
	
	public void exportReadings() {
		JFileChooser f = new JFileChooser();
        f.setDialogTitle("Select Folder");
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.setAcceptAllFileFilterUsed(false);
        f.showSaveDialog(null);

        System.out.println(f.getCurrentDirectory());
        System.out.println(f.getSelectedFile());
		SpreadsheetCreator test = new SpreadsheetCreator(this);
		test.saveFile(f.getSelectedFile().getPath());
		this.printReadings();
	}
	
	public void printReadings() {
		for (ReadingSet reading : this.readings) {
			System.out.println(reading.toString());
		}
	}
	
	public void updateStatistics() {
		this.ui.updateStatistics(this.high, this.low, this.avg, this.stddev);
	}
	
	public void calculateStatistics() {
		ArrayList<ReadingSet> readings = this.getReadings();
		ArrayList<Double> dReadings = this.getReadingsAsDouble();
		if (!readings.isEmpty()) {
			this.high = Collections.max(readings).getFinalValue();
			this.low = Collections.min(readings).getFinalValue();
			this.avg = calculateAverage(dReadings);
			this.stddev = calculateStandardDeviation(dReadings, this.avg);
		}
	}
	
	private double calculateAverage(List<Double> list) {
		double sum = 0;
		if(!list.isEmpty()) {
			for (Double value : list) {
				sum += value;
			}
			return sum / list.size();
		}
		return sum;
	}
	
	private double calculateStandardDeviation(List <Double> list, double average) {
		List<Double> squared = new ArrayList<Double>();
		if(!list.isEmpty()) {
			for (Double value : list) {
				squared.add(Math.pow(value-average, 2));
			}
			return Math.sqrt(this.calculateAverage(squared));
		}
		return 0;
	}

	public ArrayList<ReadingSet> getReadings() {
		return this.readings;
	}	
	
	public SerialManager getSerialManager() {
		return this.serialManager;
	}
	
	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getStddev() {
		return stddev;
	}

	public void setStddev(double stddev) {
		this.stddev = stddev;
	}


	
}
