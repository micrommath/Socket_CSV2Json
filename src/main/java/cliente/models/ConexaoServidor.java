package cliente.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import servidor.models.Feedback;

public class ConexaoServidor implements Runnable {

	private Socket cliente;
	private ProgressBar pBarFilaConversao;
	private ProgressBar pBarFilaLeitura;
	private String caminhoLeitura;
	private String caminhoGravacao;
	private TasksUpdate tasksUpdate;
	
	public ConexaoServidor(Socket cliente, ProgressBar pBarFilaLeitura, ProgressBar pBarFilaConversao,
			String caminhoLeitura, String caminhoGravacao) {
		this.cliente = cliente;
		this.pBarFilaLeitura = pBarFilaLeitura;
		this.pBarFilaConversao = pBarFilaConversao;
		this.caminhoLeitura = caminhoLeitura;
		this.caminhoGravacao = caminhoGravacao;
	}

	@Override
	public void run() {
		try {
			tasksUpdate = new TasksUpdate(pBarFilaConversao, pBarFilaLeitura);
			
			PrintStream saida = new PrintStream(cliente.getOutputStream());

			String caminhos = caminhoLeitura + ";" + caminhoGravacao;
			saida.println(caminhos);			

			while (!cliente.isClosed()) {

				InputStream entrada = cliente.getInputStream();

				if (entrada.available() > 0) {
					byte[] arrBytes = new byte[3000];
					entrada.read(arrBytes);

					ByteArrayInputStream bos = new ByteArrayInputStream(arrBytes);
					ObjectInputStream oos = new ObjectInputStream(bos);

					Object obj = oos.readObject();

					Feedback feedback = (Feedback) obj;
					
					System.out.println(feedback.toString());
					
					//tasksUpdate.updatePFilaBarLeitura(feedback.getPbarLeituraValor(), feedback.getTotalLinhas());
					//tasksUpdate.updatePBarFilaConversao(feedback.getPbarLeituraValor(), feedback.getTotalLinhas());
				} 
			}					

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
