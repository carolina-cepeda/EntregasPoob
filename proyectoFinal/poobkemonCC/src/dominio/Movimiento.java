package dominio;


public abstract class Movimiento {

	private String nombre;

	private int potencia;

	private int precision;

	private int pp;

	private int ppActual;
	
	private int prioridad;

	private String tipo;

	private String efectoSecuntario;
	
	public Movimiento(String nombre, int potencia, int precision, int pp, int prioridad, String tipo, String efectoSecundario) {
	    this.nombre = nombre;
	    this.potencia = potencia;
	    this.precision = precision;
	    this.pp = pp;
	    this.ppActual = pp; 
	    this.prioridad = prioridad;
	    this.tipo = tipo;
	    this.efectoSecuntario = efectoSecundario;
	}


	public Movimiento AtaqueObjetivo(Pokemon pokemonObjetivo) {
		return null;
	}
	
	public int getPP() {
		return this.pp;
	}

}
