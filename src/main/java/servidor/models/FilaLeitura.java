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
	public void enfilerar(String data) {
		try {
			fila.put(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String desenfilerar() {
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
	public int getSize() {
		return fila.size();
	}

	@Override
	public boolean getEstaVazio() {
		return fila.isEmpty();
	}
	
	@Override
	public void setTerminou(boolean terminou) {
		this.terminou = terminou;
	}
	
	@Override
	public boolean getTerminou() {
		return this.terminou;
	}
}
