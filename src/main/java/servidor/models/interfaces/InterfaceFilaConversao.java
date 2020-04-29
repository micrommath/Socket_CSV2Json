package servidor.models.interfaces;

import servidor.models.Brasil;

public interface InterfaceFilaConversao extends InterfaceFila{
	void enfilerar(Brasil data);
	Brasil desenfilerar();	
}
