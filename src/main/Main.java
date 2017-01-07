package main;

import apresentacao.Tela;
import objetos.ParserCSV;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParserCSV pcsv = new ParserCSV("/home/maycon/Desktop/Projetos/selecaoinvolves/cidades.csv");
		Tela tela = new Tela(pcsv);
		tela.iniciar();
	}
}
