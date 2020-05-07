package servidor.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;

import servidor.models.interfaces.InterfaceFilaConversao;
import servidor.models.interfaces.InterfaceFilaLeitura;

public class ObservadorProgresso implements Runnable {

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

	private String statusLeitura = "Parado";
	private String statusParse = "Parado";
	private String statusGravacao = "Parado";

	int contadorThConversao = 0, contadorThGravacao = 0;

	public ObservadorProgresso(InterfaceFilaLeitura filaLeitura, InterfaceFilaConversao filaConversao,
			Path caminhoSalvar, Socket cliente, int totalLinhas, ProgressoLeitura progressoLeitura,
			ProgressoParse progressoParse, ProgressoGravacao progressoGravacao, PBarsOperacoes pBarsOperacoes) {
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

		boolean primeiro = true;

		try {
			while (filaLeitura.getSize() > 0 || !filaLeitura.getTerminou() || filaConversao.getSize() > 0
					|| !filaConversao.getTerminou()) {

				if (primeiro) {
					statusLeitura = statusParse = statusGravacao = "Inicio da operação";
				}

				observarLeitura();
				observarConversao();
				observarGravacao();
				enviarFeedback();

				Thread.sleep(15);
			}

			enviarFeedback();

			System.out.println("**** Quantidade de thread conversão: " + contadorThConversao);
			System.out.println("**** Quantidade de thread gravação: " + contadorThGravacao);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void observarLeitura() {
		if (!progressoLeitura.getTerminado())
			this.statusLeitura = "Leitura em progresso...";
		else
			this.statusLeitura = "Leitura terminada...";
	}

	private void observarConversao() {
		if (filaLeitura.getSize() >= maximoAceitavel && !filaLeitura.getTerminou()) {
			contadorThConversao++;
			new Thread(new ParseCsvJson(filaLeitura, filaConversao, progressoParse)).start();
		}

		if (!progressoParse.getTerminado())
			this.statusParse = "Conversão em progresso...";
		else
			this.statusParse = "Conversão terminada...";
	}

	private void observarGravacao() {
		if (filaConversao.getSize() >= maximoAceitavel && !filaConversao.getTerminou()) {
			contadorThGravacao++;
			new Thread(new GravacaoJson(caminhoSalvar, filaConversao, progressoGravacao)).start();
		}

		if (!progressoGravacao.getTerminado())
			this.statusGravacao = "Gravação em progresso...";
		else
			this.statusGravacao = "Gravação terminada...";
	}

	private void enviarFeedback() {
		try {			
			Feedback feedback = new Feedback();

			feedback.setTotalLinhas(totalLinhas);
			feedback.setSizeFilaLeitura(filaLeitura.getSize());
			feedback.setSizeFilaParse(filaConversao.getSize());
			feedback.setPbarLeituraValor(pBarsOperacoes.getLeituraValor());
			feedback.setPbarConversaoValor(pBarsOperacoes.getConversaoValor());
			feedback.setPbarGravacaoValor(pBarsOperacoes.getGravacaoValor());
			feedback.setStatusLeitura(statusLeitura);
			feedback.setStatusParse(statusParse);
			feedback.setStatusGravacao(statusGravacao);

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
