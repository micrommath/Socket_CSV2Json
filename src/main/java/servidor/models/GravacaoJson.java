package servidor.models;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;

import javafx.concurrent.Task;
import servidor.models.interfaces.InterfaceFilaConversao;
import servidor.models.interfaces.InterfaceGravacao;

public class GravacaoJson implements InterfaceGravacao{

	public GravacaoJson () {		
	}
	
	public Task<Void> getTaskGravacao(int pBarIncrement, Path caminhoSalvar, InterfaceFilaConversao fila) {
		
		return new Task<Void>() {
			@Override
			protected Void call() {

				int contador = 0;
				float acum = 0;

				try (BufferedWriter writter = Files.newBufferedWriter(caminhoSalvar, StandardCharsets.ISO_8859_1,
						StandardOpenOption.CREATE)) {

					long tempoInicio = System.nanoTime();

					while (fila.getSize() > 0) {

						Brasil obj = fila.desenfilerar();

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

				} catch (IOException e) {
					System.out.println(e.getStackTrace());

				}

				return null;
			}
		};
	}
}
