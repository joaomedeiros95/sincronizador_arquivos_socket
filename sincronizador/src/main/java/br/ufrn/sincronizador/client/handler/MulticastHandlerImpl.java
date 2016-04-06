/**
 * 
 */
package br.ufrn.sincronizador.client.handler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.handler.MulticastHandler;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Resposta;
import com.joaoemedeiros.easysocket.utils.Services;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

import br.ufrn.sincronizador.arquivos.ManipuladorArquivo;
import br.ufrn.sincronizador.sync.SyncMaster;

/**
 * @author joao
 *
 */
public class MulticastHandlerImpl extends MulticastHandler {

	@Override
	public void onReceive(MulticastSocket socket, Object objeto, String ip) {
		String recebido = (String) objeto;
		String[] infos = recebido.split(";");
		
		try {
			if(infos[0].trim().equalsIgnoreCase(InetAddress.getLocalHost().getHostName())) {
				if(infos[1].trim().equalsIgnoreCase(ManipuladorArquivo.getLogin())) {
					conectarClientes(ip);
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EasySocketException e) {
			e.printStackTrace();
		} 
		
	}

	private void conectarClientes(String ip) throws EasySocketException, IOException {
		SocketClient client = new SocketClient(ip, Services.SYNCSERVICE);
		Solicitacao solicitacao = new Solicitacao();
		
		solicitacao.setOperacao(Operations.CONECTAR);
		client.enviarObjeto(solicitacao);
		
		Resposta resposta = client.readObject();
		
		if(resposta.isResultado()) {
			System.out.println("Sincronização iniciada com sucesso!");
			client.close();
			
			new SyncMaster(ip).run();
		}
	}

}
