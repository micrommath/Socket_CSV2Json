package cliente.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import servidor.models.Feedback;

public class ConexaoServidor {

	public ConexaoServidor() {
	}

	public void Conectar(String host, int porta, String caminhoLeitura, String caminhoGravacao, ProgressBar pBarFilaLeitura) {
		Task<Void> conServidor = getTask(host, porta, caminhoLeitura, caminhoGravacao, pBarFilaLeitura);		
		pBarFilaLeitura.progressProperty().bind(conServidor.progressProperty());
		new Thread(conServidor).start();					
	}

	public Task<Void> getTask(String host, int porta, String caminhoLeitura, String caminhoGravacao, ProgressBar pBarFilaLeitura) {

		return new Task<Void>() {
			@Override
			protected Void call() {

				try {
					
					Socket cliente = new Socket(host, porta);

					PrintStream saida = new PrintStream(cliente.getOutputStream());
					saida.println(caminhoLeitura + ";" + caminhoGravacao);

					InputStream entrada = cliente.getInputStream();
					Feedback feedback = null;
					while (!cliente.isClosed()) {
						if (entrada.available() > 0) {
							byte[] arrBytes = new byte[3000];
							entrada.read(arrBytes);

							ByteArrayInputStream bis = new ByteArrayInputStream(arrBytes);
							ObjectInputStream ois = new ObjectInputStream(bis);

							Object obj = ois.readObject();
							feedback = (Feedback) obj;							
							
							System.out.println(feedback.toString());
							
							updateProgress(feedback.getPbarLeituraValor(), 1F);																				
						}
					}
					
					cliente.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				return null;
			}
		};

	}
}
