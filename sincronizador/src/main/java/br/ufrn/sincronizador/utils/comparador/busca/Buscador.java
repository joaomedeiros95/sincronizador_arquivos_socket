package br.ufrn.sincronizador.utils.comparador.busca;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

import br.ufrn.sincronizador.utils.comparador.entidades.DadosArquivo;
import br.ufrn.sincronizador.utils.comparador.entidades.Arquivos;

public class Buscador {
	
	public void buscar(Arquivos arq){
		
		String diretorio = arq.getDiretorio();
		ArrayList<DadosArquivo> arquivos = arq.getArquivos();
		
			try{
				PipedOutputStream out = new PipedOutputStream();
				PipedInputStream in = new PipedInputStream(out);				
							
				Producer p = new Producer (out, diretorio);
				Consumer c = new Consumer (in, arquivos);
				p.start();
				c.start();
				
				while(c.isAlive()){
					Thread.sleep(100);
				}								
			}
			catch(RuntimeException e ){			
				//não tratada
				
			} catch (IOException e) {				
				//não tratada
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
							
	}
	
	
	public void removerArquivosDaPastaNegra(ArrayList<DadosArquivo> todosarquivos, String caminhoPastaNegra ){
		
		for(int i = 0; i < todosarquivos.size(); i++){			
			if(todosarquivos.get(i).getPath().contains(caminhoPastaNegra)){
				todosarquivos.remove(i);
				i--;
			}			
		}
	}
	
}
