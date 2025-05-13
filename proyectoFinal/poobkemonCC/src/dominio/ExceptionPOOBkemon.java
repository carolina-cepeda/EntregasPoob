package dominio;
/**
 * clase para el manejo de excepciones en el juego
 */
public class ExceptionPOOBkemon extends Exception{
	
	public static final String MESSAGE_ERROR = "error";
	public static final String GANADOR= "Ha ganado la batalla";
	public static final String accionInvalida = "la accion es invalida";

	
	/**
	 * constructor
	 * @param mensaje
	 */
	public ExceptionPOOBkemon(String mensaje) {
		super(mensaje);
	}
}
