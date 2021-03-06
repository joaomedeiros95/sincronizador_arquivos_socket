/**
 * 
 */
package br.ufrn.servidor.server;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketServer;
import com.joaoemedeiros.easysocket.utils.Services;

import br.ufrn.servidor.server.handler.UsuarioHandler;

/**
 * @author joao
 *
 */
public class Servidor {
	
	public static void main(String[] args) throws EasySocketException {
		new SocketServer(Services.LOGINSERVICE, new UsuarioHandler());
	}

}
