package br.com.involves.selecao.testes.testesintegridadeparser;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import br.com.involves.selecao.objetos.ParserCSV;

public class TesteContagemPropriedades {
	
	ParserCSV parser;
	final String caminhoArquivo = "cidades.csv";
	
	@Test
	public void deveTerContagemCertaDePropriedades() {
		
		try{
			parser = new ParserCSV(caminhoArquivo);
		}catch(FileNotFoundException e){
			fail("Arquivo não encontrado");
		}
		catch(IllegalArgumentException e){
			fail("Arquivo de tipo incompativel");
		}
		int numeroDeColunasArquivo = 8;
		int numeroDeColunasParser = parser.getNumeroTotalColunas();
		
		assertEquals(numeroDeColunasArquivo,numeroDeColunasParser);
	}

}
