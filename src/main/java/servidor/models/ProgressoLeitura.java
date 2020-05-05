package servidor.models;

public class ProgressoLeitura {

	private int incrementoProgresso;
	private PBarsOperacoes pBarsOperacoes;
	private double acum;
	private int cont;

	public ProgressoLeitura(PBarsOperacoes pBarsOperacoes) {
		this.pBarsOperacoes = pBarsOperacoes;
		this.incrementoProgresso = pBarsOperacoes.getIncrementoPBar();
	}

	public synchronized void incrementarProgresso() {
		cont++;

		if (cont == incrementoProgresso) {
			acum += 0.01F;
			pBarsOperacoes.setLeituraValor(acum);
			cont = 0;
		}
	}

	public double getPBarValue() {
		return this.acum;
	}
}
