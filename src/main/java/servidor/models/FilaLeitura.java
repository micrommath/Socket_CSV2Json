package servidor.models;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import servidor.models.interfaces.InterfaceFilaLeitura;

public class FilaLeitura implements InterfaceFilaLeitura {

	private BlockingQueue<String> fila;
	private boolean terminou;
	
	public FilaLeitura(int capacidade) {
		fila = new ArrayBlockingQueue<String>(capacidade);
		terminou = false;
	}

	@Override
	public synchronized void enfilerar(String data) {
		try {
			fila.put(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized String desenfilerar() {
		try {
			if (!fila.isEmpty()) {								
				return fila.take();							
			}
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
	public synchronized boolean getEstaVazio() {
		return fila.isEmpty();
	}
	
	@Override
	public synchronized void setTerminou(boolean terminou) {
		this.terminou = terminou;
	}
	
	@Override
	public synchronized boolean getTerminou() {
		return this.terminou;
	}
}
