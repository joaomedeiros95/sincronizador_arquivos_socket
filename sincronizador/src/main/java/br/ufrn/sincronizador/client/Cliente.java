/**
 * 
 */
package br.ufrn.sincronizador.client;

import java.util.HashMap;
import java.util.Map;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.socket.SocketMulticast;
import com.joaoemedeiros.easysocket.utils.Services;

import br.ufrn.sincronizador.client.handler.MulticastHandlerImpl;
import br.ufrn.sincronizador.input.StringInput;
import br.ufrn.sincronizador.operacoes.Operacao;
import br.ufrn.sincronizador.operacoes.OperacaoArquivo;
import br.ufrn.sincronizador.operacoes.OperacaoUsuario;
import br.ufrn.sincronizador.server.Servidor;

/**
 * @author joao
 *
 */
public class Cliente {
	
	public static final String SERVIDORLOGIN = "localhost";
	public static final String SERVIDORSYNC = "localhost";
	public static final String MULTICASTSERVER = "239.0.0.1";
	
	private static Map<String, Operacao> operacoes;
	private static boolean finish;
	
	private static SocketClient loginSocket;
	private static SocketClient syncSocket;
	private static SocketMulticast multiSocket;
	
	public static void main(String[] args) {
		construirOperacoes();
		
		try {
			Servidor.criarServidor();
			loginSocket = new SocketClient(SERVIDORLOGIN, Services.LOGINSERVICE);
			syncSocket = new SocketClient(SERVIDORSYNC, Services.SYNCSERVICE);
//			multiSocket = new SocketMulticast(MULTICASTSERVER, Services.MULTISERVICE, new MulticastHandlerImpl());
			
			while(!finish) {
				solicitarOperacao();
			}
			
		} catch (EasySocketException e) {
			e.printStackTrace();
		}
	}

	private static void solicitarOperacao() throws EasySocketException {
		System.out.println("Operações Disponíveis (use 'sair' para sair): ");
		for(Map.Entry<String, Operacao> operacao : operacoes.entrySet()) {
			System.out.print("- " + operacao.getKey() + " ");
		}
		System.out.println("");		//Dar um /n no output
		
		StringInput stringInput = new StringInput();
		String entrada = stringInput.receiveInput("Digite o identificador da operação:");
		
		if(entrada.equalsIgnoreCase("sair")) {
			finish = true;
			return;
		} else {
			solicitarSubOperacao(entrada);
		}
	}

	private static void solicitarSubOperacao(String entrada) throws EasySocketException {
		SocketClient socket;
		
		Operacao operacao = operacoes.get(entrada);
		if(operacao == null) {
			System.out.println("Digite uma operação válida");
			return;
		}
		
		if(operacao instanceof OperacaoUsuario) {
			socket = loginSocket;
		} else {
			socket = syncSocket;
		}
		
		System.out.println("Sub Operações Disponíveis: ");
		
		for(Map.Entry<String, Integer> op : operacao.getSubOperacoes().entrySet()) {
			System.out.print("- " + op.getKey() + " ");
		}
		System.out.println("");		//Dar um /n no output
		
		StringInput stringInput = new StringInput();
		entrada = stringInput.receiveInput("Digite o identificador da operação:");
		
		Integer subOperacao = operacao.getSubOperacoes().get(entrada);
		operacao.executar(socket, subOperacao);
	}

	/**
	 * Constroi as operações disponíveis para o usuário
	 */
	private static void construirOperacoes() {
		operacoes = new HashMap<String, Operacao>();
		operacoes.put("usuario", new OperacaoUsuario());
		operacoes.put("arquivo", new OperacaoArquivo());
	}

}
