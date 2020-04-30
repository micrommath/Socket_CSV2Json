package servidor.models.interfaces;

import java.nio.file.Path;

import javafx.concurrent.Task;

public interface InterfaceLeitura {
	Task<Void> getTaskLeitura(Path caminho, InterfaceFilaLeitura fila);
}
