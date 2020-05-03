package servidor.models;

import java.net.Socket;
import java.nio.file.Path;

import servidor.controller.ConversaoController;

public class ThreadCliente implements Runnable{
	private Socket cliente;
	private Path pathLeitura;
	private Path pathGravacao;
	
	public ThreadCliente(Socket cliente, Path pathLeitura, Path pathGravacao) {
		this.cliente = cliente;
		this.pathLeitura = pathLeitura;
		this.pathGravacao = pathGravacao;
	}
	
	@Override
	public void run() {		
		new ConversaoController(this.cliente).realizarOperacoes(this.pathLeitura, this.pathGravacao);
	}

}
