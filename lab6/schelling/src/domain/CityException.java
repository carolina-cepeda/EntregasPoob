package domain;

public class CityException extends Exception {
    public static final String ERROR_EXPORTAR = "función exportar en construcción. Archivo :";
    public static final String ERROR_IMPORTAR = "función importar en construcción. Archivo :";
    public static final String ERROR_GUARDAR = "función guardar en construcción. Archivo :";
    public static final String ERROR_ABRIR = " función abrir en constuccion.Archivo: ";
    public CityException(String message) {
        super(message);
    }
}
