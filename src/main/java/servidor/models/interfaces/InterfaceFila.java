package servidor.models.interfaces;

public interface InterfaceFila {
	int getSize();
	boolean getEstaVazio();
	boolean getTerminou();
	void setTerminou(boolean terminou);
}
