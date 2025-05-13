package dominio;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;

public class expertTrainer extends EntrenadorMaquina {

	public expertTrainer(String nombre, String color) {
		super(nombre, color);
	}
	
	@Override
	public Entry<String, Object> decidirAccion(Pokemon enemigo) {
		int mejorMovimiento = seleccionarMejorMovimiento(getPokemonActivo(), enemigo);

		if (mejorMovimiento != -1) {
			return new AbstractMap.SimpleEntry<>("atacar", mejorMovimiento);
		}

		// Si no hay movimientos disponibles, intenta cambiar a otro Pokémon
		Pokemon cambio = seleccionarMejorCambio(enemigo);
		if (cambio != null) {
			return new AbstractMap.SimpleEntry<>("cambiar", cambio);
		}

		// Si no puede atacar ni cambiar, huye
		return new AbstractMap.SimpleEntry<>("huir", null);
	}

	private int seleccionarMejorMovimiento(Pokemon propio, Pokemon enemigo) {
    ArrayList<Movimiento> movimientos = propio.getMovimientos();
    int mejorIndice = -1;
    double mejorValor = -1;

    for (int i = 0; i < movimientos.size(); i++) {
        Movimiento m = movimientos.get(i);
        if (m.getPP() <= 0) continue;

        double efectividad = TipoEfectividad.getMultiplicador(
            m.getTipo().toUpperCase(),
            enemigo.getTipoPrincipal().toUpperCase()
        );


        double valor = m.getPotencia() * efectividad;

        if (valor > mejorValor) {
            mejorValor = valor;
            mejorIndice = i;
        }
    }
    return mejorIndice;
}

private Pokemon seleccionarMejorCambio(Pokemon enemigo) {
    Pokemon actual = getPokemonActivo();
    double mejorEfectividad = 1.0;
    Pokemon mejor = null;

    for (Pokemon p : getPokemones()) {
        if (p == actual || p.getSalud() <= 0) continue;

        double efectividad = TipoEfectividad.getMultiplicador(
            p.getTipoPrincipal().toUpperCase(),
            enemigo.getTipoPrincipal().toUpperCase()
        );

        if (enemigo.getTipoSecundario() != null) {
            efectividad *= TipoEfectividad.getMultiplicador(
                p.getTipoPrincipal().toUpperCase(),
                enemigo.getTipoSecundario().toUpperCase()
            );
        }

        if (efectividad > mejorEfectividad) {
            mejorEfectividad = efectividad;
            mejor = p;
        }
    }

    return mejor;
}


}
