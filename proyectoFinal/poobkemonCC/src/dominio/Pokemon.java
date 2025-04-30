package dominio;


/**
 * hacer abstracta ???
 */
public class Pokemon {

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
            int velocidad, int precision, int evasion, Movimiento[] movimientosAsignados) {

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
		
		 for (int i = 0; i < 4; i++) {
		     this.movimientos[i] = movimientosAsignados[i];
		     this.ppActuales[i] = movimientosAsignados[i].getPP(); // asigna el PP máximo como valor inicial
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

}
