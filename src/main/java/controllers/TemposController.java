package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Log;
import models.LogInformacoes;
import models.TempoViewModel;

public class TemposController {

	public void CalcularTempos(TempoViewModel tempo) {
		ArrayList<String> temposLeitura = new ArrayList<String>();
		ArrayList<String> temposParse = new ArrayList<String>();
		ArrayList<String> temposGravacao = new ArrayList<String>();

		List<LogInformacoes> logs = Log.LerTodoLog();

		for (LogInformacoes log : logs) {

			temposLeitura.add(Long.toString(log.getLeitura()));
			temposParse.add(Long.toString(log.getParse()));
			temposGravacao.add(Long.toString(log.getGravacao()));
		}

		double minLeitura = 100000;
		double maxLeitura = -1;
		double mediaLeitura = 0.0;

		double minParse = 100000;
		double maxParse = -1;
		double mediaParse = 0.0;

		double minGravacao = 100000;
		double maxGravacao = -1;
		double mediaGravacao = 0.0;

		for (int i = 0; i < logs.size(); i++) {

			// Leitura
			double valorLeitura = Double.parseDouble(temposLeitura.get(i));

			mediaLeitura += valorLeitura;

			if (valorLeitura < minLeitura)
				minLeitura = valorLeitura;

			if (valorLeitura > maxLeitura)
				maxLeitura = valorLeitura;

			// Parse
			double valorParse = Double.parseDouble(temposParse.get(i));

			mediaParse += valorParse;

			if (valorParse < minParse)
				minParse = valorParse;

			if (valorParse > maxParse)
				maxParse = valorParse;

			// Gravação
			double valorGravacao = Double.parseDouble(temposGravacao.get(i));

			mediaGravacao += valorGravacao;

			if (valorGravacao < minGravacao)
				minGravacao = valorGravacao;

			if (valorGravacao > maxGravacao)
				maxGravacao = valorGravacao;
		}

		mediaLeitura = mediaLeitura / temposLeitura.size();
		mediaParse = mediaParse / temposParse.size();
		mediaGravacao = mediaGravacao / temposGravacao.size();

		tempo.getTxtMinLeitura().setText(Double.toString(minLeitura));
		tempo.getTxtMaxLeitura().setText(Double.toString(maxLeitura));
		tempo.getTxtMediaLeitura().setText(Double.toString(mediaLeitura));

		tempo.getTxtMinParse().setText(Double.toString(minParse));
		tempo.getTxtMaxParse().setText(Double.toString(maxParse));
		tempo.getTxtMediaParse().setText(Double.toString(mediaParse));

		tempo.getTxtMinGravacao().setText(Double.toString(minGravacao));
		tempo.getTxtMaxGravacao().setText(Double.toString(maxGravacao));
		tempo.getTxtMediaGravacao().setText(Double.toString(mediaGravacao));

		tempo.getLblQuantidade().setText(Integer.toString(logs.size()));
	}
	
}
