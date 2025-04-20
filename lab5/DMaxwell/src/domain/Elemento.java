package domain;

public abstract class Elemento {

	protected int px;
	protected int py;

	public Elemento(int px, int py) {
		this.px = px;
		this.py = py;
	}

	public boolean EstoyAhi(int px, int py) {
		return (this.px == px && this.py == py);
	}
	
	public boolean EstoyEnPosicionValida(int h, int w) {
		return this.py > 0 && this.py < h + 1 && this.px > 0 && this.px < w + 2;
	}

}
