package dominio;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;


public class changingTrainer extends EntrenadorMaquina {

	private boolean yaCambio = false;
	public changingTrainer(String nombre, String color) {
		super(nombre, color);
	}


	@Override
	public Entry<String, Object> decidirAccion(Pokemon enemigo) {
		if (!yaCambio) {
			double mejorEfectividad = 1.0;
			Pokemon mejorOpcion = null;

			for (Pokemon p : getPokemones()) {
				if (p.getSalud() > 0 && p != getPokemonActivo()) {
					double efectividad = TipoEfectividad.getMultiplicador(
						p.getTipoPrincipal().toUpperCase(),
						enemigo.getTipoPrincipal().toUpperCase()
					);
					if (efectividad > mejorEfectividad + 0.5) {
						mejorEfectividad = efectividad;
						mejorOpcion = p;
					}
				}
			}

			if (mejorOpcion != null) {
				yaCambio = true; // marcar que ya cambió
				return new AbstractMap.SimpleEntry<>("cambiar", mejorOpcion);
			}
		}

		Pokemon activo = getPokemonActivo();
		ArrayList<Movimiento> movimientos = activo.getMovimientos();
		List<Integer> opciones = new ArrayList<>();
		for (int i = 0; i < movimientos.size(); i++) {
			if (movimientos.get(i).getPP() > 0) {
				opciones.add(i);
			}
		}

		if (!opciones.isEmpty()) {
			int randomIndex = new Random().nextInt(opciones.size());
			yaCambio = false; // próximo turno puede volver a cambiar
			return new AbstractMap.SimpleEntry<>("atacar", opciones.get(randomIndex));
		}
		return new AbstractMap.SimpleEntry<>("huir", null);
	}



}
