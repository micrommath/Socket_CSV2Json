package servidor.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;

import servidor.models.interfaces.InterfaceFilaConversao;
import servidor.models.interfaces.InterfaceFilaLeitura;

public class Observador implements Runnable {

	private final int maximoAceitavel = 100;

	private InterfaceFilaLeitura filaLeitura;
	private InterfaceFilaConversao filaConversao;
	private Path caminhoSalvar;
	private int totalLinhas;

	private Socket cliente;

	private PBarsOperacoes pBarsOperacoes;

	private ProgressoLeitura progressoLeitura;
	private ProgressoParse progressoParse;
	private ProgressoGravacao progressoGravacao;

	int contadorThConversao = 0, contadorThGravacao = 0;

	public Observador(InterfaceFilaLeitura filaLeitura, InterfaceFilaConversao filaConversao, Path caminhoSalvar,
			Socket cliente, int totalLinhas, ProgressoLeitura progressoLeitura, ProgressoParse progressoParse,
			ProgressoGravacao progressoGravacao, PBarsOperacoes pBarsOperacoes) {
		this.filaLeitura = filaLeitura;
		this.filaConversao = filaConversao;
		this.caminhoSalvar = caminhoSalvar;
		this.cliente = cliente;
		this.totalLinhas = totalLinhas;
		this.progressoParse = progressoParse;
		this.progressoGravacao = progressoGravacao;
		this.progressoLeitura = progressoLeitura;
		this.pBarsOperacoes = pBarsOperacoes;
	}

	@Override
	public void run() {
		this.observar();
	}

	private void observar() {

		try {
			while (filaLeitura.getSize() > 0 || !filaLeitura.getTerminou() || filaConversao.getSize() > 0
					|| !filaConversao.getTerminou()) {

				this.observarConversao();
				this.observarGravacao();
				this.enviarFeedback(filaLeitura.getSize(), filaConversao.getSize());

				Thread.sleep(7);
			}

			this.enviarFeedback(filaLeitura.getSize(), filaConversao.getSize());
			
			System.out.println("**** Quantidade de thread conversão: " + contadorThConversao);
			System.out.println("**** Quantidade de thread gravação: " + contadorThGravacao);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void observarConversao() {
		if (filaLeitura.getSize() >= maximoAceitavel && !filaLeitura.getTerminou()) {
			contadorThConversao++;
			new Thread(new ParseCsvJson(filaLeitura, filaConversao, progressoParse)).start();
		}

	}

	private void observarGravacao() {
		if (filaConversao.getSize() >= maximoAceitavel && !filaConversao.getTerminou()) {
			contadorThGravacao++;
			new Thread(new GravacaoJson(caminhoSalvar, filaConversao, progressoGravacao)).start();
		}

	}

	private void enviarFeedback(int sizeFilaLeitura, int sizeFilaParse) {
		try {
			Feedback feedback = new Feedback();

			feedback.setTotalLinhas(totalLinhas);
			feedback.setSizeFilaLeitura(sizeFilaLeitura);
			feedback.setSizeFilaParse(sizeFilaParse);
			feedback.setPbarLeituraValor(pBarsOperacoes.getLeituraValor());
			feedback.setPbarConversaoValor(pBarsOperacoes.getConversaoValor());
			feedback.setPbarGravacaoValor(pBarsOperacoes.getGravacaoValor());

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(feedback);

			OutputStream saida = this.cliente.getOutputStream();
			saida.write(bos.toByteArray());

			saida.flush();
			oos.close();
			bos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
