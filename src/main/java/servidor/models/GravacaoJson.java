package servidor.models;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;

import servidor.models.interfaces.InterfaceFilaConversao;

public class GravacaoJson implements Runnable{

	private Path caminhoSalvar;
	private InterfaceFilaConversao filaConversao;
	
	public GravacaoJson(Path caminhoSalvar, InterfaceFilaConversao filaConversao) {
		this.caminhoSalvar = caminhoSalvar;
		this.filaConversao = filaConversao;
	}

	@Override
	public void run() {
 
		try (BufferedWriter writter = Files.newBufferedWriter(caminhoSalvar, StandardCharsets.ISO_8859_1,
				StandardOpenOption.CREATE)) {

			long tempoInicio = System.nanoTime();			
			Gson gson = new Gson();
			
			while (filaConversao.getSize() > 0 || !filaConversao.getTerminou()) {

				Brasil obj = filaConversao.desenfilerar();
				
				String json = gson.toJson(obj);

				writter.append(json);					
			}

			long tempoFim = System.nanoTime();

			long duracao = (tempoFim - tempoInicio) / 1000000;
			System.out.println("Gravação tempo levado: " + duracao + " milliseconds.");
			
		} catch (IOException e) {
			System.out.println(e.getStackTrace());

		}	
	}
}
