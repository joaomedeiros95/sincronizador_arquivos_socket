package br.ufrn.sincronizador.utils.comparador.entidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class DadosArquivo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String path;
	private String name;
	private String totalSpace;
	private byte[] conteudo;
	private long lastModification;
	
	
	
	public DadosArquivo(String path, String name, String totalSpace, String lastModification, byte[] conteudo) {		
		super();
		this.path = path;
		this.name = name;
		this.totalSpace = totalSpace;			
		this.lastModification = Long.parseLong(lastModification);
		this.conteudo = conteudo;
	}
	
	public String getDateLastModification(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return sdf.format(lastModification);
	}

	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getTotalSpace() {
		return totalSpace;
	}



	public void setTotalSpace(String totalSpace) {
		this.totalSpace = totalSpace;
	}



	public Long getLastModification() {
		return lastModification;
	}



	public void setLastModification(Long lastModification) {
		this.lastModification = lastModification;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}

	public void setLastModification(long lastModification) {
		this.lastModification = lastModification;
	}

	@Override
	public String toString() {
		return "Arquivo [path=" + path + ", name=" + name + ", totalSpace=" + totalSpace + ", lastModification="
				+ lastModification + "]";
	}
	
	
}
