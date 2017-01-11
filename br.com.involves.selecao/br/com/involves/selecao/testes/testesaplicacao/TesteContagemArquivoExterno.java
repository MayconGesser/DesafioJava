package br.com.involves.selecao.testes.testesaplicacao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import br.com.involves.selecao.objetos.ParserCSV;

public class TesteContagemArquivoExterno {

	final String caminho = "SalesJan2009.csv";
	
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
		assertEquals(997,contagemLinhasScanner-1);
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
