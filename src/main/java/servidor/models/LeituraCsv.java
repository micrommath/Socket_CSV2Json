package servidor.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import servidor.models.interfaces.InterfaceFilaLeitura;

public class LeituraCsv implements Runnable {

	private Path caminhoLeitura;
	private InterfaceFilaLeitura filaLeitura;
	
	public LeituraCsv(Path caminhoLeitura, InterfaceFilaLeitura filaLeitura) {
		this.caminhoLeitura = caminhoLeitura;
		this.filaLeitura = filaLeitura;
	}	

	@Override
	public void run() {		
		
		try (BufferedReader reader = Files.newBufferedReader(caminhoLeitura)) {
			boolean primeiraLinha = true;

			long tempoInicio = System.nanoTime();

			while (reader.ready()) {
				
				if (primeiraLinha) {
					primeiraLinha = false;

					@SuppressWarnings("unused")
					String linhaCabecalho = reader.readLine();
					continue;
				}
				
				filaLeitura.enfilerar(reader.readLine());
			}

			long tempoFim = System.nanoTime();

			long duration = (tempoFim - tempoInicio) / 1000000;
			filaLeitura.setTerminou(true);
			
			System.out.println("Leitura tempo levado: " + duration + " milliseconds.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
