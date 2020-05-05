package servidor.controller;

import java.lang.Thread.State;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import servidor.models.FilaConversao;
import servidor.models.FilaLeitura;
import servidor.models.GravacaoJson;
import servidor.models.LeituraCsv;
import servidor.models.Observador;
import servidor.models.PBarsOperacoes;
import servidor.models.ParseCsvJson;
import servidor.models.ProgressoGravacao;
import servidor.models.ProgressoLeitura;
import servidor.models.ProgressoParse;
import servidor.models.interfaces.*;

public class ConversaoController {

	private LeituraCsv leituraCSV;
	private ParseCsvJson parseCsvJson;
	private GravacaoJson gravacaoJson;

	private Observador observador;

	private InterfaceFilaLeitura filaLeitura;
	private InterfaceFilaConversao filaConversao;

	private Socket cliente;

	private int totalLinhas;

	private PBarsOperacoes pBarsOperacoes;

	public ConversaoController(Socket cliente) {
		this.cliente = cliente;
	}

	public void realizarOperacoes(Path caminhoLeitura, Path caminhoSalvar) {
		try {

			this.totalLinhas = Files.readAllLines(caminhoLeitura).size();
			int incrementoProgresso = totalLinhas / 100;

			// Fila
			filaLeitura = new FilaLeitura(totalLinhas);
			filaConversao = new FilaConversao(totalLinhas);

			// Classe de controle para retorno
			pBarsOperacoes = new PBarsOperacoes(incrementoProgresso);

			// Classes de controle progresso
			ProgressoLeitura progressoLeitura = new ProgressoLeitura(pBarsOperacoes);
			ProgressoParse progressoParse = new ProgressoParse(pBarsOperacoes);
			ProgressoGravacao progressoGravacao = new ProgressoGravacao(pBarsOperacoes);

			// Operações
			leituraCSV = new LeituraCsv(caminhoLeitura, filaLeitura, progressoLeitura);
			parseCsvJson = new ParseCsvJson(filaLeitura, filaConversao, progressoParse);
			gravacaoJson = new GravacaoJson(caminhoSalvar, filaConversao, progressoGravacao);

			// Observador
			observador = new Observador(filaLeitura, filaConversao, caminhoSalvar, cliente, totalLinhas,
					progressoLeitura, progressoParse, progressoGravacao, pBarsOperacoes);

			// Threads
			Thread tLeitura = new Thread(leituraCSV);
			Thread tParse = new Thread(parseCsvJson);
			Thread tGravacao = new Thread(gravacaoJson);
			Thread tObservador = new Thread(observador);

			tLeitura.start();
			tParse.start();
			tGravacao.start();
			tObservador.start();

			tObservador.join();
			System.out.println(tObservador.getState());
			cliente.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
