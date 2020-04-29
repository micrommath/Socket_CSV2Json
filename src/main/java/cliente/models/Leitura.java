package cliente.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.concurrent.Task;

public class Leitura {

	public Leitura() {
	}

	public Task<Void> getTask(int pBarIncrement, Path caminho, LogInformacoes logInformacoes) {

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

						if (contador == pBarIncrement) {
							acum += 0.01F;
							updateProgress(acum, 1);
							contador = 0;
						}

						QueueRead.addData(reader.readLine());
					}

					long tempoFim = System.nanoTime();

					long duration = (tempoFim - tempoInicio) / 1000000;
					System.out.println("Leitura tempo levado: " + duration + " milliseconds.");
					logInformacoes.setLeitura(duration);

					QueueRead.setTerminatedAdd(true);

				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		};

	}
}
