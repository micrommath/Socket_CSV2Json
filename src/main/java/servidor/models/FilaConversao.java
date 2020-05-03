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
	public synchronized void enfilerar(Brasil data) {
		try {
			fila.put(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized Brasil desenfilerar() {
		try {
			if (fila.size() > 0)
				return fila.take();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}	
	
	@Override
	public synchronized int getSize() {
		return fila.size();
	}
	
	@Override
	public synchronized boolean getTerminou() {
		return this.terminou;
	}
	
	@Override
	public synchronized void setTerminou(boolean terminou) {
		this.terminou = terminou;
	}

	@Override
	public synchronized boolean getEstaVazio() {
		return fila.isEmpty();
	}
}
