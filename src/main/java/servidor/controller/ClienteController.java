package servidor.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClienteController implements Runnable {

	private Socket cliente;

	public ClienteController(Socket cliente) {
		this.cliente = cliente;
	}

	@Override
	public void run() {

		while (!cliente.isClosed()) {
			try {

				InputStream entrada;
				entrada = cliente.getInputStream();

				Scanner scan = new Scanner(entrada);
				String caminho = scan.nextLine();
				String[] caminhos = caminho.split(";");
				scan.close();

				if(caminhos == null || caminhos.length < 2) {
					try {
						throw new Exception("Entrada de caminhos inválida");
					} catch (Exception e) { 
						e.printStackTrace();
					}
				}
				
				Path pathLeitura = Paths.get(caminhos[0]);
				Path pathGravacao = Paths.get(caminhos[1]);

				Thread thCliente = new Thread(new Runnable() {
					@Override
					public void run() {
						new ConversaoController(cliente).realizarOperacoes(pathLeitura, pathGravacao);
					}
				});

				thCliente.start();

			} catch (NoSuchElementException e) {
				System.out.println("Operação cancelada pelo usuário");				
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			cliente.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

}
