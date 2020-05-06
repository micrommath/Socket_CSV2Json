package cliente.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import cliente.models.ConexaoServidor;
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

		// Valores padrão
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

			// Conexao com o servidor
			ConexaoServidor conexao = new ConexaoServidor();
			conexao.Conectar(txtServer.getText(), Integer.parseInt(txtPorta.getText()), caminhoArquivo, caminhoArquivoSaida, progBarLeitura);			
			
		} else {
			Alerta(AlertType.WARNING, "Arquivo", "Falha",
					"Escolha um arquivo válido para iniciar\nFormato aceito: CSV File (.csv)");
		}
	}

	public Task<Void> updateTaskLeitura() {
		
		return new Task<Void>() {
			@Override
			protected Void call() {

				
				
				return null;
			}
		};
	}
	
	private void Alerta(AlertType tipo, String titulo, String cabecalho, String mensagem) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(mensagem);
		alert.show();
	}

}
