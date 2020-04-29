package servidor.models.interfaces;

import javafx.concurrent.Task;

public interface InterfaceParse {

	Task<Void> getTaskParse(int pBarIncrement, InterfaceFilaLeitura filaLeitura, InterfaceFilaConversao filaConversao);
}
