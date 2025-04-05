package domain;

/**
 * Clases de excepciones del plan 15
 * 
 * @author Carolina Cepeda
 * @version 27/03
 */
public class Plan15Exception extends Exception {

    public static final String IMPOSSIBLE = "imposible";
    public static final String CREDITS_UNKNOWN = "creditos no conocidos";
    public static final String CREDITS_ERROR = " error en los creditos";
    public static final String IN_PERSON_UNKNOWN = "horas persona no reconocida";
    public static final String IN_PERSON_ERROR = "error de horas persona";
    public static final String NAME_ERROR = "error en el nombre";
    public static final String NAME_ALREADY_EXISTS = "El nombre ingresado ya existe en el sistema";
    public Plan15Exception(String message) {
        super(message);
    }

}
