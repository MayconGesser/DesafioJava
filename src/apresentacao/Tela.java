package apresentacao;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import enums.Comando;
import objetos.ParserCSV;

public class Tela {
	private Scanner console;
	private ParserCSV parser;
	private final String[] colunasArquivo;
	
	public Tela(){
		this.console = new Scanner(System.in);
		this.colunasArquivo = this.parser.getNomesColunasArquivo();
	}
	
	public void iniciar(){
		criarParser();
		System.out.println("\n-----------------------\n");
		System.out.println("\nComandos disponíveis:");
		System.out.println(" 1 - count *");
		System.out.println(" 2 - count distinct [propriedade]");
		System.out.println(" 3 - filter [propriedade] [valor]");
		System.out.print("\n>>>");
		determinarInput();
	}
	
	private void criarParser(){
		boolean caminhoEhValido = false;
		do{
			System.out.println("Forneça o caminho para o arquivo csv a ser carregado:");
			System.out.print("\n>>>");
			String caminhoArquivo = console.nextLine();
			try{
				this.parser = new ParserCSV(caminhoArquivo);
				caminhoEhValido = true;
			}catch(FileNotFoundException e){
				System.out.println("\nArquivo não encontrado. Por favor, forneça um caminho válido.\n");
			}
		}while(!caminhoEhValido);		
	}
	
	private void exibeOpcoesPropriedades(){
		for(int i = 0; i<this.colunasArquivo.length; i++){
			System.out.println(" " + (i+1) + " - " + this.colunasArquivo[i] + "\n");
		}
		System.out.print("\n>>>");
	}
	
	private void determinarInput(){
		String opcao = this.console.nextLine();
		switch(opcao){
			case "1":
				String registros = this.parser.executarConsulta(Comando.COUNT_ALL, null).get(0);
				System.out.println(registros);
				break;
				
			case "2":				
				String[] nomePropriedade = {escolherPropriedade(Comando.COUNT_DISTINCT)};				
				ArrayList<String> contagemUnicos = this.parser.executarConsulta(Comando.COUNT_DISTINCT, nomePropriedade);
				System.out.println("-------------------------------\n");		
				System.out.println("Número de registros únicos para a propriedade " 
						+ nomePropriedade + ": " + contagemUnicos.get(0));				
				break;
				
			case "3":				
				String nomePropriedadex = escolherPropriedade(Comando.FILTER);
				System.out.println("\n----------------------\n");
				System.out.println("Selecione um valor para a propriedade a ser filtrada:\n");
				System.out.print("\n>>>");
				String valor = console.nextLine();
				String[] argumentosFiltro = {nomePropriedadex,valor};
				ArrayList<String> filtrados = this.parser.executarConsulta(Comando.FILTER, argumentosFiltro);
				
				for(String linha : filtrados){
					System.out.println(linha);
				}
				
				break;
				
			default:
				System.out.println("Opção desconhecida.");
				break;
		}
	}
	
	private String escolherPropriedade(Comando comando){
		boolean inputValido = false;
		int propriedade = 0;
		do{
			System.out.println(
					(comando.equals(Comando.COUNT_DISTINCT) ? 
							"Selecione a propriedade cujos valores devem ser únicos "
							: "Selecione a propriedade a ser filtrada" + 
								"(digite \"a\" para abortar):"));
			exibeOpcoesPropriedades();
			String input = console.nextLine();
			try{
				propriedade = Integer.parseInt(input);
				if(propriedade > this.colunasArquivo.length){
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
		
		String nomePropriedade = this.colunasArquivo[propriedade-1];
		return nomePropriedade;
	}
}
