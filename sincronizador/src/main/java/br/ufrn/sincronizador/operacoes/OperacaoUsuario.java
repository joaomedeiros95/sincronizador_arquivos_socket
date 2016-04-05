/**
 * 
 */
package br.ufrn.sincronizador.operacoes;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

import br.ufrn.pd.dominio.LoginUsuario;
import br.ufrn.pd.dominio.Usuario;
import br.ufrn.sincronizador.input.StringInput;

/**
 * @author joao
 *
 */
public class OperacaoUsuario extends Operacao {
	
	public static final Integer CRIAR = 1;
	public static final Integer LOGAR = 2;
	
	private StringInput input;
	
	public void executar(SocketClient client, Integer subOperacao) throws EasySocketException {
		Solicitacao solicitacao = new Solicitacao();
		input = new StringInput();
		
		if(subOperacao == CRIAR) {
			solicitacao.setOperacao(Operations.CADASTRARUSUARIO);
			solicitacao.setObjeto(criarUsuario());
		} else if (subOperacao == LOGAR) {
			solicitacao.setOperacao(Operations.LOGIN);
			try {
				solicitacao.setObjeto(logar());
			} catch (SocketException e) {
				System.out.println("Ocorreu um erro ao recuperar suas informações de login.");
				return;
			} catch (UnknownHostException e) {
				System.out.println("Ocorreu um erro ao recuperar suas informações de login.");
				return;
			}
		} else {
			System.out.println("Digite uma subOperação válida");
			return;
		}
		
		enviar(client, solicitacao);
		imprimirResposta(client);
	}

	private Object logar() throws SocketException, UnknownHostException {
		String email;
		String senha;
		
		System.out.println("Digite as informações do usuário: ");
		email = input.receiveInput("Email: ");
		senha = input.receiveInput("Senha: ");
		
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setSenha(senha);
//		preencherInformacoesExtras(usuario);
		
		return usuario;
	}

	@Deprecated
	private void preencherInformacoesExtras(Usuario usuario) throws SocketException, UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();
		
		usuario.setLogin(new LoginUsuario());
		usuario.getLogin().setIp(ip.getHostAddress());
		
		byte[] mac = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
		}
		
		usuario.getLogin().setMac(sb.toString());
	}

	private Object criarUsuario() {
		String email;
		String senha;
		String nome;
		
		System.out.println("Digite as informações do usuário: ");
		email = input.receiveInput("Email: ");
		senha = input.receiveInput("Senha: ");
		nome = input.receiveInput("Nome: ");
		
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		return usuario;
	}

	@Override
	public Map<String, Integer> getSubOperacoes() {
		Map<String, Integer> subOperacoes = new HashMap<String, Integer>();
		subOperacoes.put("criar_usuario", CRIAR);
		subOperacoes.put("logar", LOGAR);
		
		return subOperacoes;
	}

}
