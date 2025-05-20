package dominio;

import java.io.Serializable;

/**
 * interfaz para menajr el modo juego normal y supervivencia
 */
public interface ModoJuego extends Serializable {

	public abstract void configurarJuego(Juego j);

}
