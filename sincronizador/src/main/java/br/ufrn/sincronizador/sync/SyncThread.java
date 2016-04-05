/**
 * 
 */
package br.ufrn.sincronizador.sync;

/**
 * @author joao
 *
 */
public class SyncThread extends Thread {
	
	private static SyncThread thread;
	
	public static SyncThread getInstance() {
		if(thread == null) {
			thread = new SyncThread();
		}
		
		return thread;
	}

}
