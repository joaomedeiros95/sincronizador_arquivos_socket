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
import br.ufrn.sincronizador.utils.ArquivoUtils;
import br.ufrn.sincronizador.utils.Session;
import br.ufrn.sincronizador.utils.comparador.CriadorDiretorio;
import br.ufrn.sincronizador.utils.comparador.busca.Buscador;
import br.ufrn.sincronizador.utils.comparador.entidades.Arquivos;

/**
 * @author joao
 *
 */
public class SincronizacaoHandler extends MessageHandler {
	
	private static final String BLACKPASTE = "Black Paste";

	@Override
	public void onReceive(Connection conexao, Solicitacao solicitacao) {
		super.conexao = conexao;
		super.solicitacao = solicitacao;
		Resposta resposta = null;
		
		if(solicitacao.getOperacao().equalsIgnoreCase(Operations.CONECTAR)) {
			resposta = Resposta.criarMensagemSucesso("Sincronização iniciada com sucesso!", null);
		} else if(solicitacao.getOperacao().equalsIgnoreCase(Operations.ENVIARARQUIVO)) {
			resposta = salvarArquivo();
		} else if(solicitacao.getOperacao().equalsIgnoreCase(Operations.SOLICITARARQUIVO)) {
			String caminho = (String) solicitacao.getObjeto();
			Arquivo arquivo = pegarArquivo(caminho);
			
			resposta = Resposta.criarMensagemSucesso("", arquivo);
		} else if(solicitacao.getOperacao().equalsIgnoreCase(Operations.SOLICITARLISTA)) {
			Buscador b = new Buscador();
			Arquivos arq = new Arquivos (Session.getInstance().getValue("caminho"));
			
			b.buscar(arq);
			String barra = System.getProperty("os.name").contains("Windows") ? "\\" : "/";
			b.removerArquivosDaPastaNegra(arq.getArquivos(), Session.getInstance().getValue("caminho") + barra + BLACKPASTE);
			
			resposta = Resposta.criarMensagemSucesso("", arq);
		} 
		
		enviarResposta(resposta);
	}

	private Arquivo pegarArquivo(String caminho) {
		return ArquivoUtils.getArquivo(caminho);
	}

	private Resposta salvarArquivo() {
		Arquivo arquivo = (Arquivo) solicitacao.getObjeto();
		
		FileOutputStream output;
		try {
			CriadorDiretorio.criarDiretorio(Session.getInstance().getValue("caminho") + arquivo.getCaminho(), arquivo.getNome());
			output = new FileOutputStream(Session.getInstance().getValue("caminho") + arquivo.getCaminho() + arquivo.getNome());
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
