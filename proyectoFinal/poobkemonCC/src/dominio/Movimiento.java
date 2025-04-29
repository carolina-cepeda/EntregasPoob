package dominio;

import tipos.String;

public abstract class Movimiento {

	private String nombre;

	private int potencia;

	private int precision;

	private int pp;

	private int prioridad;

	private String tipo;

	private String efectoSecuntario;

	public Movimiento(String nombre, int potencia, int precision, int pp, int prioridad) {

	}

	public Movimiento AtaqueObjetivo(Pokemon pokemonObjetivo) {
		return null;
	}

}
