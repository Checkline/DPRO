package com.checkline.dpro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import javax.swing.JButton;
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
			this.dmThread = null;
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

	public SerialManager getSerialManager() {
		return this.serialManager;
	}
	
	public void addReading(double number) {
		this.ui.addReading(this.round(number, 1));
	}
	
}
