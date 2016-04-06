/**
 * 
 */
package br.ufrn.sincronizador.operacoes;

import java.io.IOException;
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
import br.ufrn.sincronizador.arquivos.ManipuladorArquivo;
import br.ufrn.sincronizador.input.StringInput;
import br.ufrn.sincronizador.utils.Session;

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
			
			enviar(client, solicitacao);
			imprimirResposta(client);
		} else if (subOperacao == LOGAR) {
			boolean enviar = false;
			
			try {
				ManipuladorArquivo.criarArquivo();
				
				String email = ManipuladorArquivo.getLogin();
				Usuario usuario = null;
				
				if(email == null) {
					usuario = (Usuario) logar();
					solicitacao.setOperacao(Operations.LOGIN);
					solicitacao.setObjeto(usuario);
					enviar = true;
				}
				
				if(enviar) {
					enviar(client, solicitacao);
					if(getResultadoResposta(client)) {
						ManipuladorArquivo.putString(usuario.getEmail());
						
					}
				} else {
					System.out.println("Usuário " + email + " já logado!");
				}
				
			} catch (IOException e) {
				System.out.println("Ocorreu um erro ao fazer login!");
				return;
			}
			
		} else {
			System.out.println("Digite uma subOperação válida");
			return;
		}
		
		
	}

	private Object logar() {
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
