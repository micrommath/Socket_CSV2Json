package cliente.models;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

public class TasksUpdate {

	private ProgressBar pBarFilaConversao;
	private ProgressBar pBarFilaLeitura;
	
	public TasksUpdate(ProgressBar pBarFilaConversao, ProgressBar pBarFilaLeitura) {
		this.pBarFilaConversao = pBarFilaConversao;
		this.pBarFilaLeitura = pBarFilaLeitura;		
	}

	public void updatePFilaBarLeitura(double valorAtual, int totalRegistros) {

		Task<Void> update = new Task<Void>() {
			@Override
			protected Void call() {

				updateProgress(valorAtual, totalRegistros);
				return null;
			}
		};

		pBarFilaConversao.progressProperty().bind(update.progressProperty());
		
		new Thread(update).start();
	}

	public void updatePBarFilaConversao(double valorAtual, int totalRegistros) {

		Task<Void> update = new Task<Void>() {
			@Override
			protected Void call() {

				updateProgress(valorAtual, totalRegistros);
				return null;
			}
		};

		pBarFilaLeitura.progressProperty().bind(update.progressProperty());
		
		new Thread(update).start();
	}
}
