package servidor.models.interfaces;

import java.nio.file.Path;

import javafx.concurrent.Task;

public interface InterfaceLeitura {
	Task<Void> getTaskLeitura(int pBarIncrement, Path caminho, InterfaceFilaLeitura fila);
}
