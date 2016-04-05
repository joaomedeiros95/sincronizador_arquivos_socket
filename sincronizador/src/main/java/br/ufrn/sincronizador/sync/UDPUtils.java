/**
 * 
 */
package br.ufrn.sincronizador.sync;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.joaoemedeiros.easysocket.socket.SocketMulticast;

/**
 * @author joao
 *
 */
public class UDPUtils {
	
	public static void sendDatagram(String msg, String ip, Integer porta, SocketMulticast socket) throws UnknownHostException {
		DatagramPacket dtgrm = new DatagramPacket(msg.getBytes(), msg.length(), InetAddress.getByName(ip), porta);
        try {
            socket.send(dtgrm);
        } catch (IOException e) {
            System.out.println("!!!Envia: " + e.getMessage());
        }
	}
	
}
