/**
 * 
 */
package com.joaoemedeiros.easysocket.handler;

import java.net.MulticastSocket;

import com.joaoemedeiros.easysocket.utils.Solicitacao;

/**
 * @author joao
 *
 */
public abstract class MulticastHandler {
	
	protected MulticastSocket conexao;
	protected Solicitacao solicitacao;
	
	public abstract void onReceive(MulticastSocket connection, Object objeto, String ip);
	
}
