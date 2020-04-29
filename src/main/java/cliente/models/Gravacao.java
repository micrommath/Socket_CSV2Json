package cliente.models;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;

import javafx.concurrent.Task;

public class Gravacao {

	public Gravacao () {		
	}
	
	public Task<Void> getTask(int pBarIncrement, Path caminhoSalvar, LogInformacoes logInformacoes) {
		
		return new Task<Void>() {
			@Override
			protected Void call() {

				int contador = 0;
				float acum = 0;

				try (BufferedWriter writter = Files.newBufferedWriter(caminhoSalvar, StandardCharsets.ISO_8859_1,
						StandardOpenOption.CREATE)) {

					long tempoInicio = System.nanoTime();

					while (QueueParse.getSize() > 0) {

						Brasil obj = QueueParse.getData();

						Gson gson = new Gson();
						String json = gson.toJson(obj);

						writter.append(json);

						contador++;

						if (contador == pBarIncrement) {

							acum += 0.01F;							
							updateProgress(acum, 1);
							contador = 0;
						}
					}

					long tempoFim = System.nanoTime();

					long duration = (tempoFim - tempoInicio) / 1000000;
					System.out.println("Gravação tempo levado: " + duration + " milliseconds.");
					logInformacoes.setGravacao(duration);

					Log.GravarLog(logInformacoes);

				} catch (IOException e) {
					System.out.println(e.getStackTrace());

				}

				return null;
			}
		};
	}
}
