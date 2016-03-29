/**
 * 
 */
package br.ufrn.sincronizador.dominio;

import java.io.Serializable;
import java.util.Date;

/**
 * @author joao
 *
 */
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String usuario;
	private String email;
	private String senha;
	private Date dataCadastro;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	
}
