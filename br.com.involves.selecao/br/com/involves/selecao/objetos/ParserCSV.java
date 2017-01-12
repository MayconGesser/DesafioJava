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
		//diferente da FileNotFoundException essa exception deve ser lancada
		//mediante essa verificacao, visto q eh um comportamento desejado e manualmente
		//programado
		if(!caminhoArquivo.substring(caminhoArquivo.length()-4).equals(".csv")){
			throw new IllegalArgumentException("Erro: o arquivo fornecido não é do formato .csv");	
		}
		File streamArquivo = new File(caminhoArquivo);
		
		//essa linha lanca automaticamente uma FileNotFoundException, q eh
		//coberta na clausula throws do metodo 
		this.leitor = new Scanner(streamArquivo);
		this.documentoCarregado = this.carregarArquivo();
	}
	
	private DocumentoCSV carregarArquivo(){
		DocumentoCSV retorno = new DocumentoCSV();
		String cabecalho = this.leitor.nextLine();
		
		//Aqui ha uma instabilidade: o parser sempre assume q a primeira linha
		//do arquivo representa seu cabecalho.
		//Entao todo arquivo q for suprido ao parser deve obedecer ao formato
		//esperado e ter um cabecalho na primeira linha
		retorno.setCabecalho(cabecalho);
		String[] colunas = cabecalho.split(",");		
		
		//cada valor no cabecalho q foi separado pelas virgulas
		//eh adicionado como uma coluna
		for(String s : colunas){
			retorno.addColuna(s);
		}			
		
		while(this.leitor.hasNextLine()){
			String linha = this.leitor.nextLine();
			String[] valores = linha.split(",");
			for(int j = 0; j<valores.length; j++){
				//cada valor em um registro equivale a uma coluna
				//do cabecalho. Logo, o valor na posicao X de um registro
				//equivale a coluna/propriedade X do cabecalho
				retorno.inserirNaColuna(colunas[j], valores[j]);
			}
			retorno.inserirLinha(linha);
		}
		
		this.leitor.close();
		return retorno;
	}
	//Esse metodo retorna uma ArrayList devido ao fato q serve a todos
	//os 3 tipos de comandos; entao eh necessario q possa retornar 
	//consistentemente todos os 3, pois nao se sabe qual sera invocado. 
	//Poderia ter feito uma outra versao desse metodo e manter essa versao
	//como a versao sobrecarregada, mas ambas as versoes teriam o mesmo
	//nome porem tipos de retornos diferentes
	//(comandos de COUNT necessitam retornar apenas numeros), o q nao
	//seria bom para a consistencia do codigo
	public ArrayList<String> executarConsulta(Comando comando, String[] opcionais){
		//opcionais[0] : Propriedade
		//opcionais[1] : Valor da propriedade
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
	
	public int getNumeroTotalLinhas(){
		return this.documentoCarregado.getNumeroTotalRegistros();
	}
	
	private int contarDistintos(ArrayList<String> coluna){
		int unicos = (int) coluna.stream().distinct().count();
		return unicos;
	}
	
	private ArrayList<String> filtrar(String propriedade, String valor){
		ArrayList<Integer> indices = new ArrayList<>();
		ArrayList<String> retorno = new ArrayList<>();
		retorno.add(this.documentoCarregado.getCabecalho());
		ArrayList<String> coluna = this.documentoCarregado.getColuna(propriedade);
		
		//A intencao desse codigo eh iterar pelos valores da coluna
		//especificada e verificar quais desses valores sao iguais
		//ao valor procurado. A posicao da linha eh gravada na ArrayList
		//indices para q seja iterada e suas entradas sirvam para
		//acessar a ArrayList linhas nas posicoes onde os valores ocorrem
		//zelando assim pela estabilidade do comportamento da aplicacao,
		//q procurara por valores apenas na coluna especificada, e nao 
		//na linha inteira
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
}
