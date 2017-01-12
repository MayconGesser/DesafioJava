package br.com.involves.selecao.testes.testesintegridadeparser;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import br.com.involves.selecao.objetos.ParserCSV;

public class TesteContagemPropriedadesArquivoExterno {
	
	ParserCSV parser;
	final String caminhoArquivo = "SalesJan2009.csv";

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
		int numeroDeColunasArquivo = 12;
		int numeroDeColunasParser = parser.getNumeroTotalColunas();
		
		assertEquals(numeroDeColunasArquivo,numeroDeColunasParser);
		
	}

}
