package servidor;

import java.nio.file.Path;
import java.nio.file.Paths;

import servidor.controller.ConversaoController;

public class Main {

	public static void main(String[] args) {
		/*
		 * try { do { System.out.println("[ Iniciando o servidor...... ]"); ServerSocket
		 * server = new ServerSocket(8080); System.out.print("[ OK ]\n");
		 * 
		 * System.out.println("[ Esperando por conexões.... ] "); Socket cliente =
		 * server.accept(); System.out.print("[ OK ]\n");
		 * 
		 * 
		 * 
		 * } while (true);
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * System.out.println("Servidor encerrado.");
		 */

		try {

			Path leitura = Paths.get("C:\\Users\\Matheus de Oliveira\\Desktop\\brasil.csv");
			Path gravacao = Paths.get(System.getProperty("java.io.tmpdir") + "brasil.txt");			
			
			new ConversaoController().realizarOperacoes(leitura, gravacao);
		} catch (Exception ex) {
			ex.getStackTrace();
		}
	}
}
