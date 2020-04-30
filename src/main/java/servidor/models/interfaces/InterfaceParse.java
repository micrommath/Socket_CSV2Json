package servidor.models.interfaces;

import javafx.concurrent.Task;

public interface InterfaceParse {

	Task<Void> getTaskParse(InterfaceFilaLeitura filaLeitura, InterfaceFilaConversao filaConversao);
}
