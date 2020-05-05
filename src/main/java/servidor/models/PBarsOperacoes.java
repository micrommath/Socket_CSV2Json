package servidor.models;

public class PBarsOperacoes {
	
	private double leituraValor;
	private double conversaoValor;
	private double gravacaoValor;
	private int incrementoPbar;
	
	public PBarsOperacoes(int incrementooPBar) {
		this.incrementoPbar = incrementooPBar;				
	}
	
	public int getIncrementoPBar() {
		return this.incrementoPbar;
	}
	
	public double getLeituraValor() {
		return leituraValor;
	}
	public void setLeituraValor(double leituraValor) {
		this.leituraValor = leituraValor;
	}
	public double getConversaoValor() {
		return conversaoValor;
	}
	public void setConversaoValor(double conversaoValor) {
		this.conversaoValor = conversaoValor;
	}
	public double getGravacaoValor() {
		return gravacaoValor;
	}
	public void setGravacaoValor(double gravacaoValor) {
		this.gravacaoValor = gravacaoValor;
	}
}
