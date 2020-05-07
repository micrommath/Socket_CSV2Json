package servidor.models;

import java.io.Serializable;

public class Feedback implements Serializable {

	private static final long serialVersionUID = -7178055036684378122L;
	private double pBarLeituraValor;
	private double pBarConversaoValor;
	private double pBarGravacaoValor;
	private double sizeFilaLeitura;
	private double sizeFilaParse;
	private int totalLinhas;
	
	private String statusLeitura;
	private String statusParse;
	private String statusGravacao;

	public Feedback() {
	}

	public Feedback(double pBarLeituraValor, double pBarConversaoValor, double pBarGravacaoValor) {
		this.pBarLeituraValor = pBarLeituraValor;
		this.pBarConversaoValor = pBarConversaoValor;
		this.pBarGravacaoValor = pBarGravacaoValor;
	}

	public void setPbarLeituraValor(double valor) {
		this.pBarLeituraValor = valor;
	}

	public void setPbarConversaoValor(double valor) {
		this.pBarConversaoValor = valor;
	}

	public void setPbarGravacaoValor(double valor) {
		this.pBarGravacaoValor = valor;
	}

	public void setSizeFilaLeitura(int sizeFilaLeitura) {
		this.sizeFilaLeitura = sizeFilaLeitura;
	}

	public double getSizeFilaLeitura() { 
		return this.sizeFilaLeitura;
	}
	
	public void setSizeFilaParse(int sizeFilaParse) {
		this.sizeFilaParse = sizeFilaParse;
	}

	public double getSizeFilaParse() { 
		return this.sizeFilaParse;
	}

	public double getPbarLeituraValor() {
		return this.pBarLeituraValor;
	}

	public void setTotalLinhas(int totalLinhas) {
		this.totalLinhas = totalLinhas;		
	}
	
	public int getTotalLinhas() {
		return this.totalLinhas;
	}	

	public void setStatusLeitura(String status) {
		this.statusLeitura = status;		
	}
	
	public void setStatusParse(String status) {		
		this.statusParse = status;		
	}
	
	public void setStatusGravacao(String status) {
		this.statusGravacao = status;		
	}

	public String getStatusLeitura() {
		return statusLeitura;
	}

	public String getStatusParse() {
		return statusParse;
	}

	public String getStatusGravacao() {
		return statusGravacao;
	}

	@Override
	public String toString() {
		return "pBarLeituraValor: " + pBarLeituraValor + ", " 
				+ "pBarConversaoValor: " + pBarConversaoValor + ", "
				+ "pBarGravacaoValor: " + pBarGravacaoValor + ", "
				+ "sizeFilaLeitura: " + sizeFilaLeitura + ", "
				+ "sizeFilaParse: " + sizeFilaParse + ", "				
				+ "statusLeitura: " + statusLeitura + ", "
				+ "statusParse: " + statusParse + ", "
				+ "statusGravacao: " + statusGravacao + ", "								
				+ "TotalLinhas: " + totalLinhas;
	}
	
}
