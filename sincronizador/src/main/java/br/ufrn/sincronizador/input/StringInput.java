/**
 * 
 */
package br.ufrn.sincronizador.input;

import java.util.Scanner;

/**
 * @author joao
 *
 */
public class StringInput implements Input {
	
	private Scanner scanner;

	public String receiveInput(String mensagem) {
		scanner = new Scanner(System.in);
		
		System.out.println(mensagem);
		String digitado = scanner.nextLine();
		
		while(digitado == "" || digitado == null) {
			System.out.println("Digite um valor válido");
			digitado = scanner.nextLine();
		}
		
		return digitado;
	}

}
 