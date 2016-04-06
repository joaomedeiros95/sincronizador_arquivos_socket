/**
 * 
 */
package br.ufrn.sincronizador.operacoes;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Services;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

import br.ufrn.pd.dominio.Arquivo;
import br.ufrn.sincronizador.arquivos.ManipuladorArquivo;
import br.ufrn.sincronizador.client.Cliente;
import br.ufrn.sincronizador.input.StringInput;
import br.ufrn.sincronizador.server.Servidor;
import br.ufrn.sincronizador.server.handler.SincronizacaoHandler;
import br.ufrn.sincronizador.sync.UDPUtils;
import br.ufrn.sincronizador.utils.ArquivoUtils;

/**
 * @author joao
 *
 */
public class OperacaoArquivo extends Operacao {
	
	public static final Integer ENVIARARQUIVO = 1;
	public static final Integer SINCRONIZAR = 2;
	private StringInput input;

	@Override
	public void executar(SocketClient client, Integer subOperacao) throws EasySocketException {
		Solicitacao solicitacao = new Solicitacao();
		input = new StringInput();
		
		if(subOperacao == ENVIARARQUIVO) {
			solicitacao.setOperacao(Operations.ENVIARARQUIVO);
			Object objeto = pegarArquivo();
			
			if(objeto == null) {
				return;
			}
			
			solicitacao.setObjeto(objeto);
			
			enviar(client, solicitacao);
			imprimirResposta(client);
		} else if (subOperacao == SINCRONIZAR) {
			try {
				Servidor.criarServidor(Services.SYNCSERVICE, new SincronizacaoHandler());
				UDPUtils.sendDatagram(InetAddress.getLocalHost().getHostName() + ";" + ManipuladorArquivo.getLogin(), Cliente.MULTICASTSERVER, Services.MULTISERVICE, Cliente.getMultiSocket());
			} catch (IOException e) {
				System.out.println("Ocorreu um erro ao processar sua operação!");
			}
			
		}
		
	}

	private Arquivo pegarArquivo() {
		String caminho = "";
		String nomeArquivo = "";
		
		caminho = input.receiveInput("Digite o caminho do arquivo: ");
		nomeArquivo = input.receiveInput("Digite o nome do arquivo (com extensão): ");
		
		return ArquivoUtils.getArquivo(caminho, nomeArquivo);
	}

	@Override
	public Map<String, Integer> getSubOperacoes() {
		Map<String, Integer> subOperacoes = new HashMap<String, Integer>();
		subOperacoes.put("sincronizar", SINCRONIZAR);
		
		return subOperacoes;
	}

}
