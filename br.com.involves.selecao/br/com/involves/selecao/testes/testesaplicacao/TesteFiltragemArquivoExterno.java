package br.com.involves.selecao.testes.testesaplicacao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import br.com.involves.selecao.objetos.ParserCSV;
import br.com.involves.selecao.objetos.enums.Comando;

public class TesteFiltragemArquivoExterno {

	final String caminho = "SalesJan2009.csv";
	ParserCSV parser = null;
	HashMap<String[],Integer> resultadosCertos = new HashMap<>();
	String[] filtragem_1 = {"Country","Brazil"};
	String[] filtragem_2 = {"Country","Germany"};
	String[] filtragem_3 = {"State","IL"};
	String[] filtragem_4 = {"Product","Product3"};
	ArrayList<String[]> argsFiltragens = new ArrayList<>();
	
	@Test
	public void devePassarQuandoFiltrado() {
		try{
			parser = new ParserCSV(caminho);
		}catch(FileNotFoundException e){
			fail("Arquivo não encontrado");
		}
		resultadosCertos.put(filtragem_1, 5);
		resultadosCertos.put(filtragem_2, 25);
		resultadosCertos.put(filtragem_3, 16);
		resultadosCertos.put(filtragem_4, 16);
		
		argsFiltragens.add(filtragem_1);
		argsFiltragens.add(filtragem_2);
		argsFiltragens.add(filtragem_3);
		argsFiltragens.add(filtragem_4);
		
		for(int i = 0; i<argsFiltragens.size()-1; i++){
			int resultadoEsperado = resultadosCertos.get(argsFiltragens.get(i));
			//-1 novamente desconta o cabecalho previamente incluido
			int resultadoFuncaoFiltrar = mockFuncaoFiltrar(argsFiltragens.get(i))-1;
			assertEquals(resultadoEsperado,resultadoFuncaoFiltrar);
		}		
	}
	
	public int mockFuncaoFiltrar(String[] args){
		int retorno = parser.executarConsulta(
				Comando.FILTER, args
			).size();
		return retorno;
	}
	
}
