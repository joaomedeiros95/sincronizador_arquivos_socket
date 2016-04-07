/**
 * 
 */
package br.ufrn.pd.dominio;

import java.io.Serializable;

/**
 * @author joao
 *
 */
public class Arquivo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String caminho;
	
	private String nome;

	private byte[] arquivo;

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getCaminho() {
		return caminho.replace("/", "\\");
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
