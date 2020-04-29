package servidor.models;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import servidor.models.interfaces.InterfaceFilaConversao;

public class FilaConversao implements InterfaceFilaConversao {

	private BlockingQueue<Brasil> fila;
	private boolean terminou;
	
	public FilaConversao(int capacidade) {
		fila = new ArrayBlockingQueue<Brasil>(capacidade);
		terminou = false;
	}

	@Override
	public void enfilerar(Brasil data) {
		try {
			fila.put(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Brasil desenfilerar() {
		try {
			if (fila.size() > 0)
				return fila.take();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}	
	
	@Override
	public int getSize() {
		return fila.size();
	}
	
	@Override
	public boolean getTerminou() {
		return this.terminou;
	}
	
	@Override
	public void setTerminou(boolean terminou) {
		this.terminou = terminou;
	}

	@Override
	public boolean getEstaVazio() {
		return fila.isEmpty();
	}
}
