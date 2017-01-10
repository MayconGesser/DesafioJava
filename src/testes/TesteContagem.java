package testes;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;
import java.util.Scanner;
import objetos.ParserCSV;

public class TesteContagem {

	final String caminho = "/home/maycon/Desktop/Projetos/selecaoinvolves/cidades.csv";
	
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
		assertEquals(5564,contagemLinhasScanner-1);
		assertEquals(contagemLinhasScanner-1, contagemLinhasParser);
	}
	
	public int testarContagem(){
		ParserCSV parser = new ParserCSV(caminho);
		return parser.getNumeroTotalLinhas();
	}
}
