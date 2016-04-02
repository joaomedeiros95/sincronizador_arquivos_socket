/**
 * 
 */
package br.ufrn.sincronizador.server;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketServer;
import com.joaoemedeiros.easysocket.utils.Services;

import br.ufrn.sincronizador.server.handler.SincronizacaoHandler;

/**
 * @author joao
 *
 */
public class Servidor {
	
	public static void criarServidor() throws EasySocketException {
		new SocketServer(Services.SYNCSERVICE, new SincronizacaoHandler());
	}

}
