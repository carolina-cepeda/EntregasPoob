package dominio;

import java.util.Timer;
import java.util.TimerTask;

public class ControladorTurno {

    private Batalla batalla;
    private Timer temporizador;
    private final int LIMITE_TIEMPO = 20000; // 20 segundos en milisegundos

    /**
     * clase para controlar los turnos 
     * @param batalla
     */
    public ControladorTurno(Batalla batalla) {
        this.batalla = batalla;
    }

    /**
     * metodo para iniciar el turno en la batalla
     */
    public void iniciar() {
        detener(); // Por si había un tiempo anterior corriendo
        temporizador = new Timer();
        temporizador.schedule(new TimerTask() {

        @Override
        public void run() {
            try {
                penalizarPorInactividad();
            } catch (ExceptionPOOBkemon e) {
                System.err.println("penalidad por inactividad: " + e.getMessage());
            }
}


        }, LIMITE_TIEMPO);
    }

    /**
     *metodo para detener el temporizador
     */
    public void detener() {
        if (temporizador != null) {
            temporizador.cancel();
        }
    }

    /**
     * metodo para penalizar por inactividad
     * @throws ExceptionPOOBkemon
     */
    private void penalizarPorInactividad() throws ExceptionPOOBkemon {
        batalla.getTurnoActual().getPokemonActivo().reducirPP(1);
        batalla.comenzarTurno("pasar", null); 
        throw new ExceptionPOOBkemon("¡" + batalla.getTurnoActual().getNombre() + " no jugó a tiempo!");
    }
}

