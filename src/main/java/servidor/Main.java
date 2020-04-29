package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		try {
			do {
				System.out.println("[ Iniciando o servidor...... ]");
				ServerSocket server = new ServerSocket(8080);
				System.out.print("[ OK ]\n");

				System.out.println("[ Esperando por conexões.... ] ");
				Socket cliente = server.accept();
				System.out.print("[ OK ]\n");
								
				
								
			} while (true);

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Servidor encerrado.");
	}
}
