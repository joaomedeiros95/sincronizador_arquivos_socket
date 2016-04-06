/**
 * 
 */
package com.joaoemedeiros.easysocket.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

import com.joaoemedeiros.easysocket.handler.MulticastHandler;

/**
 * @author joao
 *
 */
public class MulticastThread extends Thread {
	
	private MulticastSocket socket;
	private MulticastHandler handler;
	private boolean isRunning;

	public MulticastThread(MulticastSocket socket, MulticastHandler handler) {
		this.socket = socket;
		this.handler = handler;
		isRunning = true;
	}
	
	@Override
	public void run() {
		while(isRunning) {
			if(socket.isClosed()) {
				isRunning = false;
				break;
			}
			
			try {
				byte rec[] = new byte[256];
				DatagramPacket pkg = new DatagramPacket(rec, rec.length); 
				socket.receive(pkg);
				String data = new String(pkg.getData());
				String ip = pkg.getAddress().getHostAddress();
				
				handler.onReceive(socket, data, ip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

		}
	}

}
