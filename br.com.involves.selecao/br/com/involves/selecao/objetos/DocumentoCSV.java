package br.com.involves.selecao.objetos;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DocumentoCSV {
	private String cabecalho; 
	
	//Eh necessario usar um LinkedHashMap pois a ordem em q as
	//colunas foram inseridas precisa ser mantida para um bom
	//funcionamento da aplicacao
	private LinkedHashMap<String,ArrayList<String>> colunas;
	private ArrayList<String> linhas;
	
	public DocumentoCSV(){
		this.colunas = new LinkedHashMap<>();
		this.linhas = new ArrayList<>();
	}
	
	public void addColuna(String nomeColuna){
		this.colunas.put(nomeColuna, new ArrayList<>());
	}
	
	public void inserirNaColuna(String coluna,String valor){
		this.colunas.get(coluna).add(valor);
	}
	
	public void inserirLinha(String linha){	
		this.linhas.add(linha);
	}
	
	public ArrayList<String> getColuna(String coluna) {
		return colunas.get(coluna);
	}	
	
	public int getNumeroTotalRegistros(){
		return this.linhas.size();
	}
	
	public int getNumeroTotalColunas(){
		return this.colunas.size();
	}
	
	public String[] getNomesColunas(){
		String[] retorno = new String[this.colunas.size()];
		retorno = this.colunas.keySet().toArray(retorno);
		return retorno;
	}
	
	public ArrayList<String> getLinhas(){
		return this.linhas;
	}
		
	public void setCabecalho(String cabecalho){
		this.cabecalho = cabecalho;
	}
	
	public String getCabecalho(){
		return cabecalho;
	}
}
