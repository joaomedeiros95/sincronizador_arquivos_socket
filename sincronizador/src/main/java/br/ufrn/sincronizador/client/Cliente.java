/**
 * 
 */
package br.ufrn.sincronizador.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.socket.SocketMulticast;
import com.joaoemedeiros.easysocket.utils.Services;

import br.ufrn.sincronizador.arquivos.ManipuladorArquivo;
import br.ufrn.sincronizador.client.handler.MulticastHandlerImpl;
import br.ufrn.sincronizador.input.StringInput;
import br.ufrn.sincronizador.operacoes.Operacao;
import br.ufrn.sincronizador.operacoes.OperacaoArquivo;
import br.ufrn.sincronizador.operacoes.OperacaoUsuario;
import br.ufrn.sincronizador.utils.Session;

/**
 * @author joao
 *
 */
public class Cliente {
	
	public static final String SERVIDORLOGIN = "localhost";
	public static final String MULTICASTSERVER = "239.10.10.15";
	
	private static Map<String, Operacao> operacoes;
	private static boolean finish;
	
	private static SocketClient loginSocket;
	private static SocketMulticast multiSocket;
	
	public static void main(String[] args) {
		construirOperacoesAntesLogin();
		
		try {
			loginSocket = new SocketClient(SERVIDORLOGIN, Services.LOGINSERVICE);
			
			while(!verificarLogin()) {
				solicitarOperacao();
			}
			
			solicitarCaminhoRaiz();
			
			construirOperacoesDepoisLogin();
			multiSocket = new SocketMulticast(MULTICASTSERVER, Services.MULTISERVICE, new MulticastHandlerImpl());
			
			while(!finish) {
				solicitarOperacao();
			}
			
		} catch (EasySocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void solicitarCaminhoRaiz() {
		StringInput input = new StringInput();
		String caminho = input.receiveInput("Digite o caminho da pasta que deseja sincronizar: ");
		Session.getInstance().putValue("caminho", caminho);
		//TODO: ManipuladorArquivo.putString(caminho);
	}

	private static boolean verificarLogin() throws IOException {
		ManipuladorArquivo.criarArquivo();
		String email = ManipuladorArquivo.getLogin();
		
		if(email != null) {
			System.out.println("Usuário " + email + " logado ao sistema!");
			return true;
		} else {
			System.out.println("Prezado usuário, favor logue ao sistema ou crie uma conta!");
			return false;
		}
	}

	private static void solicitarOperacao() throws EasySocketException, IOException {
		System.out.println("Operações Disponíveis (use 'sair' para sair ou 'deslogar' para deslogar): ");
		for(Map.Entry<String, Operacao> operacao : operacoes.entrySet()) {
			System.out.print("- " + operacao.getKey() + " ");
		}
		System.out.println("");		//Dar um /n no output
		
		StringInput stringInput = new StringInput();
		String entrada = stringInput.receiveInput("Digite o identificador da operação:");
		
		if(entrada.equalsIgnoreCase("sair")) {
			finish = true;
		} else if (entrada.equalsIgnoreCase("deslogar")) {
			ManipuladorArquivo.clearArquivo();
			finish = true;
		} else {
			solicitarSubOperacao(entrada);
		}
	}

	private static void solicitarSubOperacao(String entrada) throws EasySocketException {
		SocketClient socket = loginSocket;
		
		Operacao operacao = operacoes.get(entrada);
		if(operacao == null) {
			System.out.println("Digite uma operação válida");
			return;
		}
		
		if(operacao instanceof OperacaoUsuario) {
			socket = loginSocket;
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
	 * Constroi as operações disponíveis para o usuário antes do login
	 */
	private static void construirOperacoesAntesLogin() {
		operacoes = new HashMap<String, Operacao>();
		operacoes.put("usuario", new OperacaoUsuario());
	}
	
	/**
	 * Constroi as operações disponíveis para o usuário
	 */
	private static void construirOperacoesDepoisLogin() {
		operacoes = new HashMap<String, Operacao>();
		operacoes.put("arquivo", new OperacaoArquivo());
	}

	public static SocketMulticast getMultiSocket() {
		return multiSocket;
	}

	public static void setMultiSocket(SocketMulticast multiSocket) {
		Cliente.multiSocket = multiSocket;
	}

}
