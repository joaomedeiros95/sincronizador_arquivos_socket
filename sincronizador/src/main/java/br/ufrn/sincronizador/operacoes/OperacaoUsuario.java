/**
 * 
 */
package br.ufrn.sincronizador.operacoes;

import java.util.HashMap;
import java.util.Map;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.socket.SocketClient;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

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
			solicitacao.setObjeto(logar());
		}
		
		enviar(client, solicitacao);
		imprimirResposta(client);
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
		
		return usuario;
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
