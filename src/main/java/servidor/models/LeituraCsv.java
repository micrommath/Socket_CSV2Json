package servidor.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import servidor.models.Feedback;
import servidor.models.interfaces.InterfaceFilaLeitura;

public class LeituraCsv implements Runnable {

	private Path caminhoLeitura;
	private InterfaceFilaLeitura filaLeitura;
	private Socket cliente;

	public LeituraCsv(Path caminhoLeitura, InterfaceFilaLeitura filaLeitura, Socket cliente) {
		this.caminhoLeitura = caminhoLeitura;
		this.filaLeitura = filaLeitura;
		this.cliente = cliente;
	}

	@Override
	public void run() {
		Feedback feedback = new Feedback(0, 0, 0);

		int contador = 0;
		try (BufferedReader reader = Files.newBufferedReader(caminhoLeitura)) {
			boolean primeiraLinha = true;

			long tempoInicio = System.nanoTime();

			while (reader.ready()) {

				if (primeiraLinha) {
					primeiraLinha = false;

					// Consome linha
					@SuppressWarnings("unused")
					String linhaCabecalho = reader.readLine();
					continue;
				}

				filaLeitura.enfilerar(reader.readLine());
				contador++;

				//this.enviarFeedback(feedback, contador);			 
			}

			long tempoFim = System.nanoTime();

			long duracao = (tempoFim - tempoInicio) / 1000000;
			filaLeitura.setTerminou(true);

			System.out.println("Leitura tempo levado: " + duracao + " milliseconds.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private synchronized void enviarFeedback(Feedback feedback, int contador) {
		try {
			feedback.setPbarLeituraValor(contador);
			ObjectOutputStream os = new ObjectOutputStream(this.cliente.getOutputStream());
			os.writeObject(feedback);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
