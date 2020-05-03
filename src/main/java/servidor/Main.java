package servidor;

import java.net.ServerSocket;
import java.net.Socket;

import servidor.controller.ClienteController;

public class Main {

	public static void main(String[] args) {
		String nameHost = "localhost";
		int port = 8080;

		try {
			System.out.println("Host: " + nameHost + " Porta: " + port);
			System.out.print("[ Iniciando Servidor......................... ]");
			// Instância o servidor
			ServerSocket server = new ServerSocket(port);
			System.out.print("[ OK ]\n");

			while (true) {

				System.out.print("[ Esperando por conexões .................... ]\n");				

				// The METHOD BLOCKS until a connection is made.
				Socket cliente = server.accept();
				System.out.print("[ OK ]\n");

				// Cria e inicia uma nova thread para o cliente
				new Thread(new ClienteController(cliente)).start();
				
				System.out.println("[ Iniciando operações ....................... ]");
				
				System.out.println("\n\n");
			}
		} catch (

		Exception ex) {
			ex.getStackTrace();
		}
	}
}
