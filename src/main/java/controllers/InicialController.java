package controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import models.Conversao;
import models.LogInformacoes;
import models.TempoViewModel;

public class InicialController implements Initializable { 

	@FXML
	private BorderPane borderPane;

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

	private String caminhoArquivo = null;

	private TemposController tempoController;
	
	public InicialController() {
		tempoController = new TemposController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		TempoViewModel tempo = new TempoViewModel(txtMinLeitura, txtMaxLeitura, txtMediaLeitura, txtMinParse, txtMaxParse,
				txtMediaParse, txtMinGravacao, txtMaxGravacao, txtMediaGravacao, lblQuantidade);		
		tempoController.CalcularTempos(tempo); 

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

			Path caminhoLeitura = Paths.get(caminhoArquivo);
			Path caminhoGravacao = Paths.get(System.getProperty("java.io.tmpdir") + "brasil.txt");
			Path arquivoTempoLevado = Paths.get(System.getProperty("java.io.tmpdir") + "tempoCsvToJson.txt");
			LogInformacoes logInformacoes = new LogInformacoes();

			Conversao conversao = new Conversao();
			conversao.realizarOperacoes(caminhoLeitura, caminhoGravacao, progBarLeitura, progBarConversao,
					progBarGravacao, logInformacoes, arquivoTempoLevado);

		} else {
			AlertaWarning(AlertType.WARNING ,"Arquivo", "Escolha um arquivo", "Escolha um arquivo válido para iniciar");
		}
	}
	
	private void AlertaWarning(AlertType tipo, String titulo, String cabecalho, String mensagem) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(mensagem);
		alert.show();
		
	}

}
