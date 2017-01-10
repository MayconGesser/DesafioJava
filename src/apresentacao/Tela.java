package apresentacao;
import java.util.ArrayList;
import java.util.Scanner;

import enums.Comando;
import objetos.ParserCSV;

public class Tela {
	private Scanner console;
	private ParserCSV parser;
	private String[] colunasArquivo;
	
	public Tela(ParserCSV parser){
		this.console = new Scanner(System.in);
		this.parser = parser;
		this.colunasArquivo = parser.getNomesColunasArquivo();
	}
	
	public void iniciar(){
		exibirOpcoesComandos();
		determinarInput();
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
		for(int i = 0; i<this.colunasArquivo.length; i++){
			System.out.println(" " + (i+1) + " - " + this.colunasArquivo[i] + "\n");
		}
		System.out.print("\n>>>");
	}
	
	private void determinarInput(){
		while(true){
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
							+ nomePropriedade[0] + ": " + contagemUnicos.get(0));				
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
