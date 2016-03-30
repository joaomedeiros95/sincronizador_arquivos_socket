/**
 * 
 */
package br.ufrn.sincronizador.client;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Services;

/**
 * @author joao
 *
 */
public class Cliente {
	
	private static final String SERVIDORLOGIN = "localhost";
	
	public static void main(String[] args) {
		try {
			SocketClient loginSocket = new SocketClient(SERVIDORLOGIN, Services.LOGINSERVICE);
			
		} catch (EasySocketException e) {
			e.printStackTrace();
		}
	}

}
