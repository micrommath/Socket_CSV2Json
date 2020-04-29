package servidor.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.concurrent.Task;
import servidor.models.interfaces.InterfaceFilaLeitura;
import servidor.models.interfaces.InterfaceLeitura;

public class LeituraCsv implements InterfaceLeitura {

	public LeituraCsv() {
	}

	public Task<Void> getTaskLeitura(int totalLinhas, Path caminho, InterfaceFilaLeitura fila) {

		return new Task<Void>() {
			@Override
			protected Void call() {

				try (BufferedReader reader = Files.newBufferedReader(caminho)) {
					int contador = 0;
					float acum = 0;
					boolean primeiraLinha = true;

					long tempoInicio = System.nanoTime();

					while (reader.ready()) {

						if (primeiraLinha) {
							primeiraLinha = false;

							@SuppressWarnings("unused")
							String linhaCabecalho = reader.readLine();
							continue;
						}

						contador++;

						if (contador == (totalLinhas / 100)) {
							acum += 0.01F;
							updateProgress(acum, 1);
							contador = 0;
						}

						fila.enfilerar(reader.readLine());
					}

					long tempoFim = System.nanoTime();

					long duration = (tempoFim - tempoInicio) / 1000000;
					System.out.println("Leitura tempo levado: " + duration + " milliseconds.");
					fila.setTerminou(true);

				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		};

	}
}
