/**
 * 
 */
package com.joaoemedeiros.easysocket.handler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author joao
 *
 */
public class Connection {
	
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
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
    
    public void close() {
    	try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
