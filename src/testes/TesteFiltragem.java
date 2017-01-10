package testes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import enums.Comando;
import objetos.ParserCSV;

public class TesteFiltragem {
	
	final String caminho = "/home/maycon/Desktop/Projetos/selecaoinvolves/cidades.csv";
	final ParserCSV parser = null;
	final int numeroLinhasTotal = 5564;
	HashMap<String[],Integer> resultadosCertos = new HashMap<>();
	String[] filtragem_1 = {"uf","RO"};
	String[] filtragem_2 = {"uf","AC"};
	String[] filtragem_3 = {"uf","AM"};
	String[] filtragem_4 = {"ibge_id","1300300"};
	ArrayList<String[]> argsFiltragens = new ArrayList<>();
	
	@Test
	public void test() {
		resultadosCertos.put(filtragem_1, 51);
		resultadosCertos.put(filtragem_2, 22);
		resultadosCertos.put(filtragem_3, 62);
		
		argsFiltragens.add(filtragem_1);
		argsFiltragens.add(filtragem_2);
		argsFiltragens.add(filtragem_3);
		argsFiltragens.add(filtragem_4);
		
		for(int i = 0; i<argsFiltragens.size()-1; i++){
			assertEquals((int)resultadosCertos.get(argsFiltragens.get(i)), 
						(int)testeFiltrar(argsFiltragens.get(i))
					);
		}
		
		assertEquals(1,(int)testeFiltrar(argsFiltragens.get(3)));
	}
	
	public int testeFiltrar(String[] args){
		int retorno = parser.executarConsulta(
				Comando.FILTER, args
			).size();
		return retorno;
	}
}
