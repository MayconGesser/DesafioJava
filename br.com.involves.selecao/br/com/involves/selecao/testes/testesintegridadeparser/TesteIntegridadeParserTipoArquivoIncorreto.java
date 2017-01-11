package br.com.involves.selecao.testes.testesintegridadeparser;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import br.com.involves.selecao.objetos.ParserCSV;

public class TesteIntegridadeParserTipoArquivoIncorreto {
	
	ParserCSV parser;
	final String caminhoArquivo = "incosistencias.txt";
	
	//joga a outra excecao por questoes de integridade de teste
	@Test
	public void deveFalharQuandoTipoArquivoIncorreto() throws FileNotFoundException{
		boolean passou = true; 
		try{
			parser = new ParserCSV(caminhoArquivo);
		}
		catch(IllegalArgumentException e){
			passou = false;
		}
		assertEquals(true,passou);
	}

}
