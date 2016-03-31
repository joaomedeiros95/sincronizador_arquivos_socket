/**
 * 
 */
package com.joaoemedeiros.easysocket.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.utils.Resposta;

/**
 * @author João Medeiros
 *
 */
public class SocketClient {
	
	private Socket socket;
	
	public SocketClient(String ip, int port) throws EasySocketException {
        try {
			socket = new Socket(ip, port);
		} catch (IOException e) {
			throw new EasySocketException(e.getMessage());
		}
    }
	
	public SocketClient(InetAddress ip, int port) throws EasySocketException {
		try {
			socket = new Socket(ip, port);
		} catch (IOException e) {
			throw new EasySocketException(e.getMessage());
		}
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
    
    public String readLine() throws EasySocketException {
        BufferedReader reader;
        try {
			reader = new BufferedReader(new InputStreamReader(
			                            socket.getInputStream()));
			return reader.readLine();
		} catch (IOException e) {
			throw new EasySocketException(e.getMessage());
		}
    }
    
    public Resposta readObject() throws EasySocketException {
    	ObjectInputStream reader;
        try {
            reader = new ObjectInputStream(socket.getInputStream());
            return (Resposta) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
        	throw new EasySocketException(e.getMessage());
        } 
    }

    public void enviarObjeto(Object objeto) throws EasySocketException {
    	ObjectOutputStream writer;
    	try {
    		writer = new ObjectOutputStream(socket.getOutputStream());
    		writer.writeObject(objeto);
    		writer.flush();
    	} catch (IOException e) {
    		throw new EasySocketException(e.getMessage());
    	}
    }
}
