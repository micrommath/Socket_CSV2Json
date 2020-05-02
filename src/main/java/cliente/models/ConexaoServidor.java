package cliente.models;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConexaoServidor {

	private Socket cliente;
	private String host;
	private int porta;

	public ConexaoServidor(String host, int porta) {
		this.host = host;
		this.porta = porta;
	}

	public boolean conectar() {
		try {
			this.cliente = new Socket(host, porta);

		} catch (UnknownHostException e) {
			e.printStackTrace();

			return false;
		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}
		return true;
	}

	public void enviarCaminhos(String caminhoLeitura, String caminhoGravacao) {
		try {
			PrintStream saida = new PrintStream(cliente.getOutputStream());

			String caminhos = caminhoLeitura + ";" + caminhoGravacao;
			saida.println(caminhos);
			
			saida.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
