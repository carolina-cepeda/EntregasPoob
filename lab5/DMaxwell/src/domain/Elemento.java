package domain;
/**
 * clase que describe un elemento del juego
 */
public abstract class Elemento {

	protected int px;
	protected int py;

	/**
	 * constructor del elemento
	 * @param px : posicion en x
	 * @param py : posicion en y
	 */
	public Elemento(int px, int py) {
		this.px = px;
		this.py = py;
	}

	/**
	 * metodo para verificar si un elemento está en la posición dada
	 * @param px : posicion en x
	 * @param py : posicion en y
	 * @return boolean 
	 */
	public boolean estoyAhi(int px, int py) {
		return (this.px == px && this.py == py);
	}
	
	/**
	 * metodo para verificar que el elemento se encuentre dentro de las restricciones del tablero
	 * @param h : altura del tablero
	 * @param w : ancho del tablero
	 * @return boolean
	 */
	public boolean estoyEnPosicionValida(int h, int w) {
	
		if (this.px == w / 2 && this.py != h / 2) {
			return false;
		}
	
		return this.py > 0 && this.py < h + 1 && this.px > 0 && this.px < w + 2;
	}
}
