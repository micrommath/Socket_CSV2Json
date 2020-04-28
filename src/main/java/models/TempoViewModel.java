package models;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TempoViewModel {
	
	private TextField txtMinLeitura;	

	private TextField txtMaxLeitura;
	private TextField txtMediaLeitura;

	private TextField txtMinParse;
	private TextField txtMaxParse;
	private TextField txtMediaParse;

	private TextField txtMinGravacao;
	private TextField txtMaxGravacao;
	private TextField txtMediaGravacao;

	private Label lblQuantidade;
	
	
	
	public TempoViewModel(TextField txtMinLeitura, TextField txtMaxLeitura, TextField txtMediaLeitura,
			TextField txtMinParse, TextField txtMaxParse, TextField txtMediaParse, TextField txtMinGravacao,
			TextField txtMaxGravacao, TextField txtMediaGravacao, Label lblQuantidade) {		
		this.txtMinLeitura = txtMinLeitura;
		this.txtMaxLeitura = txtMaxLeitura;
		this.txtMediaLeitura = txtMediaLeitura;
		this.txtMinParse = txtMinParse;
		this.txtMaxParse = txtMaxParse;
		this.txtMediaParse = txtMediaParse;
		this.txtMinGravacao = txtMinGravacao;
		this.txtMaxGravacao = txtMaxGravacao;
		this.txtMediaGravacao = txtMediaGravacao;
		this.lblQuantidade = lblQuantidade;
	}

	public TextField getTxtMinLeitura() {
		return txtMinLeitura;
	}

	public void setTxtMinLeitura(TextField txtMinLeitura) {
		this.txtMinLeitura = txtMinLeitura;
	}

	public TextField getTxtMaxLeitura() {
		return txtMaxLeitura;
	}

	public void setTxtMaxLeitura(TextField txtMaxLeitura) {
		this.txtMaxLeitura = txtMaxLeitura;
	}

	public TextField getTxtMediaLeitura() {
		return txtMediaLeitura;
	}

	public void setTxtMediaLeitura(TextField txtMediaLeitura) {
		this.txtMediaLeitura = txtMediaLeitura;
	}

	public TextField getTxtMinParse() {
		return txtMinParse;
	}

	public void setTxtMinParse(TextField txtMinParse) {
		this.txtMinParse = txtMinParse;
	}

	public TextField getTxtMaxParse() {
		return txtMaxParse;
	}

	public void setTxtMaxParse(TextField txtMaxParse) {
		this.txtMaxParse = txtMaxParse;
	}

	public TextField getTxtMediaParse() {
		return txtMediaParse;
	}

	public void setTxtMediaParse(TextField txtMediaParse) {
		this.txtMediaParse = txtMediaParse;
	}

	public TextField getTxtMinGravacao() {
		return txtMinGravacao;
	}

	public void setTxtMinGravacao(TextField txtMinGravacao) {
		this.txtMinGravacao = txtMinGravacao;
	}

	public TextField getTxtMaxGravacao() {
		return txtMaxGravacao;
	}

	public void setTxtMaxGravacao(TextField txtMaxGravacao) {
		this.txtMaxGravacao = txtMaxGravacao;
	}

	public TextField getTxtMediaGravacao() {
		return txtMediaGravacao;
	}

	public void setTxtMediaGravacao(TextField txtMediaGravacao) {
		this.txtMediaGravacao = txtMediaGravacao;
	}

	public Label getLblQuantidade() {
		return lblQuantidade;
	}

	public void setLblQuantidade(Label lblQuantidade) {
		this.lblQuantidade = lblQuantidade;
	}
}
