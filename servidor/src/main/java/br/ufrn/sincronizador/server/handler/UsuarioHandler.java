/**
 * 
 */
package br.ufrn.sincronizador.server.handler;

import java.sql.SQLException;

import com.joaoemedeiros.easysocket.exception.EasySocketException;
import com.joaoemedeiros.easysocket.handler.Connection;
import com.joaoemedeiros.easysocket.handler.MessageHandler;

import br.ufrn.sincronizador.dao.UsuarioDAO;
import br.ufrn.sincronizador.dominio.Usuario;

/**
 * @author joao
 *
 */
public class UsuarioHandler implements MessageHandler {

	public void onReceive(Connection conexao, Object objeto) {
		Usuario usuario = (Usuario) objeto;
		
		try {
			new UsuarioDAO().criar(usuario);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexao.println("Erro ao criar usuário " + usuario.getEmail() + "!");
			} catch (EasySocketException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			conexao.println("Usuário criado com sucesso!");
		} catch (EasySocketException e) {
			e.printStackTrace();
		}
	}

}
