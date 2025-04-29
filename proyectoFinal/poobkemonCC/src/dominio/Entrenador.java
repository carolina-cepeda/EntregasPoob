package dominio;

import tipos.String;

public abstract class Entrenador {

	public String nombre;

	public String color;

	private Pokemon pokemonActual;

	private Pokemon[] pokemones;

	private Item[] items;

	public Entrenador(String nombre, String color) {

	}

	public void cambiarPokemon() {

	}

	public void seleccionarMovimiento(Pokemon pokemonObjetivo) {

	}

	public void usarItem(Item item, Pokemon objetivo) {

	}

	public Pokemon getPokemonActivo() {
		return null;
	}

}
