package dominio;

import java.util.Timer;
import java.util.TimerTask;

public class ControladorTurno {

    private Batalla batalla;
    private Timer temporizador;
    private final int LIMITE_TIEMPO = 20000; // 20 segundos en milisegundos

    public ControladorTurno(Batalla batalla) {
        this.batalla = batalla;
    }

    public void iniciar() {
        detener(); // Por si había un tiempo anterior corriendo
        temporizador = new Timer();
        temporizador.schedule(new TimerTask() {
            @Override
            public void run() {
                penalizarPorInactividad();
            }
        }, LIMITE_TIEMPO);
    }

    public void detener() {
        if (temporizador != null) {
            temporizador.cancel();
        }
    }

    private void penalizarPorInactividad() {
        System.out.println("¡" + batalla.getTurnoActual().getNombre() + " no jugó a tiempo!"); //plantear turnos en esta clase
        batalla.getTurnoActual().getPokemonActivo().reducirPP(1);
        batalla.comenzarTurno("pasar", null); 
    }
}

