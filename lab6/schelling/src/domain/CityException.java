/**
 * clase para el manejo de excepciones 
 * @author: carolina cepeda
 */

 package domain;
public class CityException extends Exception {
    public static final String ERROR_ABRIR = "Error al abrir el archivo: ";
    public static final String ERROR_GUARDAR = "Error al guardar el archivo: ";
    public static final String ERROR_EXPORTAR = "Error al exportar la ciudad: ";
    public static final String ERROR_IMPORTAR = "Error al importar la ciudad: ";
    public static final String FORMATO_INVALIDO = "Formato inválido en línea ";
    public static final String COORDENADAS_INVALIDAS = "Coordenadas inválidas en línea ";
    public static final String TIPO_DESCONOCIDO = "Tipo de ítem desconocido en línea ";

    public CityException(String message) {
        super(message);
    }
}
