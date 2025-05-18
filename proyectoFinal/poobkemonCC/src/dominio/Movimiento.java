package dominio;
/**
 * clase que crea los movimientos usados por pokemones
 */
public class Movimiento {

    private String nombre;
    private int potencia;
    private int precision;
    private int pp;
    private int prioridad;
    private String tipo;
    private String efectoSecundario;
/**
 * constructor
 * @param nombre
 * @param potencia
 * @param precision
 * @param pp
 * @param prioridad
 * @param tipo
 * @param efectoSecundario
 */
    public Movimiento(String nombre, int potencia, int precision, int pp, int prioridad, String tipo, String efectoSecundario) {
        this.nombre = nombre;
        this.potencia = potencia;
        this.precision = precision;
        this.pp = pp;
        this.prioridad = prioridad;
        this.tipo = tipo;
        this.efectoSecundario = efectoSecundario;
    }
/**
 * getter del PP
 * @return
 */
    public int getPP() {
        return pp;
    }
/**
 * metodo para obtener el nombre
 * @return
 */
    public String getNombre() {
        return nombre;
    }
/**
 * metodo para obtener la potencia
 * @return
 */
    public int getPotencia() {
        return potencia;
    }
/**
 * metodo para obtener la precision 
 * @return
 */
    public int getPrecision() {
        return precision;
    }

    /**
 * metodo para obtener la prioridad
 * @return
 */
    public int getPrioridad() {
        return prioridad;
    }
/**
 * metodo para obtener el tipo
 * @return
 */
    public String getTipo() {
        return tipo;
    }
/**
 * metodo para obtener el efecto secundario
 * @return
 */
    public String getEfectoSecundario() {
        return efectoSecundario;
    }

    
}
