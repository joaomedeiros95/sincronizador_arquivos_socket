/**
 * 
 */
package com.joaoemedeiros.easysocket.handler;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.utils.Resposta;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

/**
 * @author joao
 *
 */
public abstract class MessageHandler {
	
	protected Connection conexao;
	protected Solicitacao solicitacao;
	
	public abstract void onReceive(Connection connection, Solicitacao solicitacao);
	
	/**
	 * Envia a resposta de volta para o cliente
	 * @param resposta
	 */
	protected void enviarResposta(Resposta resposta) {
		if(resposta != null) {
			try {
				conexao.enviarObjeto(resposta);
			} catch (EasySocketException e) {
				e.printStackTrace();
			}
		}
	}
}
