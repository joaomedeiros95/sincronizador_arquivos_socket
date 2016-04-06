package br.ufrn.sincronizador.utils.comparador.entidades;
import java.io.Serializable;
import java.util.ArrayList;

public class Arquivos implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<DadosArquivo> arquivos;
	 private String diretorioRaiz;
	 private int tamanhoDiretorioRaiz;

	 public Arquivos(String diretorio) {
			super();
			this.arquivos = new ArrayList<DadosArquivo>();
			this.diretorioRaiz = diretorio;
			tamanhoDiretorioRaiz = diretorioRaiz.length();
		}
	 
	public Arquivos(ArrayList<DadosArquivo> arquivos, String diretorio) {
		super();
		this.arquivos = arquivos;
		this.diretorioRaiz = diretorio;
		tamanhoDiretorioRaiz = diretorioRaiz.length();
	}

	public ArrayList<DadosArquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(ArrayList<DadosArquivo> arquivos) {
		this.arquivos = arquivos;
	}

	public String getDiretorio() {
		return diretorioRaiz;
	}

	public void setDiretorio(String diretorio) {
		this.diretorioRaiz = diretorio;
	}
	
	public int getTamanhoDiretorioRaiz() {
		return tamanhoDiretorioRaiz;
	}

	public void setTamanhoDiretorioRaiz(int tamanhoDiretorioRaiz) {
		this.tamanhoDiretorioRaiz = tamanhoDiretorioRaiz;
	}
	 
	
	 

}
