package cliente.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import cliente.models.ConexaoServidor;
import cliente.models.Tasks;
import cliente.models.TempoViewModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import servidor.models.Feedback;

public class InicialController implements Initializable {

	@FXML
	private Button btnChoose;

	@FXML
	private Label txtFileChoose;

	@FXML
	private ProgressBar progBarLeitura;

	@FXML
	private ProgressBar progBarConversao;

	@FXML
	private ProgressBar progBarGravacao;

	@FXML
	private Button btnStart;

	@FXML
	private TextField txtPorta;

	@FXML
	private TextField txtServer;

	@FXML
	private Label lblQuantidade;

	@FXML
	private Label txtFileSaida;

	@FXML
	private ProgressBar pBarFilaConversao;

	@FXML
	private ProgressBar pBarFilaLeitura;

    @FXML
    private TextField txtNumLeitura;

    @FXML
    private TextField txtNumConversao;

    @FXML
    private TextArea txtaStatusConversao;

    @FXML
    private TextArea txtaStatusLeitura;

    @FXML
    private TextArea txtaStatusGravacao;

	private String caminhoArquivo = null;

	private String caminhoArquivoSaida;

	private TemposController tempoController;

	@SuppressWarnings("unused")
	private final Path arquivoTempos = Paths.get(System.getProperty("java.io.tmpdir") + "tempoCsvToJson.txt");

	public InicialController() {
		tempoController = new TemposController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	

		// Valores padrão
		this.caminhoArquivoSaida = System.getProperty("java.io.tmpdir") + "brasil.txt";
		txtFileSaida.setText(this.caminhoArquivoSaida);
		txtServer.setText("localhost");
		txtPorta.setText("8080");
	}

	public void btnChooseAction(ActionEvent event) throws InterruptedException {

		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile == null)
			return;

		if (selectedFile.getPath() != null)
			txtFileChoose.setText(selectedFile.getName());

		caminhoArquivo = selectedFile.getPath();
	}

	public void btnStartAction(ActionEvent event) throws InterruptedException {

		if (caminhoArquivo != null && caminhoArquivo.endsWith(".csv")) {

			// semTravarThread();

			travandoThread();

		} else {
			Alerta(AlertType.WARNING, "Arquivo", "Falha",
					"Escolha um arquivo válido para iniciar\nFormato aceito: CSV File (.csv)");
		}
	}

	private void travandoThread() {

		try {

			Tasks tasks = new Tasks();

			String host = txtServer.getText();
			int porta = Integer.parseInt(txtPorta.getText());
			String caminhoLeitura = caminhoArquivo;
			String caminhoGravacao = caminhoArquivoSaida;

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

					updateUI(feedback, tasks);

					// Obj com informações retornadas do servidor
					System.out.println(feedback.toString());

					if (!cliente.isConnected()
							|| (feedback.getSizeFilaLeitura() == 0 && feedback.getSizeFilaParse() == 0)) {

						cliente.close();

						break;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void semTravarThread() {
		// Conexao com o servidor
		ConexaoServidor conexao = new ConexaoServidor();
		conexao.Conectar(txtServer.getText(), Integer.parseInt(txtPorta.getText()), caminhoArquivo, caminhoArquivoSaida,
				progBarLeitura, txtaStatusLeitura);
	}

	private void Alerta(AlertType tipo, String titulo, String cabecalho, String mensagem) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(mensagem);
		alert.show();
	}

	private void updateUI(Feedback feedback, Tasks tasks) {

		Task<Void> tUpdateLeitura = tasks.updateTaskLeitura(feedback.getPbarLeituraValor());
		Task<Void> tUpdateConversao = tasks.updateTaskConversao(feedback.getPbarConversaoValor());
		Task<Void> tUpdateGravacao = tasks.updateTaskGravacao(feedback.getPbarGravacaoValor());
		Task<Void> tUpdateFilaLeitura = tasks.updateTaskFilaLeitura(feedback.getSizeFilaLeitura());
		Task<Void> tUpdateFilaConversao = tasks.updateTaskFilaConversao(feedback.getSizeFilaParse());
		
		Task<Void> tUpdateSitLeitura = tasks.updateTaskSitLeitura(feedback.getStatusLeitura());
        Task<Void> tUpdateSitConversao = tasks.updateTaskSitConversao(feedback.getStatusParse());
		Task<Void> tUpdateSitgravacao = tasks.updateTaskSitGravacao(feedback.getStatusGravacao());
		
		Task<Void> tUpdateNumLeitura = tasks.updateTaskNumGravacao(feedback.getSizeFilaLeitura());
		Task<Void> tUpdateNumConversao = tasks.updateTaskNumConversao(feedback.getSizeFilaParse());
		
		// progress bar
		progBarLeitura.progressProperty().bind(tUpdateLeitura.progressProperty());
		progBarConversao.progressProperty().bind(tUpdateConversao.progressProperty());
		progBarGravacao.progressProperty().bind(tUpdateGravacao.progressProperty());
		pBarFilaLeitura.progressProperty().bind(tUpdateFilaLeitura.progressProperty());
		pBarFilaConversao.progressProperty().bind(tUpdateFilaConversao.progressProperty());

		// status 
		txtaStatusLeitura.textProperty().bind(tUpdateSitLeitura.messageProperty());
		txtaStatusConversao.textProperty().bind(tUpdateSitConversao.messageProperty());
		txtaStatusGravacao.textProperty().bind(tUpdateSitgravacao.messageProperty());
		
		// queue value
		txtNumLeitura.textProperty().bind(tUpdateNumLeitura.messageProperty());
		txtNumConversao.textProperty().bind(tUpdateNumConversao.messageProperty());
		
		new Thread(tUpdateLeitura).start();
		new Thread(tUpdateConversao).start();
		new Thread(tUpdateGravacao).start();
		new Thread(tUpdateFilaLeitura).start();
		new Thread(tUpdateFilaConversao).start();
		
		new Thread(tUpdateSitLeitura).start();
		new Thread(tUpdateSitConversao).start();
		new Thread(tUpdateSitgravacao).start();
		
		new Thread(tUpdateNumLeitura).start();
		new Thread(tUpdateNumConversao).start();
	}

}
