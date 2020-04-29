package servidor.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import servidor.models.FilaConversao;
import servidor.models.FilaLeitura;
import servidor.models.GravacaoJson;
import servidor.models.LeituraCsv;
import servidor.models.ParseCsvJson;
import servidor.models.interfaces.*;

public class ConversaoController {

	private InterfaceLeitura leituraCSV;
	private InterfaceParse parseCsvJson;
	private InterfaceGravacao gravacaoJson;
	 
	private InterfaceFilaLeitura filaLeitura;
	private InterfaceFilaConversao filaConversao;	
	
	private int pBarIncrement;
	private int totalLinhas;

	public ConversaoController() {
		leituraCSV = new LeituraCsv();
		parseCsvJson = new ParseCsvJson();
		gravacaoJson = new GravacaoJson();
	}

	public void realizarOperacoes(Path caminhoLeitura, Path caminhoSalvar, ProgressBar progBarLeitura,
			ProgressBar progBarParse, ProgressBar progBarGravacao) {

		this.inicializar(caminhoLeitura);

		// Tasks
		Task<Void> leitura = leituraCSV.getTaskLeitura(pBarIncrement, caminhoLeitura, filaLeitura);
		Task<Void> parse = parseCsvJson.getTaskParse(pBarIncrement, filaLeitura, filaConversao);
		Task<Void> gravacao = gravacaoJson.getTaskGravacao(pBarIncrement, caminhoSalvar, filaConversao);

		// Set progress bar
		progBarLeitura.progressProperty().bind(leitura.progressProperty());
		progBarParse.progressProperty().bind(parse.progressProperty());
		progBarGravacao.progressProperty().bind(gravacao.progressProperty());

		// Threads
		Thread t1 = new Thread(leitura);
		Thread t2 = new Thread(parse);
		Thread t3 = new Thread(gravacao);

		try {
			t1.start();

			Thread.sleep(20);

			t2.start();

			Thread.sleep(20);

			t3.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void inicializar(Path caminhoLeitura) {
		try {
			this.totalLinhas = Files.readAllLines(caminhoLeitura).size();
			pBarIncrement = this.totalLinhas / 100;
			
			filaLeitura = new FilaLeitura(this.totalLinhas);
			filaConversao = new FilaConversao(this.totalLinhas);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
