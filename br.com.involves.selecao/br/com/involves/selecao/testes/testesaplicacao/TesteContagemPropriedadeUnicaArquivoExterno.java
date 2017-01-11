package br.com.involves.selecao.testes.testesaplicacao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.Test;

import br.com.involves.selecao.objetos.ParserCSV;
import br.com.involves.selecao.objetos.enums.Comando;

public class TesteContagemPropriedadeUnicaArquivoExterno {
	final String caminho = "SalesJan2009.csv";
	ParserCSV parser = null;
	
	HashMap<String,Integer> resultadosCertos = new HashMap<>();
	String[] nomesColunas = null;
	@Test
	public void devePassarQuandoContarPropriedadeUnica() {
		try{
			parser = new ParserCSV(caminho);
			nomesColunas = parser.getNomesColunasArquivo();
		}catch(FileNotFoundException e){
			fail("Arquivo não encontrado");
		}

		resultadosCertos.put(nomesColunas[0],986);		//Transaction_date
		resultadosCertos.put(nomesColunas[1], 4);		//Product
		resultadosCertos.put(nomesColunas[2], 9);		//Price
		resultadosCertos.put(nomesColunas[3], 4);		//Payment_Type
		resultadosCertos.put(nomesColunas[4], 764);		//Name
		resultadosCertos.put(nomesColunas[5], 759);		//City
		resultadosCertos.put(nomesColunas[6], 205);		//State
		resultadosCertos.put(nomesColunas[7], 56);		//Country
		resultadosCertos.put(nomesColunas[8], 975);		//Account_Created
		resultadosCertos.put(nomesColunas[9], 976);		//Last_Login
		resultadosCertos.put(nomesColunas[10], 706);	//Latitude
		resultadosCertos.put(nomesColunas[11], 723);	//Longitude
		
		for(int i = 0; i<nomesColunas.length; i++){
			int certo = resultadosCertos.get(nomesColunas[i]);
			int funcaoContarUnicos = mockFuncaoContagemUnicos(nomesColunas[i]);
			assertEquals(certo, funcaoContarUnicos);
		}
	}
	
	public int mockFuncaoContagemUnicos(String nomeColuna){				
		String[] args = {nomeColuna};
		int retorno = Integer.parseInt(
				parser.executarConsulta(Comando.COUNT_DISTINCT, args).get(0));
		return retorno;
	}
}
