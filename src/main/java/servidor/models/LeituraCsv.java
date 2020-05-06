package servidor.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import servidor.models.interfaces.InterfaceFilaLeitura;

public class LeituraCsv implements Runnable {

	private Path caminhoLeitura;
	private InterfaceFilaLeitura filaLeitura;	
	private ProgressoLeitura progressoLeitura;	
	
	public LeituraCsv(Path caminhoLeitura, InterfaceFilaLeitura filaLeitura, ProgressoLeitura progressoLeitura) {
		this.caminhoLeitura = caminhoLeitura;
		this.filaLeitura = filaLeitura;		
		this.progressoLeitura = progressoLeitura;
	}

	@Override
	public void run() {
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
				
				progressoLeitura.incrementarProgresso();				
			}

			long tempoFim = System.nanoTime();

			long duracao = (tempoFim - tempoInicio) / 1000000;
			filaLeitura.setTerminou(true);
			progressoLeitura.setTerminado(true);
			
			System.out.println("Leitura tempo levado: " + duracao + " milliseconds.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
