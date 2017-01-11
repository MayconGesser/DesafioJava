package testes;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import objetos.ParserCSV;

public class TesteContagemArquivoExterno {

	final String caminho = "SalesJan2009.csv";
	
	@Test
	public void test() {
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
		int contagemLinhasParser = testarContagem();
		//-1 desconta o cabecalho
		assertEquals(997,contagemLinhasScanner-1);
		assertEquals(contagemLinhasScanner-1, contagemLinhasParser);
	}
	
	private int testarContagem(){
		ParserCSV parser = null;
		try{
			parser = new ParserCSV(caminho);
		}catch(FileNotFoundException e){
			fail("Arquivo não encontrado");
		}
		
		return parser.getNumeroTotalLinhas();
	}
}
