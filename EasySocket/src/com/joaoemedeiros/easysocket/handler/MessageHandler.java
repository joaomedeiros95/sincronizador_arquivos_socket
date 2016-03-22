/**
 * 
 */
package com.joaoemedeiros.easysocket.handler;

/**
 * @author joao
 *
 */
public interface MessageHandler {
	public void onReceive(Connection connection, Object objeto);
}
