/**
 * 
 */
package br.ufrn.sincronizador.operacoes;

import java.util.Map;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Resposta;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

/**
 * @author joao
 *
 */
public abstract class Operacao {

	public abstract void executar(SocketClient client, Integer subOperacao) throws EasySocketException;
	public abstract Map<String, Integer> getSubOperacoes();
	
	protected void enviar(SocketClient client, Solicitacao solicitacao) throws EasySocketException {
		client.enviarObjeto(solicitacao);
	}

	protected void imprimirResposta(SocketClient client) throws EasySocketException {
		Resposta resposta = client.readObject();
		
		if(!resposta.isResultado()) {
			System.out.println("Ocorreu um erro ao realizar sua operação!");
		}
		
		System.out.println(resposta.getMensagem());
	}
	
	protected boolean getResultadoResposta(SocketClient client) throws EasySocketException {
		Resposta resposta = client.readObject();
		
		if(!resposta.isResultado()) {
			System.out.println("Ocorreu um erro ao realizar sua operação!");
		}
		
		System.out.println(resposta.getMensagem());	
		
		return resposta.isResultado();
	}
}
