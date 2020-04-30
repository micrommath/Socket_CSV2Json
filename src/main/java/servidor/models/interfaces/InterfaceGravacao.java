package servidor.models.interfaces;

import java.nio.file.Path;

import javafx.concurrent.Task;

public interface InterfaceGravacao {
	Task<Void> getTaskGravacao(Path caminhoSalvar, InterfaceFilaConversao fila);
}
