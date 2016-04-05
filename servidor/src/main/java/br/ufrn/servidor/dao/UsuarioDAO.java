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
	public boolean verificarLogin(Usuario usuario) throws SQLException {
		Connection conexao = ConnectionFactory.getConexao();
		PreparedStatement stmt = null;
		try {
			String sql = "SELECT id_usuario, email FROM usuario " 
					+ "WHERE email = ? AND senha = ?";
			
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, usuario.getEmail());
			stmt.setString(2, usuario.getSenha());
			
			ResultSet set = stmt.executeQuery();
			
			return set.next();
		} finally {
			if(stmt != null) {
				stmt.close();
			}
			conexao.close();
		}
	}

	@Deprecated
	private boolean inserirLoginInfo(Usuario usuario) throws SQLException {
		Connection conexao = ConnectionFactory.getConexao();
		String insertSql = "INSERT INTO login_usuario(id_usuario, ip, ultima_entrada, mac) " 
					+ "SELECT ?, ?, ?, ? "
					+ "FROM login_usuario "
					+ "WHERE NOT EXISTS (SELECT 1 FROM login_usuario WHERE mac = ?);";
		
		PreparedStatement stmt = conexao.prepareStatement(insertSql);
		stmt.setInt(1, usuario.getId());
		stmt.setString(2, usuario.getLogin().getIp());
		stmt.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
		stmt.setString(4, usuario.getLogin().getMac());
		stmt.setString(5, usuario.getLogin().getMac());
		System.out.println(stmt);
		stmt.execute();
		stmt.close();
		
		String updateSql = "UPDATE login_usuario SET ip = ?, ultima_entrada = ? "
					+ "WHERE mac = ?;";
		
		PreparedStatement stmt2 = conexao.prepareStatement(updateSql);
		stmt2.setString(1, usuario.getLogin().getIp());
		stmt2.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
		stmt2.setString(3, usuario.getLogin().getMac());
		System.out.println(stmt2);
		stmt2.execute();
		stmt2.close();
		
		
		conexao.close();
		
		
		return true;
	}

}
