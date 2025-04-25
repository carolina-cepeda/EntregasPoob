package domain;

import java.awt.Color;
import java.util.*;
/**
 * Clase principal que representa el juego DMaxwell.
 * Contiene la logica del juego y los elementos que lo componen.
 * @author carolina cepeda
 */
public class DMaxwell {

	private int h;

	private int w;

	private int cantidadRojas;

	private int cantidadAzules;

	private int cantidadHoles;

	private int afectadas;

	private ArrayList<Elemento> elementos = new ArrayList<>();

	private boolean simulacionTerminada = false;

/**
 * Constructor de la clase DMaxwell.
 * Inicializa los atributos de la clase y genera los elementos en el tablero.
 * @param h : alto del tablero
 * @param w : ancho del tablero
 * @param r : cantidad de particulas rojas
 * @param b : cantidad de particulas azules
 * @param o : cantidad de agujeros
 */
public DMaxwell(int h, int w, int r, int b, int o) {
    this.h = h;
    this.w = w;
    this.cantidadRojas = r;
    this.cantidadAzules = b;
    this.cantidadHoles = o;
    this.afectadas = 0;

    Set<String> posiciones = new HashSet<>();
    Random random = new Random();

    generarParticulas(r, true, random, posiciones); 
    generarParticulas(b, false, random, posiciones); 
    generarAgujeros(o, random, posiciones); 

    elementos.add(new Demonio(w / 2, h / 2)); // demonio al centro
}

/**
 * Genera las particulas en el tablero.
 * @param cantidad
 * @param esRoja
 * @param random
 * @param posiciones
 */
private void generarParticulas(int cantidad, boolean esRoja, Random random, Set<String> posiciones) {
    int generadas = 0;
    while (generadas < cantidad) {
        int px = random.nextInt(w);
        int py = random.nextInt(h);

        if (px == w / 2) continue;
        if (esRoja && px <= w / 2) continue;
        if (!esRoja && px > w / 2 +1) continue;

        String clave = px + "," + py;
        if (posiciones.add(clave)) {
            elementos.add(new Particula(esRoja, px, py));
            generadas++;
        }
    }
}

/**
 * Genera los agujeros en el tablero.
 * @param cantidad
 * @param random
 * @param posiciones
 */
private void generarAgujeros(int cantidad, Random random, Set<String> posiciones) {
    int generados = 0;
    while (generados < cantidad) {
        int px = random.nextInt(w);
        int py = random.nextInt(h);

        if (px == w / 2) continue;

        String clave = px + "," + py;
        if (posiciones.add(clave)) {
            elementos.add(new Agujero(px, py));
            generados++;
        }
    }
}

	/**
	 * Mueve la particula a la nueva posicion, si no es valida, vuelve a la
	 * posicion anterior.Verifica si ha caido en un agujero antes de moverla y si 
	 * ha caido en un agujero no la mueve.
	 * @param px : posicion en x de la particula
	 * @param py : posicion en y de la particula
	 * @param aumentoX : cantidad a mover en x
	 * @param aumentoY : cantidad a mover en y
	 */
	public void moverParticula(int px, int py, int aumentoX, int aumentoY) {

		if (finish()) return;
	
		for (Iterator<Elemento> it = elementos.iterator(); it.hasNext();) {
			Elemento elemento = it.next();
	
			if (!(elemento.estoyAhi(px, py) && elemento instanceof Particula particula)) 
				continue;
	
			int nuevaX = px + aumentoX;
			int nuevaY = py + aumentoY;

			if (interactuarConDemonio(particula, nuevaX, nuevaY, it)) {
				finish();
				return;
			}
	
			if (!posicionOcupadaPorParticula(nuevaX, nuevaY, particula) &&
				nuevaX >= 0 && nuevaX < w +2 && nuevaY >= 0 && nuevaY < h) {
				particula.mover(aumentoX, aumentoY);
				if (verificarAgujero(particula, it)) return;
			}
	
			finish();
			return;
		}
		finish();
	}
	
	/**
	 * Verifica si la particula interactua con el demonio, si es asi, la mueve y
	 * verifica si cae en un agujero. Si cae en un agujero, la elimina de la lista de elementos.
	 * @param particula
	 * @param nuevaX
	 * @param nuevaY
	 * @param aumentoX
	 * @param aumentoY
	 * @param it :elemento actual
	 * @return true si la particula interactua con el demonio, false en caso contrario.
	 */
	private boolean interactuarConDemonio(Particula particula, int nuevaX, int nuevaY, Iterator<Elemento> it) {
		for (Elemento e : elementos) {
			if (e instanceof Demonio demonio && demonio.estoyAhi(nuevaX, nuevaY)) {
				boolean paso = demonio.pasar(particula, nuevaX, nuevaY);
				if (paso) {
					// Verificar la posición al lado del demonio
					int ladoX = particula.isRed() ? nuevaX - 1 : nuevaX + 1;
					if (!posicionOcupadaPorParticula(ladoX, nuevaY, particula)) {
						return verificarAgujero(particula, it);
					}
				}
				return false; 
			}
		}
		return false; 
	}
		
