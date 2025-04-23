package domain;

/**
 * Clase que representa un agujero en el juego DMaxwell.
 * Un agujero es un elemento que puede afectar a las particulas en el tablero.
 * Si una particula cae en un agujero, se considera que ha sido afectada.
 * @author Carolina cepeda
 */
public class Agujero extends Elemento {

	/**
	 * constructor del agujero
	 * @param px : posicion en x
	 * @param py : posicion en y
	 */
	public Agujero(int px, int py){
		super(px,py);
	}
	/**
	 * metodo para verificar si una particula cae en el agujero o no
	 * @param p
	 * @return boolean
	 */
	public boolean cae(Particula p) {
		return p.estoyAhi(px, py);
	}

}
