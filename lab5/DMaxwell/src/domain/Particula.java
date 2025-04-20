package domain;

public class Particula extends Elemento {

        @SuppressWarnings("FieldMayBeFinal")
	private boolean isRed;

	public Particula(boolean isRed, int px, int py) {
		super(px, py);
		this.isRed = isRed;
	}

	public void Mover(int aumentoX, int aumentoY) {
		this.px += aumentoX;
		this.py += aumentoY;
	}

	public boolean EstoyPosicionCorrecta(int h, int w) {
		if (isRed) {
			return this.px < w / 2;
		}
		return this.px > w / 2;
	}

}
