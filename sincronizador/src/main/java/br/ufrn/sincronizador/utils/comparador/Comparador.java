package br.ufrn.sincronizador.utils.comparador;

import java.util.ArrayList;

import br.ufrn.sincronizador.utils.comparador.entidades.DadosArquivo;
import br.ufrn.sincronizador.utils.comparador.entidades.Arquivos;

public class Comparador {
	
	public boolean compararCaminho(String caminho1, String caminho2, int quant1, int quant2){
		String c1 = caminho1;
		String c2 = caminho2;
		
		c1 = c1.substring(quant1);
		c2 = c2.substring(quant2);
		
		if(c1 == c2){
			return true;
		}
		else{
			return false;
		}
		
	}	
	
	
	public ArrayList<DadosArquivo> compararListasArquivos(Arquivos arquivosLocal, Arquivos arquivosRecebidos){	
		ArrayList<DadosArquivo> arquivosEnviar = new ArrayList<DadosArquivo>();
		boolean adicionar = true;
		
		
		for(int i = 0; i < arquivosLocal.getArquivos().size(); i++){
			for(int j = 0; j < arquivosRecebidos.getArquivos().size(); j++){
				if( compararCaminho(arquivosLocal.getArquivos().get(i).getPath(), arquivosRecebidos.getArquivos().get(j).getPath(), 
						arquivosLocal.getTamanhoDiretorioRaiz(), arquivosRecebidos.getTamanhoDiretorioRaiz())){
					
					if(!compararArquivos(arquivosLocal.getArquivos().get(i), arquivosRecebidos.getArquivos().get(j))) {
						adicionar = false;
					}
				}
			}
			if(adicionar){
				arquivosEnviar.add(arquivosLocal.getArquivos().get(i));
			}
		}
			
		return arquivosEnviar;		
	}
	
	public boolean compararArquivos(DadosArquivo arquivoLocal, DadosArquivo arquivoRecebido) {
		if(arquivoLocal.getConteudo() != arquivoRecebido.getConteudo()) {
			return true;
		} else {
			if(arquivoLocal.getLastModification() > arquivoRecebido.getLastModification()) {
				return true;
			} 
		}
		
		return false;
	}
	
}
