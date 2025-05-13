package dominio;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;

public class defensiveTrainer extends EntrenadorMaquina {

	public defensiveTrainer(String nombre, String color) {
		super(nombre, color);
	}
	
	@Override
	public Entry<String, Object> decidirAccion(Pokemon enemigo) {
	
		for (Pokemon p : getPokemones()) {
			if (p.getSalud() <= 0) {
				for (Item item : getItems()) {
					if (item.getNombre().equalsIgnoreCase("Revive")) {
						return new AbstractMap.SimpleEntry<>("usaritem", item);
					}
				}
			}
		}

		String[] pociones = {"HyperPotion", "SuperPotion", "Potion"};
		for (String nombre : pociones) {
			for (Item item : getItems()) {
				if (item.getNombre().equalsIgnoreCase(nombre)) {
					return new AbstractMap.SimpleEntry<>("usaritem", item);
				}
			}
		}

		Pokemon activo = getPokemonActivo();
		ArrayList<Movimiento> movimientos = activo.getMovimientos();
		for (int i = 0; i < movimientos.size(); i++) {
			if (movimientos.get(i).getPP() > 0) {
				return new AbstractMap.SimpleEntry<>("atacar", i);
			}
		}

		return new AbstractMap.SimpleEntry<>("huir", null);
	}




}
