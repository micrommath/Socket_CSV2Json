package cliente.models;

import javafx.concurrent.Task;

public class Tasks {

	public Tasks() {
	}

	public Task<Void> updateTaskLeitura(double valor) {

		return new Task<Void>() {
			@Override
			protected Void call() {

				updateProgress(valor, 1F);

				return null;
			}
		};
	}

	public Task<Void> updateTaskConversao(double valor) {

		return new Task<Void>() {
			@Override
			protected Void call() {

				updateProgress(valor, 1F);

				return null;
			}
		};
	}

	public Task<Void> updateTaskGravacao(double valor) {

		return new Task<Void>() {
			@Override
			protected Void call() {

				updateProgress(valor, 1F);
				return null;
			}
		};
	}
	
	public Task<Void> updateTaskFilaLeitura(double valor) {

		return new Task<Void>() {
			@Override
			protected Void call() {

				updateProgress(valor, 1F);
				return null;
			}
		};
	}
	
	public Task<Void> updateTaskFilaConversao(double valor) {

		return new Task<Void>() {
			@Override
			protected Void call() {

				updateProgress(valor, 1F);
				return null;
			}
		};
	}

	public Task<Void> updateTaskSitLeitura(String statusLeitura) {
		return new Task<Void>() {
			@Override
			protected Void call() {

				updateMessage(statusLeitura);
				return null;
			}
		};
	}

	public Task<Void> updateTaskSitConversao(String statusParse) {
		return new Task<Void>() {
			@Override
			protected Void call() {

				updateMessage(statusParse);
				return null;
			}
		};
	}

	public Task<Void> updateTaskSitGravacao(String statusGravacao) {
		return new Task<Void>() {
			@Override
			protected Void call() {

				updateMessage(statusGravacao);
				return null;
			}
		};
	}

	public Task<Void> updateTaskNumGravacao(double value) {
		return new Task<Void>() {
			@Override
			protected Void call() {

				updateMessage(Double.toString(value));
				return null;
			}
		};
	}

	public Task<Void> updateTaskNumConversao(double value) {
		return new Task<Void>() {
			@Override
			protected Void call() {

				updateMessage(Double.toString(value));
				return null;
			}
		};
	}
}
