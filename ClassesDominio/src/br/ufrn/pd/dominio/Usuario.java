/**
 * 
 */
package br.ufrn.pd.dominio;

import java.io.Serializable;
import java.util.Date;

/**
 * @author joao
 *
 */
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private Date dataCadastro;
	private LoginUsuario login;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public LoginUsuario getLogin() {
		return login;
	}
	public void setLogin(LoginUsuario login) {
		this.login = login;
	}
	
}
