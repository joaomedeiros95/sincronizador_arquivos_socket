/**
 * 
 */
package br.ufrn.sincronizador.server.handler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.joaoemedeiros.easysocket.handler.Connection;
import com.joaoemedeiros.easysocket.handler.MessageHandler;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Resposta;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

import br.ufrn.pd.dominio.Arquivo;
import br.ufrn.sincronizador.client.Cliente;
import br.ufrn.sincronizador.sync.SyncThread;

/**
 * @author joao
 *
 */
public class SincronizacaoHandler extends MessageHandler {

	@Override
	public void onReceive(Connection conexao, Solicitacao solicitacao) {
		super.conexao = conexao;
		super.solicitacao = solicitacao;
		Resposta resposta = null;
		
		if(solicitacao.getOperacao().equalsIgnoreCase(Operations.ENVIARARQUIVO)) {
			resposta = salvarArquivo();
		} else if(solicitacao.getOperacao().equalsIgnoreCase(Operations.SINCRONIZAR)) {
			String ip = (String) solicitacao.getObjeto();
			
			Cliente.setIPSYNC(ip);
			SyncThread.getInstance().start();
			
			resposta = Resposta.criarMensagemSucesso("Sincronização iniciada com sucesso!", null);
		}
		
		enviarResposta(resposta);
	}

	private Resposta salvarArquivo() {
		Arquivo arquivo = (Arquivo) solicitacao.getObjeto();
		
		FileOutputStream output;
		try {
			output = new FileOutputStream(arquivo.getCaminho() + arquivo.getNome());
			output.write(arquivo.getArquivo());
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return Resposta.criarMensagemErro("Erro ao criar arquivo", null);
		} catch (IOException e) {
			e.printStackTrace();
			return Resposta.criarMensagemErro("Erro ao criar arquivo", null);
		}
		
		return Resposta.criarMensagemSucesso("Arquivo criado com sucesso " + arquivo.getCaminho(), null);
	}

}
