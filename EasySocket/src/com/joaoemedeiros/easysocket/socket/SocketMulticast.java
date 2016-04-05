/**
 * 
 */
package com.joaoemedeiros.easysocket.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.joaoemedeiros.easysocket.handler.MulticastHandler;
import com.joaoemedeiros.easysocket.thread.MulticastThread;

/**
 * @author joao
 *
 */
public class SocketMulticast {
	
	private MulticastSocket socket;
	
	public SocketMulticast(String ip, Integer porta, MulticastHandler handler) throws IOException {
		socket = new MulticastSocket(porta);
		InetAddress address = InetAddress.getByName(ip);
		socket.joinGroup(address);
		
		new MulticastThread(socket, handler).start();
	}

}
