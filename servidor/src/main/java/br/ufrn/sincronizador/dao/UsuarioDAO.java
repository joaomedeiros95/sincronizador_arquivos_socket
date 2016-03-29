/**
 * 
 */
package br.ufrn.sincronizador.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import br.ufrn.sincronizador.dominio.Usuario;
import br.ufrn.sincronizador.utils.ConnectionFactory;

/**
 * @author joao
 *
 */
public class UsuarioDAO {
	
	public void criar(Usuario usuario) throws SQLException {
		System.out.println("Gravando usuário " + usuario.getEmail());
		
		Connection conexao = ConnectionFactory.getConexao();
		String sql = "INSERT INTO usuario(nome, email, senha, data_cadastrado) " 
					+ "VALUES (?, ?, ?, ?);";
		
		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.setString(1, usuario.getNome());
		stmt.setString(2, usuario.getEmail());
		stmt.setString(3, usuario.getSenha());
		stmt.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
		
		stmt.execute();
		stmt.close();
		
		System.out.println("Usuário " + usuario.getEmail() + " criado!");
		
		conexao.close();
	}

}
