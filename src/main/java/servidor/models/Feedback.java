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

	public void setSizeFilaParse(int sizeFilaParse) {
		this.sizeFilaParse = sizeFilaParse;
	}

	public double getSizeFilaParse() { 
		return this.sizeFilaParse;
	}

	public double getPbarLeituraValor() {
		return this.sizeFilaLeitura;
	}

	public void setTotalLinhas(int totalLinhas) {
		this.totalLinhas = totalLinhas;		
	}
	
	public int getTotalLinhas() {
		return this.totalLinhas;
	}

	@Override
	public String toString() {
		return "pBarLeitura: " + pBarLeituraValor + ", pBarConversao: " + pBarConversaoValor
				+ ", pBarGravacao: " + pBarGravacaoValor + ", qntdFilaLeitura: " + sizeFilaLeitura
				+ ", qntdFilaParse: " + sizeFilaParse + ", totalLinhasArquivo: " + totalLinhas;
	}
	
	
}
