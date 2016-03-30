/**
 * 
 */
package br.ufrn.sincronizador.operacoes;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

/**
 * @author joao
 *
 */
public class OperacaoUsuario extends Operacao {
	
	public static final Integer CRIAR = 1;
	public static final Integer LOGAR = 2;
	
	public void executar(SocketClient client, Integer subOperacao, Object objeto) throws EasySocketException {
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setObjeto(objeto);
		
		if(subOperacao == CRIAR) {
			solicitacao.setOperacao(Operations.CADASTRARUSUARIO);
		} else if (subOperacao == LOGAR) {
			solicitacao.setOperacao(Operations.LOGIN);
		}
		
		enviar(client, solicitacao);
		imprimirResposta(client);
	}

}
