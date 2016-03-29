/**
 * 
 */
package com.joaoemedeiros.easysocket.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.joaoemedeiros.easysocket.socket.SocketServer;

/**
 * @author joao
 *
 */
public class ListeningThread extends Thread {
	private SocketServer socketServer;
    private ServerSocket serverSocket;
    private boolean isRunning;

    public ListeningThread(SocketServer socketServer, ServerSocket serverSocket) {
        this.socketServer = socketServer;
        this.serverSocket = serverSocket;
        isRunning = true;
    }

    @Override
    public void run() {
        while(isRunning) {
            if (serverSocket.isClosed()) {
                isRunning = false;
                break;
            }
            
            try {
                Socket socket;
                socket = serverSocket.accept();
                new ConnectionThread(socket, socketServer).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void stopRunning() {
        isRunning = false;
    }
}
