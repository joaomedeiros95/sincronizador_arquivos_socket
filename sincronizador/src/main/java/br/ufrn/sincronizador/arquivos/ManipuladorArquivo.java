/**
 * 
 */
package br.ufrn.sincronizador.arquivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joao
 *
 */
public class ManipuladorArquivo {
	
	private static final String ARQUIVO = "login.txt";
	
	public static void putString(String linha) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(ARQUIVO, true));
		out.println(linha);
		out.flush();
		out.close();
	}
	
	private static String getLinha(Integer linhaDesejada) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO));
		List<String> linhas = new ArrayList<String>();
		
		String temp;
		while((temp = reader.readLine()) != null) {
			linhas.add(temp);
		}
		reader.close();
		
		return linhas.isEmpty() || linhas.size() <= linhaDesejada ? null : linhas.get(linhaDesejada);
	}
	
	public static String getLogin() throws IOException {
		return getLinha(0);
	}
	
	public static String getCaminho() throws IOException {
		return getLinha(1);
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
