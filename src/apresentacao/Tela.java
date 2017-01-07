package apresentacao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
		this.colunasArquivo = this.parser.getNomesColunasArquivo();
	}
	
	public void iniciar(){
		System.out.println("Comandos disponíveis:");
		System.out.println(" 1 - count *");
		System.out.println(" 2 - count distinct [propriedade]");
		System.out.println(" 3 - filter [propriedade] [valor]");
		System.out.print("\n>>>");
		determinarInput();
	}
	
	private void exibeOpcoesPropriedades(){
		for(int i = 0; i<this.colunasArquivo.length; i++){
			System.out.println(" " + (i+1) + " - " + this.colunasArquivo[i] + "\n");
		}
	}
	
	private void determinarInput(){
		String opcao = this.console.nextLine();
		switch(opcao){
			case "1":
				String registros = this.parser.executarConsulta(Comando.COUNT_ALL, null).get(0);
				System.out.println(registros);
				break;
				
			case "2":
				System.out.println("Selecione a propriedade cujos valores devem ser únicos:");
				exibeOpcoesPropriedades();
				int propriedade = 0;
				try{
					propriedade = console.nextInt();
					console.nextLine();
				}catch(InputMismatchException e){
					e.printStackTrace();
				}
				String opcionais = this.colunasArquivo[propriedade+1];
				ArrayList<String> unicos = this.parser.executarConsulta(Comando.COUNT_DISTINCT, opcionais);
				System.out.println("-------------------------------\n");
				File arquivoSaida = new File("/home/maycon/Desktop/Projetos/arquivoteste.txt");
			try {
				FileOutputStream fos = new FileOutputStream(arquivoSaida);
				for(String s : unicos){
					String linha = s + "\n";
					fos.write(linha.getBytes());
					fos.flush();
				}
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
				break;
				
			case "3":
				break;
		}
	}
}
