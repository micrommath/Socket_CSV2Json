package servidor.models;

public class ProgressoGravacao {

	private int incrementoProgresso;
	private PBarsOperacoes pBarsOperacoes;
	private double acum;
	private int cont = 0;	
	private boolean terminado;
	
	public ProgressoGravacao(PBarsOperacoes pBarsOperacoes) {
		this.pBarsOperacoes = pBarsOperacoes;
		this.incrementoProgresso = pBarsOperacoes.getIncrementoPBar();
	}

	public synchronized void incrementarProgresso() {
		cont++;

		if (cont == incrementoProgresso) {
			acum += 0.01F;
			pBarsOperacoes.setGravacaoValor(acum);
			//System.out.println("Contador: " + cont + " || Acumulador: " + acum);
			cont = 0;			
		}
	}

	public double getPBarValue() {
		return this.acum;
	}
	
	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}
	
	public boolean getTerminado() {
		return this.terminado;
	}
}
