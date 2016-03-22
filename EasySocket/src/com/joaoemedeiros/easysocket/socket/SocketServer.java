/**
 * 
 */
package com.joaoemedeiros.easysocket.socket;

import java.io.IOException;
import java.net.ServerSocket;

import com.joaoemedeiros.easysocket.handler.MessageHandler;
import com.joaoemedeiros.easysocket.thread.ListeningThread;

/**
 * @author João
 *
 */
public class SocketServer {
	private ServerSocket serverSocket;
    private ListeningThread listeningThread;
    private MessageHandler messageHandler;

    public SocketServer(int port, MessageHandler handler) {
        messageHandler = handler;
        try {
            serverSocket = new ServerSocket(port);
            listeningThread = new ListeningThread(this, serverSocket);
            listeningThread.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void setMessageHandler(MessageHandler handler) {
        messageHandler = handler;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
    
    @Deprecated
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
