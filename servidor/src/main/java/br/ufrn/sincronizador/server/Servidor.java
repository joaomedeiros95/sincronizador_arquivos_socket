/**
 * 
 */
package br.ufrn.sincronizador.server;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketServer;

import br.ufrn.sincronizador.server.handler.UsuarioHandler;

/**
 * @author joao
 *
 */
public class Servidor {
	
	public static void main(String[] args) throws EasySocketException {
		SocketServer server = new SocketServer(5665, new UsuarioHandler());
	}

}
