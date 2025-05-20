package presentacion;

import dominio.*;
import java.io.Serializable;
import java.util.List;
/**
 * clase para manejar el estado del juego, comunica a la presentación con
 * aplicación 
 */
public class EstadoJuego implements Serializable {
    public String nombreJugador;
    public Pokemon pokemonActivo;
    public List<Pokemon> equipo;
    public List<Item> items;
    public String nombreOponente;
    public Pokemon pokemonOponente;

    /**
     * constructor
     * @param nombreJugador
     * @param activo
     * @param equipo
     * @param items
     * @param nombreOponente
     * @param pokemonOponente
     */
    public EstadoJuego(String nombreJugador, Pokemon activo, List<Pokemon> equipo,
                     List<Item> items, String nombreOponente, Pokemon pokemonOponente) {
        this.nombreJugador = nombreJugador;
        this.pokemonActivo = activo;
        this.equipo = equipo;
        this.items = items;
        this.nombreOponente = nombreOponente;
        this.pokemonOponente = pokemonOponente;
    }
}