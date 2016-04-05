/**
 * 
 */
package br.ufrn.sincronizador.arquivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author joao
 *
 */
public class ManipuladorArquivo {
	
	private static final String ARQUIVO = "login.txt";
	
	public static void putString(String linha) throws IOException {
		PrintWriter out = new PrintWriter(ARQUIVO);
		out.println(linha);
		out.flush();
		out.close();
	}
	
	public static String getString() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO));
		String linha = reader.readLine();
		reader.close();
		
		return linha;
	}
	
	public static void criarArquivo() throws IOException {
		File file = new File(ARQUIVO);
		if(!file.isFile()) {
			file.createNewFile();
		}
	}
	
	public static void clearArquivo() throws IOException {
		File file = new File(ARQUIVO);
		if(file.isFile()) {
			file.delete();
			criarArquivo();
		}
	}

}
