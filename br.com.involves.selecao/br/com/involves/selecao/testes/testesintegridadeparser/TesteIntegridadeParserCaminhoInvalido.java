package br.com.involves.selecao.testes.testesintegridadeparser;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import br.com.involves.selecao.objetos.ParserCSV;

public class TesteIntegridadeParserCaminhoInvalido {
	
	ParserCSV parser;
	final String caminhoArquivo = "/um/caminho/inexistente.csv";

	@Test
	public void deveFalharQuandoCaminhoArquivoInvalido() {
		boolean passou = true;
		try{
			parser = new ParserCSV(caminhoArquivo);
		}catch(FileNotFoundException e){
			passou = false;
		}
		assertEquals(true, passou);
	}	
}
