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

	private ArrayList<Elemento> elementos;

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
// mejorar distribucion
	public DMaxwell (int h, int w, int r, int b, int o) {
		this.h = h;
		this.w = w;
		this.cantidadRojas = r;
		this.cantidadAzules = b;
		this.cantidadHoles = o;
		this.afectadas = 0;
		this.elementos = new ArrayList<>();
		Random random = new Random();
		Set<String> posiciones = new HashSet<>();
		int totalElementos = cantidadRojas + cantidadAzules + cantidadHoles;
		int elementosGenerados = 0;

		while(elementosGenerados < totalElementos){
			int px = random.nextInt(w);
			int py = random.nextInt(h);
			if (px == w / 2) continue;
			if (elementosGenerados < r) {
				if (px <= w/2) continue; 
			} 
			else if (elementosGenerados < r + b) {
				if (px > w/2) continue; 
			}
			String clave = px + ","+ py;

			if(!posiciones.contains(clave)){
				posiciones.add(clave);
				if (elementosGenerados < r) {
					Particula particula = new Particula(true, px, py);
					elementos.add(particula);
				} else if (elementosGenerados < r + b) {
					Particula particula = new Particula(false, px, py);
					elementos.add(particula);
				} else {
					Agujero agujero = new Agujero(px, py);
					elementos.add(agujero);
				}
				
				elementosGenerados++;
			}
		}
		Demonio demonio = new Demonio(w/2, h/2);
		elementos.add(demonio);
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
		if (simulacionTerminada) return;
		for (Iterator<Elemento> iterator = elementos.iterator(); iterator.hasNext();) {
			Elemento elemento = iterator.next();
			if (elemento.estoyAhi(px, py) && elemento instanceof Particula particula) {
				int nuevaX = px + aumentoX;
				int nuevaY = py + aumentoY;
	
				for (Elemento e : elementos) {
					if (e instanceof Demonio demonio && demonio.estoyAhi(nuevaX, nuevaY)) {

						int posFinalX = particula.isRed() ? nuevaX - 1 : nuevaX + 1;
						int posFinalY = nuevaY;
						
						if (posicionOcupadaPorParticula(posFinalX, posFinalY, particula)) {
							return; 
						}

						particula.mover(aumentoX, aumentoY);

						particula.pasar();

						for (Elemento agujero : elementos) {
							if (agujero instanceof Agujero a && a.cae(particula)) {
								iterator.remove();
								afectadas++;
								finish();
								return;
							}
						}
						return;
					}
				}

				if (!posicionOcupadaPorParticula(nuevaX, nuevaY, particula) &&
					nuevaX >= 0 && nuevaX < w+1 && nuevaY >= 0 && nuevaY < h) {
					particula.mover(aumentoX, aumentoY);

					for (Elemento e : elementos) {
						if (e instanceof Agujero agujero && agujero.cae(particula)) {
							iterator.remove();
							afectadas++;
							finish();
							return;
						}
					}
				}

		        finish();
				return;
			}
		}
		finish();
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
		int totalDisponibles = totalParticulas - afectadas; 
		
		for (Elemento elemento : elementos) {
			if (elemento instanceof Particula particula) {
				if (particula.estoyPosicionCorrecta(h, w)) {
					correctas++;
				}
			}
		}
		return (correctas * 100.0) / totalDisponibles;
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
	
	public boolean cambiarColorParticulaEn(int x, int y, Color nuevoColor) {
		for (Elemento e : elementos) {
			if (e instanceof Particula p && p.estoyAhi(x, y)) {
				p.setColorPersonalizado(nuevoColor);
				return true;
			}
		}
		return false;
	}
	
	public boolean finish() {
		double porcentajeCorrectas = calcularParticulasCorrectas();
		double porcentajePerdidas = calcularParticulasCaidas();
		
		if (porcentajeCorrectas >= 100.0 || porcentajePerdidas >= 100.0) {
			simulacionTerminada = true;
			return true;
		}
		return false;
	}
	public boolean isSimulacionTerminada() {
		return simulacionTerminada;
	}
}
