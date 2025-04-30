package dominio;
/**
 * clase del movimiento FISICO
 */
class MovimientoNormal extends Movimiento { 
	
	/**
	 * constructor del movimiento fisico
	 * @param nombre
	 * @param potencia
	 * @param precision
	 * @param pp
	 * @param prioridad
	 * @param tipo
	 * @param efectoSecundario
	 */
	public MovimientoNormal(String nombre, int potencia, int precision, int pp, int prioridad, String tipo, String efectoSecundario) {
		super(nombre,potencia, precision, pp, prioridad, tipo,efectoSecundario);
	}
}