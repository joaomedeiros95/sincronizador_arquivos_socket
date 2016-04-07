package br.ufrn.sincronizador.utils.comparador.busca;
import java.io.DataOutputStream;
import java.io.File;
import java.io.PipedOutputStream;

import org.apache.commons.io.FileUtils;

public class Producer extends Thread{
	private DataOutputStream out;
	private PipedOutputStream os;
	public String diretorioAtual;
	public String palavraAlvo;
	
	public Producer ( PipedOutputStream os, String diretorio){
		this.os = os;
		out = new DataOutputStream(os);
		diretorioAtual = diretorio;
	}
	
	public void	run(){
			try{
				File diretorio = new File(diretorioAtual); 
		        File[] arquivos = diretorio.listFiles(); 
		  
		        if(arquivos != null){ 
		            int length = arquivos.length; 
		  
		            for(int i = 0; i < length; ++i){ 
		                File f = arquivos[i]; 
		            
		                if(f.isFile()){ 
	                    	out.writeUTF(f.getAbsolutePath() + ">" + f.getName() +  ">" + f.getTotalSpace() + ">"+ f.lastModified() 
	                    					+ ">" + FileUtils.readFileToByteArray(f) );
		                    out.flush();
		                    sleep(500);
		                } 
		                else if(f.isDirectory()){ 
		                   // System.out.println("Diretorio: " + f.getName()); 
		                	Producer p = new Producer(os, f.getAbsolutePath());
		                	p.start();
		                } 
		            } 
		        }
		        System.out.println("terminou a busca: " + diretorioAtual);	 
		        
			}
			catch (Exception e) { e.printStackTrace();}
		}
	

}
