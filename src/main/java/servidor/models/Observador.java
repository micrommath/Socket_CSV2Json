package servidor.models;

import java.nio.file.Path;

import servidor.models.interfaces.InterfaceFilaConversao;
import servidor.models.interfaces.InterfaceFilaLeitura;

public class Observador implements Runnable {

	private InterfaceFilaLeitura filaLeitura;
	private InterfaceFilaConversao filaConversao;
	private Path caminhoSalvar;
	private final int maximoAceitavel = 100;

	private int contadorThConversao = 0;
	private int contadorThGravacao = 0;

	public Observador(InterfaceFilaLeitura filaLeitura, InterfaceFilaConversao filaConversao, Path caminhoSalvar) {
		this.filaLeitura = filaLeitura;
		this.filaConversao = filaConversao;
		this.caminhoSalvar = caminhoSalvar;
	}

	@Override
	public void run() {
		this.observar();
	}

	private void observar() {
		try {
			while (filaLeitura.getSize() > 0 || !filaLeitura.getTerminou() || filaConversao.getSize() > 0
					|| !filaConversao.getTerminou()) {

				Thread.sleep(20);

				this.observarConversao();
				this.observarGravacao();
			}

			System.out.println("*** Quantidade de thread conversão: " + contadorThConversao);
			System.out.println("*** Quantidade de thread gravação: " + contadorThGravacao);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void observarConversao() {
		if (filaLeitura.getSize() >= maximoAceitavel && !filaLeitura.getTerminou()) {
			contadorThConversao++;
			new Thread(new ParseCsvJson(filaLeitura, filaConversao)).start();
			System.out.println("Nova thread conversão. Quantidade itens na fila: " + filaLeitura.getSize());
		}

	}

	private void observarGravacao() {
		if (filaConversao.getSize() >= maximoAceitavel && !filaConversao.getTerminou()) {
			contadorThGravacao++;
			new Thread(new GravacaoJson(caminhoSalvar, filaConversao)).start();
			System.out.println("Nova thread gravação. Quantidade itens na fila:  " + filaConversao.getSize());
		}

	}
}
