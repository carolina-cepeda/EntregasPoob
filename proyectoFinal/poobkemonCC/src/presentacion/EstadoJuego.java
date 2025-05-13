package presentacion;

import dominio.*;

import java.util.List;

public class EstadoJuego {
    public String nombreJugador;
    public Pokemon pokemonActivo;
    public List<Pokemon> equipo;
    public List<Item> items;
    public String nombreOponente;
    public Pokemon pokemonOponente;

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

