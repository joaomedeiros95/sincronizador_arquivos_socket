/**
 * 
 */
package br.ufrn.sincronizador.operacoes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

import br.ufrn.pd.dominio.Arquivo;
import br.ufrn.sincronizador.input.StringInput;

/**
 * @author joao
 *
 */
public class OperacaoArquivo extends Operacao {
	
	public static final Integer ENVIARARQUIVO = 1;
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
		}
		
		enviar(client, solicitacao);
		imprimirResposta(client);
	}

	private Arquivo pegarArquivo() {
		String caminho = "";
		String nomeArquivo = "";
		Arquivo arquivo = null;
		
		caminho = input.receiveInput("Digite o caminho do arquivo: ");
		nomeArquivo = input.receiveInput("Digite o nome do arquivo (com extensão): ");
		
		File file = new File(caminho + nomeArquivo);
		try {
			byte[] array = FileUtils.readFileToByteArray(file);
			arquivo = new Arquivo();
			arquivo.setCaminho(caminho + "teste/");
			arquivo.setArquivo(array);
			arquivo.setNome(nomeArquivo);
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao ler o arquivo");
			e.printStackTrace();
		}
		
		return arquivo;
	}

	@Override
	public Map<String, Integer> getSubOperacoes() {
		Map<String, Integer> subOperacoes = new HashMap<String, Integer>();
		subOperacoes.put("enviar_arquivo", ENVIARARQUIVO);
		
		return subOperacoes;
	}

}
