package cliente.controllers;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

import cliente.models.Feedback;
import cliente.models.LogInformacoes;
import cliente.models.TempoViewModel;
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
	private TextField txtMinLeitura;

	@FXML
	private TextField txtMaxLeitura;

	@FXML
	private TextField txtMediaLeitura;

	@FXML
	private TextField txtMinParse;

	@FXML
	private TextField txtMaxParse;

	@FXML
	private TextField txtMediaParse;

	@FXML
	private TextField txtMinGravacao;

	@FXML
	private TextField txtMaxGravacao;

	@FXML
	private TextField txtMediaGravacao;

	@FXML
	private Label lblQuantidade;

	@FXML
	private Label txtFileSaida;
	
    @FXML
    private ProgressBar pBarFilaConversao;

    @FXML
    private ProgressBar pBarFilaLeitura;

    @FXML
    private TextArea txtaStatus;

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

		TempoViewModel tempo = new TempoViewModel(txtMinLeitura, txtMaxLeitura, txtMediaLeitura, txtMinParse,
				txtMaxParse, txtMediaParse, txtMinGravacao, txtMaxGravacao, txtMediaGravacao, lblQuantidade);
		tempoController.CalcularTempos(tempo);

		this.caminhoArquivoSaida = System.getProperty("java.io.tmpdir") + "brasil.txt";
		txtFileSaida.setText(this.caminhoArquivoSaida);
		
		txtServer.setText("localhost");
		txtPorta.setText("8080");
	}

	public void btnChooseAction(ActionEvent event) throws InterruptedException {

		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile.getPath() != null)
			txtFileChoose.setText(selectedFile.getName());

		caminhoArquivo = selectedFile.getPath();
	}

	public void btnStartAction(ActionEvent event) throws InterruptedException {

		if (caminhoArquivo != null && caminhoArquivo.endsWith(".csv")) {

			// caminhoArquivo;
			// caminhoArquivoSaida

			// LogInformacoes logInformacoes = new LogInformacoes();

			this.conexaoServidor(this.caminhoArquivo, this.caminhoArquivoSaida);
		} else {
			Alerta(AlertType.WARNING, "Arquivo", "Falha",
					"Escolha um arquivo válido para iniciar\nFormato aceito: CSV File (.csv)");
		}
	}

	private void conexaoServidor(String caminhoLeitura, String caminhoGravacao) {
		Socket cliente = null;
		try {
			String host = txtServer.getText();
			int porta = Integer.parseInt(txtPorta.getText());

			cliente = new Socket(host, porta);

			PrintStream saida = new PrintStream(cliente.getOutputStream());

			String caminhos = caminhoLeitura + ";" + caminhoGravacao;
			saida.println(caminhos);
			saida.close();
			
			while (!cliente.isClosed()) {
				ObjectInputStream  in = new ObjectInputStream (cliente.getInputStream());
				
				Feedback feedback = (Feedback)in.readObject();
				feedback.toString();			
				//feedback.progressBarOperacoes(progBarLeitura, progBarConversao, progBarGravacao);
			}
			
		} catch (UnknownHostException e) {
			System.out.println("Falha ao se conectar com o servidor: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void Alerta(AlertType tipo, String titulo, String cabecalho, String mensagem) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(mensagem);
		alert.show();
	}

}
