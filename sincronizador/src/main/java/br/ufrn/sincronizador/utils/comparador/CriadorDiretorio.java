package br.ufrn.sincronizador.utils.comparador;

import java.io.File;

public class CriadorDiretorio {
	
	public static String criarDiretorio(String caminho,String nome){
		//TODO: lembrar de tirar o nome do arquivo
		String c1 = caminho;
				
		String novoCaminho = c1;
		
		File file = new File(novoCaminho);
		if(file.exists()){
			return novoCaminho;
		}
		else{
			file.mkdirs();
			return novoCaminho;
		}
		
	}
	
}
