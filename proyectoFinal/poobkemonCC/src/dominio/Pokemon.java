package dominio;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * clase para pokemon
 */
public class Pokemon implements Serializable{

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

	/**
	 * constructor de la clase pokemon 
	 * 
	 */
	public Pokemon(String nombre, int salud, int nivel, String tipoPrincipal, String tipoSecundario, int ataque, int defensa, int ataqueEspecial, int defensaEspecial,
			int velocidad, int precision, int evasion, Movimiento[] nuevosMovimientos) {

		 this.nombre = nombre;
		 this.salud = salud;
		 this.saludInicial = salud;
		 this.nivel = nivel;
		 this.tipoPrincipal = tipoPrincipal;
		 this.tipoSecundario = tipoSecundario;
		 this.ataque = ataque;
		 this.defensa = defensa;
		 this.ataqueEspecial = ataqueEspecial;
		 this.defensaEspecial = defensaEspecial;
		 this.velocidad = velocidad;
		 this.precision = precision;
		 this.evasion = evasion;
		
		 this.movimientos = new Movimiento[4];
		 this.ppActuales = new int[4];
		
		this.movimientos = new Movimiento[4];
		this.ppActuales = new int[4];
		for (int i = 0; i < 4; i++) {
			this.movimientos[i] = nuevosMovimientos[i];
			this.ppActuales[i] = nuevosMovimientos[i].getPP();
		}
}

	
	/**
	 * metodo para aumentar la salud de un pokemon
	 * se hace uso de este metodo con un item.
	 * @param aumento : aumento de la salud 
	 */
	public void aumentoSalud(int aumento) {
		this.salud += aumento;

	}

	/**
	 * metodo para consultar la salud del pokemon
	 * 
	 * @return int
	 */
	public int getSalud() {
		return this.salud;
	}
	
	/**
	 * metodo para consultar la salud del pokemon inicial.
	 * @return int
	 */
	public int getSaludInicial() {
		return this.saludInicial;
	}

	/**
	 * metodo para atacar a otro pokemon
	 * @param indiceMovimiento 
	 * @param objetivo : pokemon objetivo
	 */
	public void Atacar(int indiceMovimiento, Pokemon objetivo) {
		if (indiceMovimiento < 0 || indiceMovimiento >= movimientos.length) return;
		Movimiento mov = movimientos[indiceMovimiento];
		if (ppActuales[indiceMovimiento] <= 0) return;

		String tipoAtaque = mov.getTipo();
		double multiplicador1 = TipoEfectividad.getMultiplicador(tipoAtaque.toUpperCase(), objetivo.getTipoPrincipal().toUpperCase());
	
		double efectividad = multiplicador1;
		int dañoBase = mov.getPotencia(); 
		int daño = (int)(dañoBase * efectividad);
	
		System.out.println("¡El ataque fue " + (efectividad > 1 ? "super efectivo!" : efectividad < 1 ? "poco efectivo..." : "normal.") + " x" + efectividad);
		objetivo.recibirDaño(Math.max(0, daño));
	}
	
	

	public void recibirDaño(int daño) {
		this.salud = Math.max(0, this.salud - daño);
		if (this.salud == 0) {
			System.out.println(nombre + " ha sido derrotado.");
		} else {
			System.out.println(nombre + " ha recibido " + daño + " puntos de daño. Salud restante: " + this.salud);
		}
	}
	
	/**
	 * metodo para consultar el tipo principal del pokemon
	 * @return String
	 */
	public String getTipoPrincipal() {
		return tipoPrincipal;
	}
	
	/**
	 * metodo para consultar el tipo secundario del pokemon
	 * @return String
	 */
	public String getTipoSecundario() {
		return tipoSecundario;
	}

	public int getAtaque() {
		return ataque;
	}

	public ArrayList<Movimiento> getMovimientos() {
		ArrayList<Movimiento> movimientosDisponibles = new ArrayList<>();
		for (int i = 0; i < movimientos.length; i++) {
			movimientosDisponibles.add(movimientos[i]);
		}
		return movimientosDisponibles;
	}

	public void reducirPP(int cantidad) {
		for (int i = 0; i < movimientos.length; i++) {
			ppActuales[i] = Math.max(0, ppActuales[i] - cantidad); // toca cambiarlo a solo movimientos especiales 
		}
	}

	public String getNombre(){
		return this.nombre;
	}

	public int[] getPP(){
		return this.ppActuales ;
	}

}
