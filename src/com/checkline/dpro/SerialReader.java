package com.checkline.dpro;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

public class SerialReader implements Runnable {
	InputStream in;
    BlockingQueue<String> bq = null;
    protected boolean running = false;
    
    public SerialReader ( InputStream in, BlockingQueue<String> bq ) {
    	this.in = in;
    	this.bq = bq;
    }

    public void startThread() {
    	this.running = true;
    }
    
    public void stopThread() {
    	this.running = false;
    }
    
    public void run () {
	        byte[] buffer = new byte[1024];
	        int len = -1;
	        String line;
	        try {
	            while (this.running && ( len = this.in.read(buffer)) > -1 ) {
	            	line = new String(buffer,0,len);
	            	this.bq.put(line);
	            }
	        }
	        catch ( IOException | InterruptedException e ) {
	            e.printStackTrace();
	        }
    }

	public void addStream(InputStream inputStream) {
		this.in = inputStream;		
	}

	public void removeStream() {
		this.in = null;		
	}
}
