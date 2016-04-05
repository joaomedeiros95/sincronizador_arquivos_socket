/**
 * 
 */
package com.joaoemedeiros.easysocket.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.handler.MulticastHandler;
import com.joaoemedeiros.easysocket.thread.MulticastThread;

/**
 * @author joao
 *
 */
public class SocketMulticast {
	
	private MulticastSocket socket;
	
	public SocketMulticast(String ip, Integer porta, MulticastHandler handler) throws EasySocketException {
		try {
			socket = new MulticastSocket(porta);
			InetAddress address = InetAddress.getByName(ip);
			socket.joinGroup(address);
			
			new MulticastThread(socket, handler).start();
		} catch (IOException e) {
			throw new EasySocketException(e.getMessage());
		}
		
	}

}
