package testes;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.Test;

import enums.Comando;
import objetos.ParserCSV;

public class TesteContagemPropriedadeUnica {
		
	final String caminho = "/home/maycon/Desktop/Projetos/selecaoinvolves/cidades.csv";
	ParserCSV parser = null;
	
	final int numeroLinhasTotal = 5564;
	HashMap<String,Integer> resultadosCertos = new HashMap<>();
	String[] nomesColunas = null;
	@Test
	public void test() {
		try{
			parser = new ParserCSV(caminho);
			nomesColunas = parser.getNomesColunasArquivo();
		}catch(FileNotFoundException e){
			fail("Arquivo não encontrado");
		}
		resultadosCertos.put(nomesColunas[0], numeroLinhasTotal);			//ibge_id
		resultadosCertos.put(nomesColunas[1], 27);							//uf
		resultadosCertos.put(nomesColunas[2], 5290);						//name
		resultadosCertos.put(nomesColunas[3], numeroLinhasTotal);			//lat
		resultadosCertos.put(nomesColunas[4], numeroLinhasTotal);			//lon
		resultadosCertos.put(nomesColunas[5], 5282);						//no_accents
		resultadosCertos.put(nomesColunas[6], 554);							//microregion
		resultadosCertos.put(nomesColunas[7], 137);							//mesoregion
		
		for(int i = 0; i<nomesColunas.length; i++){
			int certo = resultadosCertos.get(nomesColunas[i]);
			int funcaoContarUnicos = testarContagemUnicos(nomesColunas[i]);
			assertEquals(certo, funcaoContarUnicos);
		}
	}
	
	public int testarContagemUnicos(String nomeColuna){				
		String[] args = {nomeColuna};
		int retorno = Integer.parseInt(
				parser.executarConsulta(Comando.COUNT_DISTINCT, args).get(0));
		return retorno;
	}
}
