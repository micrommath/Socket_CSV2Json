package servidor.controller;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import servidor.models.FilaConversao;
import servidor.models.FilaLeitura;
import servidor.models.GravacaoJson;
import servidor.models.LeituraCsv;
import servidor.models.Observador;
import servidor.models.ParseCsvJson;
import servidor.models.interfaces.*;

public class ConversaoController {

	private LeituraCsv leituraCSV;
	private ParseCsvJson parseCsvJson;
	private GravacaoJson gravacaoJson;

	private Observador observador;
	
	private InterfaceFilaLeitura filaLeitura;
	private InterfaceFilaConversao filaConversao;

	private Socket cliente;
	
	private int totalLinhas;

	public ConversaoController(Socket cliente) {		
		this.cliente = cliente;
	}

	public void realizarOperacoes(Path caminhoLeitura, Path caminhoSalvar) {
		try {

			this.totalLinhas = Files.readAllLines(caminhoLeitura).size();

			filaLeitura = new FilaLeitura(totalLinhas);
			filaConversao = new FilaConversao(totalLinhas);			
			
			leituraCSV = new LeituraCsv(caminhoLeitura, filaLeitura, cliente);
			parseCsvJson = new ParseCsvJson(filaLeitura, filaConversao);
			gravacaoJson = new GravacaoJson(caminhoSalvar, filaConversao);
			observador = new Observador(filaLeitura, filaConversao, caminhoSalvar);
			
			// Threads
			Thread tLeitura = new Thread(leituraCSV);
			Thread tParse = new Thread(parseCsvJson);
			Thread tGravacao = new Thread(gravacaoJson);
			Thread tObservador = new Thread(observador);
			
			tLeitura.start();			
			tParse.start();			
			tGravacao.start();
			tObservador.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
