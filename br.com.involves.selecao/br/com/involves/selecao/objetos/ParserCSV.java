package br.com.involves.selecao.objetos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import br.com.involves.selecao.objetos.enums.Comando;

public class ParserCSV {
	
	private Scanner leitor;
	private DocumentoCSV documentoCarregado;
	
	public ParserCSV(String caminhoArquivo) throws FileNotFoundException, IllegalArgumentException{
		//checagem para determinar se arquivo fornecido eh do tipo correto
		if(!caminhoArquivo.substring(caminhoArquivo.length()-4).equals(".csv")){
			throw new IllegalArgumentException("Erro: o arquivo fornecido não é do formato .csv");	
		}
		File streamArquivo = new File(caminhoArquivo);
		this.leitor = new Scanner(streamArquivo);
		this.documentoCarregado = this.carregarArquivo();
	}
	
	private DocumentoCSV carregarArquivo(){
		DocumentoCSV retorno = new DocumentoCSV();
		String cabecalho = this.leitor.nextLine();
		retorno.setCabecalho(cabecalho);
		String[] colunas = cabecalho.split(",");		
		for(String s : colunas){
			retorno.addColuna(s);
		}			
		while(this.leitor.hasNextLine()){
			String linha = this.leitor.nextLine();
			String[] valores = linha.split(",");
			for(int j = 0; j<valores.length; j++){
				retorno.inserirNaColuna(colunas[j], valores[j]);
			}
			retorno.inserirLinha(linha);
		}
		
		this.leitor.close();
		return retorno;
	}
	
	public ArrayList<String> executarConsulta(Comando comando, String[] opcionais){
		ArrayList<String> retorno = new ArrayList<>();
		switch(comando){
			case COUNT_ALL:
				retorno.add(String.valueOf(this.documentoCarregado.getNumeroTotalRegistros()));
				break;
			case COUNT_DISTINCT:
				ArrayList<String> coluna = this.documentoCarregado.getColuna(opcionais[0]);
				retorno.add(String.valueOf(contarDistintos(coluna)));
				break;
			case FILTER:
				retorno = filtrarPorColuna(opcionais[0],opcionais[1]);
				break;
			default:
				break;
		}
		return retorno;
	}
	
	private int contarDistintos(ArrayList<String> coluna){
		int unicos = (int) coluna.stream().distinct().count();
		return unicos;
	}
	
	private ArrayList<String> filtrarPorColuna(String propriedade, String valor){
		ArrayList<Integer> indices = new ArrayList<>();
		ArrayList<String> retorno = new ArrayList<>();
		retorno.add(this.documentoCarregado.getCabecalho());
		ArrayList<String> coluna = this.documentoCarregado.getColuna(propriedade);
		for(int i = 0; i<coluna.size(); i++){
			if(coluna.get(i).equals(valor)){
				indices.add(i);
			}
		}
		for(int j = 0; j<indices.size(); j++){
			//+2 leva em conta tanto a numeracao da ArrayList, q comeca
			//em 0, quanto o cabecalho
			String linhaRetorno = "Linha " + (indices.get(j) + 2) + ": "
					+ this.documentoCarregado.getLinhas().get(indices.get(j));
			retorno.add(linhaRetorno);
		}
		return retorno;
	}
	
	public String[] getNomesColunasArquivo(){
		return this.documentoCarregado.getNomesColunas();
	}
	
	public int getNumeroTotalColunas(){
		return this.documentoCarregado.getNumeroTotalColunas();
	}
	
	public int getNumeroTotalLinhas(){
		return this.documentoCarregado.getContagemLinhas();
	}
}
