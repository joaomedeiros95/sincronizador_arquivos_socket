/**
 * 
 */
package br.ufrn.servidor.server.handler;

import java.sql.SQLException;

import com.joaoemedeiros.easysocket.handler.Connection;
import com.joaoemedeiros.easysocket.handler.MessageHandler;
import com.joaoemedeiros.easysocket.utils.Operations;
import com.joaoemedeiros.easysocket.utils.Resposta;
import com.joaoemedeiros.easysocket.utils.Solicitacao;

import br.ufrn.pd.dominio.Usuario;
import br.ufrn.servidor.dao.UsuarioDAO;

/**
 * @author joao
 *
 */
public class UsuarioHandler extends MessageHandler {
	
	public void onReceive(Connection conexao, Solicitacao solicitacao) {
		super.conexao = conexao;
		super.solicitacao = solicitacao;
		Resposta resposta = null;
		
		try {
			if(solicitacao.getOperacao().equalsIgnoreCase(Operations.CADASTRARUSUARIO)) {
				resposta = cadastrarUsuario();
			} else if(solicitacao.getOperacao().equalsIgnoreCase(Operations.LOGIN)) {
				resposta = loginUsuario();
			}
		} catch (SQLException e) {
			enviarResposta(Resposta.criarMensagemErro("Erro ao realizar sua operação!", null));
			e.printStackTrace();
		}
		
		enviarResposta(resposta);
	}


	private Resposta loginUsuario() throws SQLException {
		Usuario usuario = (Usuario) solicitacao.getObjeto();
		boolean correto = new UsuarioDAO().verificarLogin(usuario.getEmail(), usuario.getSenha());
		
		if(correto) {
			return Resposta.criarMensagemSucesso("Usuário logado com sucesso!", null);
		} else {
			return Resposta.criarMensagemErro("Senha ou Email errados!", null);
		}
	}

	private Resposta cadastrarUsuario() throws SQLException {
		Usuario usuario = (Usuario) solicitacao.getObjeto();
		
		new UsuarioDAO().criar(usuario);
		
		return Resposta.criarMensagemSucesso("Usuário " + usuario.getEmail() + " criado com sucesso!", null);
	}

}
