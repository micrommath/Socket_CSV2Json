package models;

import javafx.concurrent.Task;

public class Parse {
	public Parse() {		
	}
	
	public Task<Void> getTask(int pBarIncrement, LogInformacoes logInformacoes){
		
		return new Task<Void>() {
			@Override
			protected Void call() {

				float acum = 0;
				int contador = 0;

				long tempoInicio = System.nanoTime();

				while (QueueRead.getSize() > 0) {

					Brasil informacao = new Brasil();
					String dado = "";
					dado = QueueRead.getData();

					String[] linha = dado.split(",");

					if (linha[0] == "Number") {
						continue;
					} else {

						informacao.setNumero(Integer.parseInt(linha[0]));
						informacao.setSexo(linha[1]);
						informacao.setNameSet(linha[2]);
						informacao.setTitulo(linha[3]);
						informacao.setNomeDado(linha[4]);
						informacao.setSobreNome(linha[5]);
						informacao.setEndereco(linha[6]);
						informacao.setCidade(linha[7]);
						informacao.setEstado(linha[8]);
						informacao.setCodigoZip(linha[9]);
						informacao.setPaisCompleto(linha[10]);
						informacao.setEmail(linha[11]);
						informacao.setUsuario(linha[12]);
						informacao.setSenha(linha[13]);
						informacao.setTelefone(linha[14]);
						informacao.setDataAniversario(linha[15]);
						informacao.setCcType(linha[16]);
						informacao.setCcNumber(linha[17]);
						informacao.setCcv2(linha[18]);
						informacao.setCcExpiracao(linha[19]);
						informacao.setNacionalID(linha[20]);
						informacao.setCor(linha[21]);
						informacao.setKilogramas(linha[22]);
						informacao.setCentimetros(linha[23]);
						informacao.setGuid(linha[24]);

						QueueParse.addData(informacao);

						contador++;

						if (contador == pBarIncrement) {
							acum += 0.01F;

							updateProgress(acum, 1);
							contador = 0;
						}
					}
				}

				long tempoFim = System.nanoTime();

				long duration = (tempoFim - tempoInicio) / 1000000;
				System.out.println("Conversão empo levado: " + duration + " milliseconds.");
				logInformacoes.setParse(duration);

				return null;
			}
		};
	}
}
