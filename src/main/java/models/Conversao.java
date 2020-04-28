package models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

public class Conversao {

	private int pBarIncrement = 0;

	public Conversao() {
	}

	public void realizarOperacoes(Path caminho, Path caminhoSalvar, ProgressBar progBarLeitura,
			ProgressBar progBarParse, ProgressBar progBarGravacao, LogInformacoes logInformacoes,
			Path arquivoTempoLevado) {

		this.inicializar(caminho);

		// Tasks
		Task<Void> leitura = new Leitura().getTask(pBarIncrement, caminho, logInformacoes);
		Task<Void> parse = new Parse().getTask(pBarIncrement, logInformacoes);
		Task<Void> gravacao = new Gravacao().getTask(pBarIncrement, caminhoSalvar, logInformacoes);

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

	private void inicializar(Path caminho) {
		try {
			int totalLines = Files.readAllLines(caminho).size();
			pBarIncrement = totalLines / 100;
			new QueueRead(totalLines);
			new QueueParse(totalLines);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}