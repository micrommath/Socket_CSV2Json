package cliente.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import cliente.models.ConexaoServidor;
import cliente.models.LogInformacoes;
import cliente.models.TempoViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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

	private String caminhoArquivo = null;

	private String caminhoArquivoSaida;

	private TemposController tempoController;

	private final Path arquivoTempoLevado = Paths.get(System.getProperty("java.io.tmpdir") + "tempoCsvToJson.txt");

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
	}

	public void btnChooseAction(ActionEvent event) throws InterruptedException {

		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		if (selectedFile.getPath() != null)
			txtFileChoose.setText(selectedFile.getName());

		caminhoArquivo = selectedFile.getPath();
	}

	public void btnStartAction(ActionEvent event) throws InterruptedException {

		if (caminhoArquivo != null) {

			// caminhoArquivo;
			// caminhoArquivoSaida

			//LogInformacoes logInformacoes = new LogInformacoes();
			String host = txtServer.getText();
			int porta = Integer.parseInt(txtPorta.getText());
			
			ConexaoServidor conServidor = new ConexaoServidor(host, porta);
			conServidor.conectar();
					
			
			conServidor.enviarCaminhos(this.caminhoArquivo, this.caminhoArquivoSaida);
			
		} else {
			Alerta(AlertType.WARNING, "Arquivo", "Escolha um arquivo", "Escolha um arquivo válido para iniciar");
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
