/**
 * 
 */
package br.ufrn.sincronizador.sync;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Services;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

import br.ufrn.sincronizador.operacoes.OperacaoArquivo;

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
				new OperacaoArquivo().executar(client, OperacaoArquivo.ENVIARARQUIVO);
				
				Solicitacao solicitacao = new Solicitacao();
				solicitacao.setOperacao(Operations.SOLICITARARQUIVO);
				solicitacao.setObjeto("/BparaA.txt");
				
				client.enviarObjeto(solicitacao);
				System.out.println(client.readObject().getMensagem());
			} catch (EasySocketException e) {
				e.printStackTrace();
			}
			
			
			dormir();
		}
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
