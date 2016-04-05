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
public class LoginUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String ip;
	
	private Date ultimoLogin;
	
	private String mac;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}
