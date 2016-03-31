/**
 * 
 */
package br.ufrn.servidor.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import br.ufrn.pd.dominio.Usuario;
import br.ufrn.servidor.utils.ConnectionFactory;

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
	
	/**
	 * Verifica se o usuário tem acesso ao sistema
	 * @param email
	 * @param senha
	 * @return  true -> se os dados de login foram dados corretamente
	 * 			false -> caso contrário
	 * @throws SQLException 
	 */
	public boolean verificarLogin(String email, String senha) throws SQLException {
		Connection conexao = ConnectionFactory.getConexao();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT email FROM usuario " 
					+ "WHERE email = ? AND senha = ?";
			
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, senha);
			
			ResultSet set = stmt.executeQuery();
			
			return set.next();
		} finally {
			if(stmt != null) {
				stmt.close();
			}
			conexao.close();
		}
	}

}
