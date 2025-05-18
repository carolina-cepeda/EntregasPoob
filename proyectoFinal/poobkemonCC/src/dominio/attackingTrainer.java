package dominio;

import java.util.*;
import java.util.Map.Entry;

/**
 * clase del entrenador maquina que se especifica en ataque
 */
public class attackingTrainer extends EntrenadorMaquina {

	/**
	 * constructor
	 * @param nombre
	 * @param color
	 */
    public attackingTrainer(String nombre, String color) {
        super(nombre, color);
    }

	/**
	 * metodo para decidir accion
	 */
	@Override
	public Entry<String, Object> decidirAccion(Pokemon pokemonEnemigo) {
		Pokemon miPokemon = getPokemonActivo();
		ArrayList<Movimiento> movimientos = miPokemon.getMovimientos();

		int mejorIndice = 0;
		double mejorValor = -1;

		for (int i = 0; i < movimientos.size(); i++) {
			Movimiento m = movimientos.get(i);
			if (m.getPP() <= 0) continue;

			double efectividad = TipoEfectividad.getMultiplicador(
				m.getTipo().toUpperCase(),
				pokemonEnemigo.getTipoPrincipal().toUpperCase()
			);
			double valor = m.getPotencia() * efectividad;

			if (valor > mejorValor) {
				mejorValor = valor;
				mejorIndice = i;
			}
		}

		return Map.entry("atacar", mejorIndice);
	}


}

