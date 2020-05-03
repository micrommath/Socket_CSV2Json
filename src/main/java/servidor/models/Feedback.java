package servidor.models;

import java.io.Serializable;

public class Feedback implements Serializable {	
	
	//private static final long serialVersionUID = 129348938L;	
	
	private double pBarLeituraValor;		
	private double pBarConversaoValor;
	private double pBarGravacaoValor;

	public Feedback(double pBarLeituraValor, double pBarConversaoValor, double pBarGravacaoValor) {
		this.pBarLeituraValor = pBarLeituraValor;
		this.pBarConversaoValor = pBarConversaoValor;
		this.pBarGravacaoValor = pBarGravacaoValor;
	}
	
	public void setPbarLeituraValor(double valor) {
		this.pBarLeituraValor = valor;
	}
	
}
