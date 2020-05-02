package servidor.controller;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import servidor.models.FilaConversao;
import servidor.models.FilaLeitura;
import servidor.models.GravacaoJson;
import servidor.models.LeituraCsv;
import servidor.models.ParseCsvJson;
import servidor.models.interfaces.*;

public class ConversaoController {

	private LeituraCsv leituraCSV;
	private ParseCsvJson parseCsvJson;
	private GravacaoJson gravacaoJson;

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

			leituraCSV = new LeituraCsv(caminhoLeitura, filaLeitura);
			parseCsvJson = new ParseCsvJson(filaLeitura, filaConversao);
			gravacaoJson = new GravacaoJson(caminhoSalvar, filaConversao);

			// Threads
			Thread t1 = new Thread(leituraCSV);
			Thread t2 = new Thread(parseCsvJson);
			Thread t3 = new Thread(gravacaoJson);

			t1.start();			
			t2.start();			
			t3.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
