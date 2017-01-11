package br.com.involves.selecao.testes.testesaplicacao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import br.com.involves.selecao.objetos.ParserCSV;

import java.util.Scanner;

public class TesteContagem {

	final String caminho = "cidades.csv";
	
	@Test
	public void devePassarQuandoContarTodosRegistros() {
		File documento = new File(caminho);
		Scanner sc = null;
		try {
			sc = new Scanner(documento);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int contagemLinhasScanner = 0;
		while(sc.hasNextLine()){
			contagemLinhasScanner++;
			sc.nextLine();
		}
		sc.close();
		int contagemLinhasParser = mockFuncaoContarRegistros();
		//-1 desconta o cabecalho
		assertEquals(5564,contagemLinhasScanner-1);
		assertEquals(contagemLinhasScanner-1, contagemLinhasParser);
	}
	
	private int mockFuncaoContarRegistros(){
		ParserCSV parser = null;
		try{
			parser = new ParserCSV(caminho);
		}catch(FileNotFoundException e){
			fail("Arquivo não encontrado");
		}
		
		return parser.getNumeroTotalLinhas();
	}
}
