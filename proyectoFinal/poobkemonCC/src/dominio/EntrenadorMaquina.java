package dominio;

import java.util.Map.Entry;

public abstract class EntrenadorMaquina extends Entrenador {
    public EntrenadorMaquina(String nombre, String color) {
        super(nombre, color);
    }

    public abstract Entry<String, Object> decidirAccion(Pokemon pokemonEnemigo);

    public void realizarAccionAutomatica(Juego juego) throws ExceptionPOOBkemon {
        Pokemon enemigo = juego.obtenerEstadoActual().pokemonOponente;
        Entry<String, Object> accion = decidirAccion(enemigo);

        try {
            System.out.println("Intentando realizar acción automática: " + accion.getKey());
            juego.realizarAccion(accion.getKey(), accion.getValue());
        } catch (ExceptionPOOBkemon e) {
            System.out.println("Acción automática inválida: " + e.getMessage());
            // Si la acción no es válida, pasar el turno
            juego.realizarAccion("pasar", null);
        }
    }
}

