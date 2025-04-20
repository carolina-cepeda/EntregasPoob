package domain;

public class Agujero extends Elemento {

	public Agujero(int px, int py){
		super(px,py);
	}
	public boolean cae(Particula p) {
		return p.EstoyAhi(px, py);
	}

}
