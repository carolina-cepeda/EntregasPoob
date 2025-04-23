package domain;

import java.awt.Color;

/**
 * clase que describe una particula del juego
 * @author Carolina Cepeda
*/
public class Particula extends Elemento {
	private boolean isRed;
	private Color colorPersonalizado;

	/**
	 * constructor de la particula
	 * @param isRed : booleano que indica si la particula es del equipo rojo o azul
	 * @param px : posicion en x
	 * @param py : posicion en y
	 */
	public Particula(boolean isRed, int px, int py) {
		super(px, py);
		this.isRed = isRed;
	}

	/**
	 * metodo para mover la particula a la nueva posicion
	 * @param aumentoX : cantidad a mover en x
	 * @param aumentoY : cantidad a mover en y
	 */
	public void mover(int aumentoX, int aumentoY) {
		this.px += aumentoX;
		this.py += aumentoY;
	}

	/**
	 * metodo para verificar si la particula está en el lado correcto segun su equipo
	 * 
	 * @param h : altura del tablero
	 * @param w : ancho del tablero
	 * @return boolean
	 */
	public boolean estoyPosicionCorrecta(int h, int w) {
		if (isRed) {
			return this.px < w / 2;
		}
		return this.px > w / 2;
	}

	/**
	 * metodo para pasar la particula si esta choca con un demonio
	 * * @return void
	 */
	public void pasar() {
		if(isRed){
			mover(-1, 0);
		}
		else{
			mover(1, 0);
		}
	}

	/**
	 * metodo para retornar si es del equipo rojo  la particula
	 */
	public boolean isRed() {
		return isRed;
	}

	/**
	 * metodo para colocar un color personalizado a la particula
	 * @param c
	 */
	public void setColorPersonalizado(Color c) {
		this.colorPersonalizado = c;
	}
	/**
	 * metodo para retornar el color personalizado de la particula
	 * @return Color
	 */
	
	public Color getColorPersonalizado() {
		return colorPersonalizado;
	}
	

}
