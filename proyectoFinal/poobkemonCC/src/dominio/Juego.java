package dominio;

public class Juego {

    private ModoJuego modoJuego;
    private Batalla batalla;

    public Juego() {
        // Constructor vacío, espera que se configure el modo
    }

    /**
     * Define el modo de juego y configura el juego (entrenadores, pokémones, ítems, etc.)
     */
    public void seleccionarModoJuego(ModoJuego modo) {
        this.modoJuego = modo;
        modoJuego.configurarJuego(this);
    }

    /**
     * Inicia la batalla entre los entrenadores configurados por el modo de juego.
     */
    public void empezarBatalla(Entrenador entrenador1, Entrenador entrenador2) {
        this.batalla = new Batalla(entrenador1, entrenador2);
        batalla.iniciarBatalla();
    }

    /**
     * Ejecuta la acción de un turno desde la interfaz o IA
     * @param accion La acción a ejecutar: atacar, cambiar, usaritem, huir, pasar
     * @param dato Parámetro adicional necesario según la acción
     */
    public void comenzarTurno(String accion, Object dato) {
        if (batalla != null) {
            batalla.comenzarTurno(accion, dato);
        }
    }

    /**
     * Retorna el entrenador cuyo turno está activo
     */
    public Entrenador getTurnoActual() {
        return batalla != null ? batalla.getTurnoActual() : null;
    }

    /**
     * Verifica si hay una batalla activa
     */
    public boolean hayBatallaActiva() {
        return batalla != null;
    }
}

