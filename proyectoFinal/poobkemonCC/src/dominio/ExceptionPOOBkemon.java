package dominio;
/**
 * clase para el manejo de excepciones en el juego
 */
public class ExceptionPOOBkemon extends Exception{
	
	public static final String MESSAGE_ERROR = "error";

	/**
	 * constructor
	 * @param mensaje
	 */
	public ExceptionPOOBkemon(String mensaje) {
		super(mensaje);
	}
}
