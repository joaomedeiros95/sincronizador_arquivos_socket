package br.ufrn.sincronizador.utils.comparador.busca;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.util.ArrayList;

import br.ufrn.sincronizador.utils.comparador.entidades.DadosArquivo;

public class Consumer extends Thread {
	private DataInputStream in;
	private PipedInputStream oss;
	private ArrayList<DadosArquivo> arquivos;
	


	public Consumer( PipedInputStream is, ArrayList<DadosArquivo> arquivos ){
		in = new DataInputStream(is);
		oss = is;
		this.arquivos = arquivos;
	}
	
	public void run() {
		String informacoes;
		while(true){			

				try {
					informacoes = in.readUTF();
					if (informacoes != null){
						//System.out.println("informações:" + informacoes);
						String infoArquivo[] = informacoes.split(">");
						arquivos.add(new DadosArquivo(infoArquivo[0],infoArquivo[1], infoArquivo[2], infoArquivo[3]));						
					}
					
				} catch (IOException e) {
					throw new RuntimeException();					
				}
				
		}
	}
		
}
