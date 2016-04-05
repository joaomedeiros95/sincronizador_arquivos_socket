/**
 * 
 */
package br.ufrn.sincronizador.server;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.handler.MessageHandler;
import com.joaoemedeiros.easysocket.socket.SocketServer;

/**
 * @author joao
 *
 */
public class Servidor {
	
	public static void criarServidor(Integer port, MessageHandler handler) throws EasySocketException {
		new SocketServer(port, handler);
	}

}
