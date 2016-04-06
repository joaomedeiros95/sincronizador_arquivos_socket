/**
 * 
 */
package br.ufrn.sincronizador.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.ufrn.pd.dominio.Arquivo;

/**
 * @author joao
 *
 */
public class ArquivoUtils {
	
	public static Arquivo getArquivo(String caminho, String nome) {
		Arquivo arquivo = null;
		
		try {
			File file = new File(Session.getInstance().getValue("caminho") + caminho + nome);
			byte[] array = FileUtils.readFileToByteArray(file);
			arquivo = new Arquivo();
			arquivo.setCaminho(caminho);
			arquivo.setArquivo(array);
			arquivo.setNome(nome);
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao ler o arquivo");
			e.printStackTrace();
		}
		
		return arquivo;
	}
	
	public static Arquivo getArquivo(String fullCaminho) {
		Arquivo arquivo = null;
		
		try {
			File file = new File(Session.getInstance().getValue("caminho") + fullCaminho);
			byte[] array = FileUtils.readFileToByteArray(file);
			arquivo = new Arquivo();
			arquivo.setCaminho(fullCaminho.replace(file.getName(), ""));
			arquivo.setArquivo(array);
			arquivo.setNome(file.getName());
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao ler o arquivo");
			e.printStackTrace();
		}
		
		return arquivo;
	}

}
