package dominio;

import tipos.String;

/**
 * hacere abstracta -
 */
public abstract class Pokemon {

	public String nombre;

	private int salud;
	
	private int saludInicial;

	private int nivel;

	private String tipoPrincipal;

	private String tipoSecundario;

	private int ataque;

	private int defensa;

	private int ataqueEspecial;

	private int defensaEspecial;

	private int velocidad;

	private int precision;

	private int evasion;

	private int ppActuales[];

	private Movimiento[] movimientos;

	public Pokemon(int saludInicial) {
		this.salud = saludInicial;
		this.saludInicial = saludInicial;

	}

	public void AumentoSalud(int aumento) {

	}

	public int getSalud() {
		return 0;
	}
	
	public int getSaludInicial() {
		return this.saludInicial;
	}

	public void Atacar(int indiceMovimiento, Pokemon objetivo) {

	}

	public String getTipoPrincipal() {
		return null;
	}

	public String getTipoSecundario() {
		return null;
	}

}
