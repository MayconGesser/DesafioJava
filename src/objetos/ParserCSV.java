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
				retorno = filtrar(opcionais[0],opcionais[1]);
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
	
	private ArrayList<String> filtrar(String propriedade, String valor){
		ArrayList<String> retorno = new ArrayList<>();
		retorno.add(this.documentoCarregado.getCabecalho());
		Object[] filtrados = this.documentoCarregado.getColuna(propriedade).stream()
				.filter(s -> s.equals(valor)).toArray();
//		String[] valores = new String[filtrados.length];
//		
//		for(int i = 0; i<filtrados.length; i++){
//			valores[i] = (String) filtrados[i];
//		}
		
		for(int j = 0; j<filtrados.length; j++){
			retorno.add(documentoCarregado.getLinhas().get(j));
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
