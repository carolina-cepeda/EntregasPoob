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
        juego.realizarAccion(accion.getKey(), accion.getValue());
    }
}

