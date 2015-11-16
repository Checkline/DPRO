package com.checkline.dpro;

import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter implements Runnable {
    protected OutputStream out;
    protected boolean running = false;
    
    public SerialWriter ( OutputStream out ) {
        this.out = out;
    }
    
    public void startThread() {
    	this.running = true;
    }
    
    public void stopThread() {
    	this.running = false;
    }
    
    public void run () {
    		try {                
	            byte[] array = {0x52, 0x0D};
	            while ( this.running ) {
	               this.out.write(array);
	               this.out.flush();
	               Thread.sleep(16);
	            }                
	        }
	        catch ( IOException | InterruptedException e ) {
	            e.printStackTrace();
	        }            
    }

	public void addStream(OutputStream outputStream) {
		this.out = outputStream;		
	}
	
	public void removeStream() {
		this.out = null;
	}
	
}
