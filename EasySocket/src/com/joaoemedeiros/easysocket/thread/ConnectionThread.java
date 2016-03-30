/**
 * 
 */
package com.joaoemedeiros.easysocket.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.joaoemedeiros.easysocket.handler.Connection;
import com.joaoemedeiros.easysocket.socket.SocketServer;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

/**
 * @author joao
 *
 */
public class ConnectionThread extends Thread {
	private Socket socket;
    private SocketServer socketServer;
    private Connection connection;
    private boolean isRunning;

    public ConnectionThread(Socket socket, SocketServer socketServer) {
        this.socket = socket;
        this.socketServer = socketServer;
        connection = new Connection(socket);
        isRunning = true;
    }

    @Override
    public void run() {
        while(isRunning) {
            // Check whether the socket is closed.
            if (socket.isClosed()) {
                isRunning = false;
                break;
            }
            
            ObjectInputStream reader;
            try {
                reader = new ObjectInputStream(socket.getInputStream());
                Solicitacao solicitacao = (Solicitacao) reader.readObject();
                socketServer.getMessageHandler().onReceive(connection, solicitacao);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
    }
    
    public void stopRunning() {
        isRunning = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
