package main;

import java.io.FileNotFoundException;

import apresentacao.Tela;
import objetos.ParserCSV;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String caminhoArquivo = null;
		try{
			caminhoArquivo = args[0];
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Erro: Nenhum arquivo ou diretório especificado.");
			System.exit(1);
		}		
		ParserCSV parser = null;
		try{
			parser = new ParserCSV(caminhoArquivo);
		}catch(FileNotFoundException e){
			System.out.println("Erro: Arquivo ou diretório não encontrado.");
			System.exit(1);
		}		
		Tela tela = new Tela(parser);
		tela.iniciar();
	}
}
