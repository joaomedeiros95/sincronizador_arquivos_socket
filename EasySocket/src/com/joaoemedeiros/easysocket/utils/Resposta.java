/**
 * 
 */
package com.joaoemedeiros.easysocket.utils;

import java.io.Serializable;

/**
 * @author joao
 *
 */
public class Resposta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean resultado;
	
	private String mensagem;
	
	private Object retorno;
	
	public Resposta(boolean resultado, String mensagem, Object retorno) {
		super();
		this.resultado = resultado;
		this.mensagem = mensagem;
		this.retorno = retorno;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Object getRetorno() {
		return retorno;
	}

	public void setRetorno(Object retorno) {
		this.retorno = retorno;
	}
	
	/** Construtores de resposta */
	public static Resposta criarMensagemErro(String mensagem, Object retorno) {
		return criarMensagem(mensagem, retorno, false);
	}
	
	public static Resposta criarMensagemSucesso(String mensagem, Object retorno) {
		return criarMensagem(mensagem, retorno, true);
	}
	
	public static Resposta criarMensagem(String mensagem, Object retorno, boolean resultado) {
		return new Resposta(resultado, mensagem, retorno);
	}

}
