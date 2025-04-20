package domain;

public class Demonio extends Elemento {

	public Demonio(int px, int py){
		super(px, py);
	}
	public boolean pasar(Particula particula) {
		return particula.EstoyAhi(px, py);
	}

}
