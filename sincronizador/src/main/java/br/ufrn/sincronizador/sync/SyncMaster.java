/**
 * 
 */
package br.ufrn.sincronizador.sync;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Resposta;
import com.joaoemedeiros.easysocket.utils.Services;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

import br.ufrn.pd.dominio.Arquivo;
import br.ufrn.sincronizador.utils.ArquivoUtils;
import br.ufrn.sincronizador.utils.Session;
import br.ufrn.sincronizador.utils.comparador.Comparador;
import br.ufrn.sincronizador.utils.comparador.CriadorDiretorio;
import br.ufrn.sincronizador.utils.comparador.busca.Buscador;
import br.ufrn.sincronizador.utils.comparador.entidades.Arquivos;
import br.ufrn.sincronizador.utils.comparador.entidades.DadosArquivo;

/**
 * @author joao
 *
 */
public class SyncMaster extends Thread {
	
	private SocketClient client;

	public SyncMaster(String ip) throws EasySocketException {
		super();
		client = new SocketClient(ip, Services.SYNCSERVICE);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				compararListas();
			} catch (EasySocketException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void compararListas() throws EasySocketException {
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setOperacao(Operations.SOLICITARLISTA);
		
		client.enviarObjeto(solicitacao);
		
		Arquivos arquivosRecebidos = (Arquivos) client.readObject().getRetorno();
		Arquivos arquivosLocais = pegarListaLocal();
		System.out.println(arquivosRecebidos);
		System.out.println(arquivosLocais);

		Comparador comparador = new Comparador();
		List<DadosArquivo> arquivosAEnviar = comparador.compararListasArquivos(arquivosLocais, arquivosRecebidos);
		List<DadosArquivo> arquivosAReceber = comparador.compararListasArquivos(arquivosRecebidos, arquivosLocais);
		
		enviarArquivos(arquivosAEnviar);
		solicitarArquivos(arquivosAReceber);
	}

	private void solicitarArquivos(List<DadosArquivo> arquivosAReceber) throws EasySocketException {
		for(DadosArquivo dado : arquivosAReceber) {
			Solicitacao solicitacao = new Solicitacao();
			solicitacao.setOperacao(Operations.SOLICITARARQUIVO);
			solicitacao.setObjeto(dado.getPath());
			
			client.enviarObjeto(solicitacao);
			
			Resposta resposta = client.readObject();
			if(resposta.isResultado()) {
				salvarArquivo((Arquivo) resposta.getRetorno());
			}
		}
		
	}
	
	private Resposta salvarArquivo(Arquivo arquivo) {
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

	private void enviarArquivos(List<DadosArquivo> arquivosAEnviar) throws EasySocketException {
		for(DadosArquivo dado : arquivosAEnviar) {
			Solicitacao solicitacao = new Solicitacao();
			solicitacao.setOperacao(Operations.ENVIARARQUIVO);
			solicitacao.setObjeto(ArquivoUtils.getArquivo(dado.getPath()));
			
			client.enviarObjeto(solicitacao);
			
			if(!client.readObject().isResultado()) {
				break;
			}
		}
	}

	private Arquivos pegarListaLocal() {
		Buscador b = new Buscador();
		Arquivos arq = new Arquivos (Session.getInstance().getValue("caminho"));
		
		b.buscar(arq);
		b.removerArquivosDaPastaNegra(arq.getArquivos(), Session.getInstance().getValue("caminho") + "/Black Folder");
		
		return arq;
	}

}
