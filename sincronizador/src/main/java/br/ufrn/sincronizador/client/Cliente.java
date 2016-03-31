/**
 * 
 */
package br.ufrn.sincronizador.client;

import java.util.HashMap;
import java.util.Map;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Services;

import br.ufrn.sincronizador.input.StringInput;
import br.ufrn.sincronizador.operacoes.Operacao;
import br.ufrn.sincronizador.operacoes.OperacaoUsuario;

/**
 * @author joao
 *
 */
public class Cliente {
	
	private static final String SERVIDORLOGIN = "localhost";
	private static Map<String, Operacao> operacoes;
	private static SocketClient loginSocket;
	private static boolean finish;
	
	public static void main(String[] args) {
		construirOperacoes();
		
		try {
			loginSocket = new SocketClient(SERVIDORLOGIN, Services.LOGINSERVICE);
			
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
		System.out.println("Sub Operações Disponíveis: ");
		Operacao operacao = operacoes.get(entrada);
		
		if(operacao == null) {
			System.out.println("Digite uma operação válida");
			return;
		}
		
		for(Map.Entry<String, Integer> op : operacao.getSubOperacoes().entrySet()) {
			System.out.print("- " + op.getKey() + " ");
		}
		System.out.println("");		//Dar um /n no output
		
		StringInput stringInput = new StringInput();
		entrada = stringInput.receiveInput("Digite o identificador da operação:");
		
		Integer subOperacao = operacao.getSubOperacoes().get(entrada);
		operacao.executar(loginSocket, subOperacao);
	}

	/**
	 * Constroi as operações disponíveis para o usuário
	 */
	private static void construirOperacoes() {
		operacoes = new HashMap<String, Operacao>();
		operacoes.put("usuario", new OperacaoUsuario());
	}

}
