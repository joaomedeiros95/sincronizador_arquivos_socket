/**
 * 
 */
package com.joaoemedeiros.easysocket.handler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.joaoemedeiros.easysocket.exception.EasySocketException;

/**
 * @author joao
 *
 */
public class Connection {
	
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void println(String message) throws EasySocketException {
        PrintWriter writer;
	    try {
			writer = new PrintWriter(new OutputStreamWriter(
			                         socket.getOutputStream()), true);
			writer.println(message);
		} catch (IOException e) {
			throw new EasySocketException(e.getMessage());
		}
    }
    
    public void enviarObjeto(Object objeto) throws EasySocketException {
    	ObjectOutputStream writer;
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
			writer.writeObject(objeto);
		} catch (IOException e) {
			throw new EasySocketException(e.getMessage());
		}
    }
    
    public void close() {
    	try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
