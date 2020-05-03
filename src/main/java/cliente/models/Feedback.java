package cliente.models;

import java.io.Serializable;

import javafx.scene.control.ProgressBar;

public class Feedback implements Serializable {	
		
	private static final long serialVersionUID = 129348938L;	
	
	public double pBarLeituraValor;	
	public double pBarConversaoValor;
	public double pBarGravacaoValor;
	
	public Feedback(double pBarLeituraValor, double pBarConversaoValor, double pBarGravacaoValor) {
		this.pBarLeituraValor = pBarLeituraValor;
		this.pBarConversaoValor = pBarConversaoValor;
		this.pBarGravacaoValor = pBarGravacaoValor;
	}
	
	public void progressBarOperacoes(ProgressBar progBarLeitura, ProgressBar progBarConversao,
			ProgressBar progBarGravacao) {
		progBarLeitura.setProgress(pBarLeituraValor);
		
	}
	
	@Override
	public String toString() {
		return "Feedback [pBarLeituraValor=" + pBarLeituraValor + ", pBarConversaoValor=" + pBarConversaoValor
				+ ", pBarGravacaoValor=" + pBarGravacaoValor + "]";
	}
}
