/**
 * 
 */
package br.ufrn.servidor.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author joao
 *
 */
public class ConnectionFactory {
	
	public static final String DRIVER = "org.postgresql.Driver";
	public static final String URL = "jdbc:postgresql://localhost/sincronizador";
	public static final String USUARIO = "sincronizador";
	public static final String SENHA = "sync123";
	
	public static Connection getConexao() throws SQLException {
		return DriverManager.getConnection(URL, USUARIO, SENHA);
	}
	
}
