package br.com.involves.selecao.apresentacao;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import br.com.involves.selecao.objetos.ParserCSV;
import br.com.involves.selecao.objetos.enums.Comando;

public class Tela {
	private Scanner console;
	private ParserCSV parser;
		
	public Tela(){
		this.console = new Scanner(System.in);		
	}
	
	public void iniciar(){
		criarParser();
		exibirOpcoesComandos();
		determinarInput();
	}
	
	private void criarParser(){
		boolean caminhoEhValido = false;
		ParserCSV parser = null;
		do{
			System.out.println("Forneça o caminho para o arquivo .csv:");
			System.out.print("\n>>>");
			String caminhoArquivo = console.nextLine();
			try{
				parser = new ParserCSV(caminhoArquivo);
				caminhoEhValido = true;
			}catch(FileNotFoundException e){
				//Aqui eh necessario exibir uma mensagem "custom", para esclarecer
				//a natureza do erro
				System.out.println("\nErro: Arquivo ou diretório não encontrado.");
				rotinaErroInput();
			}
			catch(IllegalArgumentException ex){
				//Como essa excecao foi lancada com uma mensagem propria
				//nao eh necessaria uma outra mensagem, basta exibir a mensagem
				//da propria excecao
				System.out.println(ex.getMessage());
				rotinaErroInput();
			}
		}while(!caminhoEhValido);
		this.parser = parser;
	}
	
	private void exibirOpcoesComandos(){
		System.out.println("\n-----------------------\n");
		System.out.println("\nComandos disponíveis:");
		System.out.println(" 1 - count *.");
		System.out.println(" 2 - count distinct [propriedade].");
		System.out.println(" 3 - filter [propriedade] [valor].");
		System.out.println(" 4 - Sair do programa.");
		System.out.print("\n>>>");
	}
	
	private void exibeOpcoesPropriedades(){
		String[] colunasArquivo = this.parser.getNomesColunasArquivo();
		for(int i = 0; i<colunasArquivo.length; i++){
			System.out.println(" " + (i+1) + " - " + colunasArquivo[i] + "\n");
		}
		System.out.print("\n>>>");
	}
	
	private void determinarInput(){
		//esse metodo avalia o input como String como uma maneira
		//de ser mais estavel: se algum input desconhecido for 
		//fornecido o proprio switch case o tratara; se a opcao 
		//fosse convertida para o formato numerico todo o tratamento 
		//de excecao para realizar a conversao seria necessario, 
		//oq ocasionaria um overhead desnecessario
		while(true){
			String opcao = this.console.nextLine();
			switch(opcao){
				case "1":
					String registros = this.parser.executarConsulta(Comando.COUNT_ALL, null).get(0);
					System.out.println("Total de registros no documento: " + registros);
					break;
					
				case "2":		
					String nomePropriedadeContar = escolherPropriedade(Comando.COUNT_DISTINCT);
					if(nomePropriedadeContar.equals("a")){
						exibirOpcoesComandos();
						continue;
					}
					String[] argsConsultaContar = {nomePropriedadeContar};				
					ArrayList<String> contagemUnicos = this.parser.executarConsulta(Comando.COUNT_DISTINCT, argsConsultaContar);
					System.out.println("-------------------------------\n");		
					System.out.println("Número de registros únicos para a propriedade " 
							+ argsConsultaContar[0] + ": " + contagemUnicos.get(0));				
					break;
					
				case "3":				
					String nomePropriedadeFiltrar = escolherPropriedade(Comando.FILTER);
					if(nomePropriedadeFiltrar.equals("a")){
						exibirOpcoesComandos();
						continue;
					}
					System.out.println("\n----------------------\n");
					System.out.println("Selecione um valor para a propriedade a ser filtrada:\n");
					System.out.print("\n>>>");
					String valor = console.nextLine();
					String[] argsConsultaFiltrar = {nomePropriedadeFiltrar,valor};
					ArrayList<String> filtrados = this.parser.executarConsulta(Comando.FILTER, argsConsultaFiltrar);
					
					for(String linha : filtrados){
						System.out.println(linha);
					}
					
					break;
					
				case "4":
					return;
				
				default:
					System.out.println("Opção desconhecida.");
					break;
			}
			exibirOpcoesComandos();
		}	
	}
	
	private String escolherPropriedade(Comando comando){
		boolean inputValido = false;
		//aqui a conversao do input para o formato numerico
		//se faz necessaria, jah q a numeracao do input eh utilizada
		//para acessar o array q contem as colunas/propriedades do arquivo
		int propriedade = 0;
		do{
			System.out.println(
					((comando.equals(Comando.COUNT_DISTINCT) ? 
							"Selecione a propriedade cujos valores devem ser únicos "
							: "Selecione a propriedade a ser filtrada ") + 
								"(digite \"a\" para abortar):"));
			exibeOpcoesPropriedades();
			String input = console.nextLine();
			if(input.equals("a")){
				return input;
			}
			try{
				propriedade = Integer.parseInt(input);
				if(propriedade > this.parser.getNumeroTotalColunas()
						|| propriedade <= 0){
					System.out.println("\nOpção desconhecida. "
							+ "Favor fornecer uma opção válida.\n");
					System.out.println("---------------------------\n");
					continue;
				}
			}catch(NumberFormatException e){
				System.out.println("Favor fornecer apenas entradas numéricas.\n");
				System.out.println("-----------------------\n");
			}
			inputValido = propriedade > 0 ? true : false;
		}while(!inputValido);
		
		String nomePropriedade = this.parser.getNomesColunasArquivo()[propriedade-1];
		return nomePropriedade;
	}
	
	private void rotinaErroInput(){
		System.out.println("Gostaria de tentar novamente? (S/N)");
		System.out.print("\n>>>");
		String opcao = console.nextLine();
		//trata-se apenas o caso do input q confirma a intencao de 
		//tentar novamente por questoes de estabilidade da aplicacao
		//eh desnecessario testar qualquer outro input
		if(!opcao.toUpperCase().equals("S")){
			System.exit(1);
		}
	}
}
