package dominio;

import java.util.*;

/**
 * clase pocion que aumenta la salud de un pokemon
 * 
 */
public class Pocion extends Item {

	private int puntosSanacion;

	private static final Map<String, Integer> tiposPocion = new HashMap<>() {{
		put("Potion", 20);
		put("SuperPotion", 50);
		put("HyperPotion", 200);
		put("Revive", 1/2); 
	}};

	/**
	 * constructor
	 * @param nombre
	 */
	public Pocion(String nombre) {
		super(nombre);
		if (!tiposPocion.containsKey(nombre)) {
			throw new IllegalArgumentException("El nombre de la poción no es válido: " + nombre);
		}
		this.puntosSanacion = tiposPocion.get(nombre);
	}

	/**
	 * metodo para usar la pocion sobre un pokemon
	 */

	@Override
	public void usar(Pokemon p) {
		String nombre = getNombre(); // usa el nombre heredado

		if (!tiposPocion.containsKey(nombre)) {
			throw new IllegalArgumentException("El nombre de la poción no es válido: " + nombre);
		}

		if (nombre.equals("Revive")) {
			if (p.getSalud() == 0) {
				p.aumentoSalud(p.getSaludInicial() / 2);
			}
		} else {
			if (p.getSalud() > 0) {
				p.aumentoSalud(puntosSanacion);
			}
		}
	}

	@Override
	/**
	 * metodo que devuelve el nombre de la pocion
	 */
	public String toString() {
		return getNombre(); // usa el getter heredado
	}
}

