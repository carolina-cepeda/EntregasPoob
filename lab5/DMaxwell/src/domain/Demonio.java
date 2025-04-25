package domain;
/**
 * Clase que representa un demonio en el juego DMaxwell.
 * Un demonio es un elemento que puede afectar a las particulas en el tablero.
 * nc
 * @autor Carolina Cepeda
 */
public class Demonio extends Elemento {

	/**
	 * constructor del demonio
	 * @param px : posicion en x
	 * @param py : posicion en y
	 */
	public Demonio(int px, int py){
		super(px, py);
	}
	/**
	 * metodo para verificar si una particula pasa al demonio o no
	 * @param particula 
	 * @param targetX : posicion en x a verificar
	 * @param targetY : posicion en y a verificar
	 * @return boolean
	 */
	public boolean pasar(Particula particula, int targetX, int targetY) {
		if (estoyAhi(targetX, targetY)) {
			particula.pasar();
			return true;
		}
		return false;
	}
	

}
