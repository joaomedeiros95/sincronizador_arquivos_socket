/**
 * 
 */
package br.ufrn.sincronizador.sync;

import com.joaoemedeiros.easysocket.socket.SocketClient;

/**
 * @author joao
 *
 */
public class SyncMaster extends Thread {
	
	private String ip;
	private SocketClient client;

	public SyncMaster(String ip) {
		super();
		this.ip = ip;
	}
	
	@Override
	public void run() {
		while(true) {
			
			
			dormir();
		}
	}
	
	private void dormir() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
