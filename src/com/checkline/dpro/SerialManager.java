package com.checkline.dpro;

import gnu.io.CommPortIdentifier;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class SerialManager {

	BlockingQueue<String> bq;
	SerialConnector sc;

	public SerialManager(BlockingQueue<String> bq) {
		this.bq = bq;
		this.sc = new SerialConnector(this);
	}

	public boolean hasChosenPort() {
		return this.sc.getPort() != null;
	}
	
	public void updatePort(String selectedPortName, CommPortIdentifier selectedPortIdentifier) {
		this.sc.updatePort(selectedPortName, selectedPortIdentifier);
	}
	
	public void startRunning() throws Exception {
		this.sc.connect();
		this.sc.createThreads();
		this.sc.startThreads();
	}
	
	public void stopRunning() throws IOException {
		this.sc.stopThreads();
		this.sc.disconnect();
		this.sc.destroyThreads();
	}

	public BlockingQueue<String> getQueue() {
		return this.bq;
	}
	
}
