package objetos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import enums.Comando;

public class ParserCSV {
	
	private Scanner leitor;
	private DocumentoCSV documentoCarregado;
	private boolean prontoParaLer;
	
	public ParserCSV(String caminhoArquivo){
		File streamArquivo = new File(caminhoArquivo);
		try {	//TODO: Tirar esse try-catch; objeto pode acabar "meio criado"
			this.leitor = new Scanner(streamArquivo);
			this.documentoCarregado = this.carregarArquivo();
			this.prontoParaLer = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.prontoParaLer = false;
		}
	}
	
	private DocumentoCSV carregarArquivo(){
		DocumentoCSV retorno = new DocumentoCSV();
		String primeiraLinha = this.leitor.nextLine();
		String[] colunas = primeiraLinha.split(",");
		
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
	
	public ArrayList<String> executarConsulta(Comando comando, String opcionais){
		ArrayList<String> retorno = new ArrayList<>();
		switch(comando){
			case COUNT_ALL:
				retorno.add(String.valueOf(this.documentoCarregado.getNumeroTotalRegistros()));
				break;
			case COUNT_DISTINCT:
				retorno = this.documentoCarregado.getColuna(opcionais);
				break;
			case FILTER:
				break;
			default:
				break;
		}
		return retorno;
	}
	
	//metodo de conveniencia para checar se arquivo ja foi carregado
	public boolean isProntoParaLer(){
		return this.prontoParaLer;
	}
	
	public String[] getNomesColunasArquivo(){
		return this.documentoCarregado.getNomesColunas();
	}
	
	public int getNumeroTotalColunas(){
		return this.documentoCarregado.getNumeroTotalColunas();
	}
}
