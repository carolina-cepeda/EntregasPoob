package dominio;

import java.util.List;
import java.util.Map.Entry;

/**
 * clase entrenador maquina que decide sus acciones en base a un requisito
 */
public abstract class EntrenadorMaquina extends Entrenador {
    public EntrenadorMaquina(String nombre, String color) {
        super(nombre, color);
    }

    public abstract Entry<String, Object> decidirAccion(Pokemon pokemonEnemigo);

    /**
     * metodo para realizar una accion de manera automatica
     * @param juego
     * @throws ExceptionPOOBkemon
     */
    public void realizarAccionAutomatica(Juego juego) throws ExceptionPOOBkemon {
    System.out.println("Intentando realizar acción automática para: " + getNombre());
    Pokemon pokemonEnemigo = juego.obtenerEstadoActual().pokemonOponente ;
    System.out.println("Pokémon enemigo: " + pokemonEnemigo.getNombre());
    
    // Mostrar información del Pokémon activo
    Pokemon activo = getPokemonActivo();
    System.out.println("Mi Pokémon activo: " + activo.getNombre() + " (Salud: " + activo.getSalud() + ")");
    
    // Mostrar movimientos disponibles
    System.out.println("Movimientos disponibles:");
    List<Movimiento> movimientos = activo.getMovimientos();
    for (int i = 0; i < movimientos.size(); i++) {
        Movimiento m = movimientos.get(i);
        System.out.println("  " + i + ": " + m.getNombre() + " (PP: " + m.getPP() + ", Potencia: " + m.getPotencia() + ")");
    }
    
    // Obtener la decisión
    Entry<String, Object> decision = decidirAccion(pokemonEnemigo);
    System.out.println("Decisión tomada: " + decision.getKey() + " | " + decision.getValue());
    
    // Ejecutar la acción
    juego.realizarAccion(decision.getKey(), decision.getValue());
}
}

