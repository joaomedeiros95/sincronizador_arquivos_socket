/**
 * 
 */
package com.joaoemedeiros.easysocket.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author João Medeiros
 *
 */
public class SocketClient {
	
	private Socket socket;
	
	public SocketClient(InetAddress ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }

    public void println(String message) throws IOException {
        PrintWriter writer;
	    writer = new PrintWriter(new OutputStreamWriter(
	                             socket.getOutputStream()), true);
	    writer.println(message);
    }
    
    public void enviarObjeto(Object objeto) throws IOException {
    	ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
    	writer.writeObject(objeto);
    }

    public String readLine() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(
                                    socket.getInputStream()));
        return reader.readLine();
    }

}
