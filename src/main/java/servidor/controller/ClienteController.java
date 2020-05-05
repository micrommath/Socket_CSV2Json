package servidor.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

import servidor.models.Feedback;
import servidor.models.ThreadCliente;

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

				Path pathLeitura = Paths.get(caminhos[0]);
				Path pathGravacao = Paths.get(caminhos[1]);				
				
				Thread thCliente = new Thread(new ThreadCliente(cliente, pathLeitura, pathGravacao));
				thCliente.start();

			} catch (NoSuchElementException e) {
				e.getStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.getStackTrace();
			}
		}

		try {
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
