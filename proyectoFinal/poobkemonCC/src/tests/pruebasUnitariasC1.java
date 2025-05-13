package tests;

import dominio.*;
import presentacion.EstadoJuego;

import org.junit.Test;
import static org.junit.Assert.*;

public class pruebasUnitariasC1 {

    // CREACION Y CONFIGURACION
    @Test
    public void testJuegoPuedeCrearEntrenadores() throws ExceptionPOOBkemon {
        Juego juego = new Juego();
        juego.crearEntrenadores("Ash", "Gary");
        juego.agregarPokemonAEntrenador(1, crearPokemonBasico("Pikachu"));
        juego.agregarPokemonAEntrenador(2, crearPokemonBasico("Eevee"));

        assertNotNull(juego);
    }

    @Test
    public void testJuegoPermiteAgregarItems() throws ExceptionPOOBkemon {
        Juego juego = new Juego();
        juego.crearEntrenadores("Ash", "Gary");
        juego.agregarItemAEntrenador(1, new Pocion("Potion"));
        juego.agregarPokemonAEntrenador(1, crearPokemonBasico("Pikachu"));
        juego.agregarPokemonAEntrenador(2, crearPokemonBasico("Eevee"));

        juego.comenzarBatalla();
        EstadoJuego estado = juego.obtenerEstadoActual();

        assertEquals(1, estado.items.size());
        assertEquals("Potion", estado.items.get(0).getNombre());
    }

    // TURNOS Y COMBATE
    @Test
    public void testAtaqueDisminuyeVidaOponente() throws ExceptionPOOBkemon {
        Juego juego = new Juego();
        juego.crearEntrenadores("Red", "Blue");

        Pokemon atacante = crearPokemonBasico("Charmander");
        Pokemon defensor = crearPokemonBasico("Bulbasaur");

        juego.agregarPokemonAEntrenador(1, atacante);
        juego.agregarPokemonAEntrenador(2, defensor);

        juego.comenzarBatalla();

        int vidaInicial = defensor.getSalud();
        juego.realizarAccion("atacar", 0); // turno de Charmander

        EstadoJuego estado = juego.obtenerEstadoActual();
        int vidaActual = estado.pokemonOponente.getSalud();

        assertTrue(vidaActual < vidaInicial);
    }

    @Test
    public void testCambioDeTurnoFuncionando() throws ExceptionPOOBkemon {
        Juego juego = new Juego();
        juego.crearEntrenadores("Ash", "Misty");

        juego.agregarPokemonAEntrenador(1, crearPokemonBasico("Pikachu"));
        juego.agregarPokemonAEntrenador(2, crearPokemonBasico("Staryu"));

        juego.comenzarBatalla();

        String turnoAntes = juego.obtenerEstadoActual().nombreJugador;
        juego.realizarAccion("atacar", 0); // turno 1
        String turnoDespues = juego.obtenerEstadoActual().nombreJugador;

        assertNotEquals(turnoAntes, turnoDespues);
    }

    // EXCEPCIONES
    @Test(expected = ExceptionPOOBkemon.class)
    public void testAccionInvalidaLanzaExcepcion() throws ExceptionPOOBkemon {
        Juego juego = new Juego();
        juego.crearEntrenadores("Ash", "Gary");
        juego.agregarPokemonAEntrenador(1, crearPokemonBasico("Pikachu"));
        juego.agregarPokemonAEntrenador(2, crearPokemonBasico("Eevee"));
        juego.comenzarBatalla();

        juego.realizarAccion("volar", null); // acción no válida
    }

    @Test(expected = ExceptionPOOBkemon.class)
    public void testErrorAlAgregarPokemonJugadorInexistente() throws ExceptionPOOBkemon {
        Juego juego = new Juego();
        juego.crearEntrenadores("Ash", "Gary");

        // Jugador 3 no existe
        juego.agregarPokemonAEntrenador(3, crearPokemonBasico("MissingNo"));
    }

    // ESTADO DE JUEGO
    @Test
    public void testEstadoJuegoContieneDatosEsperados() throws ExceptionPOOBkemon {
        Juego juego = new Juego();
        juego.crearEntrenadores("Red", "Blue");
        Pokemon poke1 = crearPokemonBasico("Charmander");
        Pokemon poke2 = crearPokemonBasico("Squirtle");

        juego.agregarPokemonAEntrenador(1, poke1);
        juego.agregarPokemonAEntrenador(2, poke2);
        juego.comenzarBatalla();

        EstadoJuego estado = juego.obtenerEstadoActual();

        assertEquals("Red", estado.nombreJugador);
        assertEquals("Blue", estado.nombreOponente);
        assertEquals(poke1, estado.pokemonActivo);
        assertEquals(poke2, estado.pokemonOponente);
    }

    // CREACIÓN DE POKEMONES
    private Pokemon crearPokemonBasico(String nombre) {
        Movimiento[] movimientos = {
            new Movimiento("Placaje", 40, 100, 35, 0, "Normal", null),
            new Movimiento("Nada", 3, 100, 40, 0, "Normal", null),
            new Movimiento("Nada2", 2, 100, 40, 0, "Normal", null),
            new Movimiento("Nada3", 2, 100, 40, 0, "Normal", null)
        };
        return new Pokemon(nombre, 200, 30, "Normal", null, 150, 100, 120, 110, 160, 100, 100, movimientos);
    }

}
