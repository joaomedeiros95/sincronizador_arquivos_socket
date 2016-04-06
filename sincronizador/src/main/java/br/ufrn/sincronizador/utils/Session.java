/**
 * 
 */
package br.ufrn.sincronizador.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author joao
 *
 */
public class Session {
	
	private Map<String, String> valores;
	private static Session session;
	
	public Session() {
		valores = new HashMap<String, String>();
	}
	
	public static Session getInstance() {
		if(session == null) {
			session = new Session();
		}
		
		return session;
	}
	
	public void putValue(String key, String value) {
		valores.put(key, value);
	}
	
	public String getValue(String key) {
		return valores.get(key);
	}

}