	/**
	 * Verifica si la particula cae en un agujero, si es asi, la elimina de la lista de elementos.
	 * @param particula
	 * @param it :elemento actual
	 * @return true si la particula cae en un agujero, false en caso contrario.
	 */
	private boolean verificarAgujero(Particula particula, Iterator<Elemento> it) {
		for (Elemento e : elementos) {
			if (e instanceof Agujero agujero && agujero.cae(particula)) {
				it.remove();
				afectadas++;
				finish();
				return true;
			}
		}
		return false;
	}

	/**
	 * Calcula la cantidad de Particulas que han caido en agujeros en 
	 * razon al total.
	 * @return porcentaje de particulas caidas.
	 */
	public double calcularParticulasCaidas() {
		return (afectadas * 100.0)/(cantidadRojas + cantidadAzules);
	}

	/**
	 * Calcula la cantidad de Particulas que han pasado por el demonio.
	 * en razon al total.
	 * @return porcentaje de particulas pasadas.
	 */

	 public double calcularParticulasCorrectas() {
		int totalParticulas = cantidadRojas + cantidadAzules;
		if (totalParticulas == 0) return 0.0;
		
		int correctas = 0;
		
		for (Elemento elemento : elementos) {
			if (elemento instanceof Particula particula) {
				if (particula.estoyPosicionCorrecta(h, w)) {
					correctas++;
				}
			}
		}
		return (correctas * 100.0) / totalParticulas;
	}

	/*
	* * Devuelve la cantidad de particulas que han caido en agujeros.
	* @return int cantidad de particulas caidas.
	*/
	public int getAfectadas() {
		return this.afectadas;
	}

	/**
	 * Devuelve la cantidad de agujeros en el tablero.
	 * @return int cantidad de agujeros.
	 */
	public int getCantidadHoles() {
		return this.cantidadHoles;
	}

	/**
	 * Devuelve la cantidad de particulas rojas en el tablero.
	 * @return int cantidad de particulas rojas.
	 */
	public int getCantidadRojas() {
		return this.cantidadRojas;
	}

	/**
	 * Devuelve la cantidad de particulas azules en el tablero.
	 * @return int cantidad de particulas azules.
	 */
	public int getCantidadAzules() {
		return this.cantidadAzules;
	}

	/**
	 * Devuelve la cantidad de filas del tablero.
	 * @return int cantidad de filas.
	 */
	public int getH() {
		return this.h;
	}

	/**
	 * Devuelve la cantidad de columnas del tablero.
	 * @return int cantidad de columnas.
	 */
	public int getW() {
		return this.w;
	}

	/**
	 * Devuelve la cantidad de demonios en el juego
	 * @return int cantidad de demonios.
	 */
	public int getCantidadDemonios() {
		int demonios = 0;
		for (Elemento elemento : elementos) {
			if (elemento instanceof Demonio) {
				demonios++;
			}
		}
		return demonios;
	}

	/**
	 * Devuelve la lista de elementos del tablero.
	 * @return ArrayList<Elemento> lista de elementos.
	 */
	public ArrayList<Elemento> getElementos() {
		return this.elementos;
	}

	/**
	 * Verifica si una posición está ocupada por otra partícula.
	 * @param px : posición en x a verificar.
	 * @param py : posición en y a verificar.
	 * @param actual : partícula actual que se está moviendo.
	 * @return true si la posición está ocupada, false en caso contrario.
	 */
	private boolean posicionOcupadaPorParticula(int x, int y, Particula actual) {
		for (Elemento e : elementos) {
			if (e instanceof Particula p && p != actual && p.estoyAhi(x, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param x : posicion en x de la particula a cambiar el color
	 * @param y : posicion en y de la particula a cambiar el color
	 * @param nuevoColor : nuevo color de la particula
	 * @return boolean : el proceso de cambiar el color de la particula fue exitoso.
	 */
	public boolean cambiarColorParticulaEn(int x, int y, Color nuevoColor) {
		for (Elemento e : elementos) {
			if (e instanceof Particula p && p.estoyAhi(x, y)) {
				p.setColorPersonalizado(nuevoColor);
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica si terminó el juego
	 * @return true si la simulación terinó, false en caso contrario.
	 */
	public boolean finish() {
		double porcentajeCorrectas = calcularParticulasCorrectas();
		double porcentajePerdidas = calcularParticulasCaidas();
		
		if (porcentajeCorrectas + porcentajePerdidas >= 100.0) {
			simulacionTerminada = true;
			return true;
		}
		return false;
	}
	
}
