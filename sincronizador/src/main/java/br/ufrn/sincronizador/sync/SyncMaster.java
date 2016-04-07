/**
 * 
 */
package br.ufrn.sincronizador.sync;

import java.util.List;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Services;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

import br.ufrn.sincronizador.operacoes.OperacaoArquivo;
import br.ufrn.sincronizador.utils.ArquivoUtils;
import br.ufrn.sincronizador.utils.Session;
import br.ufrn.sincronizador.utils.comparador.Comparador;
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
		try {
			compararListas();
			
		} catch (EasySocketException e) {
			e.printStackTrace();
		}
		
		
		dormir();
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
		b.removerArquivosDaPastaNegra(arq.getArquivos(), Session.getInstance().getValue("caminho") + "/Black Paste");
		
		return arq;
	}

	private void dormir() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
