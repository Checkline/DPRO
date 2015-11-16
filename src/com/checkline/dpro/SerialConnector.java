package com.checkline.dpro;

import java.io.IOException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class SerialConnector {
	
	protected SerialManager serialManager;
	protected SerialPort thePort = null;
	protected CommPortIdentifier selectedIndentifier = null;
	protected String selectedIndentifierName;
	protected SerialReader serialReader;
	protected SerialWriter serialWriter;
	protected Thread readThread, writeThread;
	
	public SerialConnector(SerialManager serialManager) {
		this.serialManager = serialManager;
		this.serialReader = new SerialReader(null, this.serialManager.getQueue());
		this.serialWriter = new SerialWriter(null);
	}
	
	public void updatePort(String selectedPortName, CommPortIdentifier selectedPortIdentifier) {
		this.selectedIndentifierName = selectedPortName;
		this.selectedIndentifier = selectedPortIdentifier;
	}
	
	public void connect() throws Exception {
		if (this.portAvailable()) {
			CommPort commPort = this.selectedIndentifier.open(this.getClass().getName(),6000);
			if ( commPort instanceof SerialPort ) {
				this.thePort = (SerialPort) commPort;
				this.thePort.setSerialPortParams(4800,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_ODD);
				this.thePort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
	            System.out.println("BaudRate: " + this.thePort.getBaudRate());
	            System.out.println("DataBIts: " + this.thePort.getDataBits());
	            System.out.println("StopBits: " + this.thePort.getStopBits());
	            System.out.println("Parity: " + this.thePort.getParity());
	            System.out.println("FlowControl: " + this.thePort.getFlowControlMode());
				this.serialReader.addStream(this.thePort.getInputStream());
				this.serialWriter.addStream(this.thePort.getOutputStream());
	        }
			else {
				throw new Exception("Port - "+this.selectedIndentifierName+" - is not a serial port.");
			}
		}
		else {
			throw new Exception("Port - "+this.selectedIndentifierName+" - is not available.");
		}
	}
	
	public void disconnect() throws IOException {
		if (this.thePort != null) {
			this.thePort.getInputStream().close();
			this.thePort.getOutputStream().close();
			this.serialReader.removeStream();
			this.serialWriter.removeStream();
			this.thePort.close();
			this.thePort = null;
		}
	}
	
	public boolean portAvailable() {
		return !this.selectedIndentifier.isCurrentlyOwned();
	}
	
	public void createThreads() {
		this.readThread = new Thread(this.serialReader);
		this.writeThread = new Thread(this.serialWriter);
	}

	public void destroyThreads() {
		this.readThread = null;
		this.writeThread = null;
	}
	
	public void startThreads() { 
		serialReader.startThread();
		serialWriter.startThread();
		try {
			readThread.start();
			writeThread.start();
		} catch (IllegalThreadStateException e) {
			e.printStackTrace();
		}
	}
	
	public void stopThreads() {
		serialReader.stopThread();
		serialWriter.stopThread();
		try {
			readThread.join();
			writeThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getPort() {
		return selectedIndentifierName;
	}

}
