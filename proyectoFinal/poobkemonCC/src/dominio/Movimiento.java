package dominio;

public class Movimiento {

    private String nombre;
    private int potencia;
    private int precision;
    private int pp;
    private int prioridad;
    private String tipo;
    private String efectoSecundario;

    public Movimiento(String nombre, int potencia, int precision, int pp, int prioridad, String tipo, String efectoSecundario) {
        this.nombre = nombre;
        this.potencia = potencia;
        this.precision = precision;
        this.pp = pp;
        this.prioridad = prioridad;
        this.tipo = tipo;
        this.efectoSecundario = efectoSecundario;
    }

    public int getPP() {
        return pp;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPotencia() {
        return potencia;
    }

    public int getPrecision() {
        return precision;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEfectoSecundario() {
        return efectoSecundario;
    }

    public Movimiento AtaqueObjetivo(Pokemon objetivo) {
        //como la hago?
        return this;
    }
}
