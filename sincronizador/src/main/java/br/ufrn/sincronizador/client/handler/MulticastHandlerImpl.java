/**
 * 
 */
package br.ufrn.sincronizador.client.handler;

import java.net.MulticastSocket;

import com.joaoemedeiros.easysocket.handler.MulticastHandler;

/**
 * @author joao
 *
 */
public class MulticastHandlerImpl extends MulticastHandler {

	@Override
	public void onReceive(MulticastSocket socket, Object objeto) {
		String login = (String) objeto;
		
		System.out.println("Recebido por Multicast, ip: " + login);
	}

}
