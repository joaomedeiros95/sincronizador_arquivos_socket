/**
 * 
 */
package com.joaoemedeiros.easysocket.utils;

import java.io.Serializable;

/**
 * @author joao
 *
 */
public class Solicitacao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String operacao;
	
	private Object objeto;

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

}
