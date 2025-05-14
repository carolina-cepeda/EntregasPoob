package dominio;

import java.util.*;

public class Pocion extends Item {

	private int puntosSanacion;

	private static final Map<String, Integer> tiposPocion = new HashMap<>() {{
		put("Potion", 20);
		put("SuperPotion", 50);
		put("HyperPotion", 200);
		put("Revive", 1/2); 
	}};

	public Pocion(String nombre) {
		super(nombre);
		if (!tiposPocion.containsKey(nombre)) {
			throw new IllegalArgumentException("El nombre de la poción no es válido: " + nombre);
		}
		this.puntosSanacion = tiposPocion.get(nombre);
	}

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
	public String toString() {
		return getNombre(); // usa el getter heredado
	}
}

