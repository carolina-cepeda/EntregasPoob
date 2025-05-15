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

        Pocion pocion = new Pocion("Potion");
        juego.agregarItemAEntrenador(1, pocion);
        juego.agregarPokemonAEntrenador(1, crearPokemonBasico("Pikachu"));
        juego.agregarPokemonAEntrenador(2, crearPokemonBasico("Eevee"));

        juego.comenzarBatalla();

        EstadoJuego estado = juego.obtenerEstadoActual();

        boolean itemPresente = estado.items.stream()
                .anyMatch(item -> item.getNombre().equals("Potion"));

        assertTrue("La poción no está en la lista de items", itemPresente);
    }

    // TURNOS Y COMBATE
    @Test
    public void testAtaqueDisminuyeVidaOponente() throws ExceptionPOOBkemon {
        Juego juego = new Juego();
        juego.crearEntrenadores("Red", "Blue");

        Pokemon charmander = crearPokemonBasico("Charmander");
        Pokemon bulbasaur = crearPokemonBasico("Bulbasaur");

        juego.agregarPokemonAEntrenador(1, charmander);
        juego.agregarPokemonAEntrenador(2, bulbasaur);

        juego.comenzarBatalla();

        EstadoJuego estadoAntes = juego.obtenerEstadoActual();
        Pokemon oponenteAntes = estadoAntes.pokemonOponente;
        int vidaInicial = oponenteAntes.getSalud();

        juego.realizarAccion("atacar", 0);

        EstadoJuego estadoDespues = juego.obtenerEstadoActual();
        Pokemon oponenteDespues = estadoDespues.pokemonOponente;
        int vidaActual = oponenteDespues.getSalud();

        assertTrue("El ataque no redujo la vida del oponente", vidaActual < vidaInicial);
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

        assertTrue(estado.nombreJugador.equals("Red") || estado.nombreJugador.equals("Blue"));
        assertTrue(estado.nombreOponente.equals("Red") || estado.nombreOponente.equals("Blue"));
        assertTrue(estado.pokemonActivo == poke1 || estado.pokemonActivo == poke2);
        assertTrue(estado.pokemonOponente == poke1 || estado.pokemonOponente == poke2);
        assertNotEquals(estado.pokemonActivo, estado.pokemonOponente);

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

    // ADICIONALES
    @Test
    public void testCreacionPokemon() {
        Movimiento[] movimientos = {
                new Movimiento("Placaje", 40, 100, 35, 0, "Normal", null),
                new Movimiento("Rayo", 50, 95, 30, 0, "Eléctrico", null),
                new Movimiento(null, 0, 0, 0, 0, null, null),
                new Movimiento(null, 0, 0, 0, 0, null, null)
        };
        Pokemon pikachu = new Pokemon("Pikachu", 100, 25, "Eléctrico", null, 55, 40, 50, 50, 90, 100, 100, movimientos);

        assertEquals("Pikachu", pikachu.getNombre());
        assertEquals(100, pikachu.getSalud());
        assertEquals("Eléctrico", pikachu.getTipoPrincipal());
        assertEquals(2, pikachu.getMovimientos().size()); // Verifica que tenga 2 movimientos
    }

    @Test
    public void testCreacionItem() {
        Item pocion = new Pocion("Potion");

        assertEquals("Potion", pocion.getNombre());
        assertThrows(IllegalArgumentException.class, () -> new Pocion("InvalidPotion")); // Verifica que se lance una
                                                                                         // excepción para un nombre
                                                                                         // inválido
    }

    @Test
    public void testAgregarPokemonAEntrenador() throws ExceptionPOOBkemon {
        Entrenador entrenador = new Entrenador("Ash", "Rojo");
        Pokemon pikachu = new Pokemon("Pikachu", 100, 25, "Eléctrico", "abc", 55, 40, 50, 50, 90, 100, 0,
                new Movimiento[0]);

        entrenador.agregarPokemon(pikachu);
        assertEquals(1, entrenador.getPokemones().size());
        assertEquals(pikachu, entrenador.getPokemonActivo());

        // Agregar más Pokémon hasta el límite
        for (int i = 0; i < 3; i++) {
            entrenador.agregarPokemon(
                    new Pokemon("Pokemon" + i, 100, 25, "Normal", null, 50, 50, 50, 50, 50, 100, i, new Movimiento[0]));
        }
        assertEquals(6, entrenador.getPokemones().size()); // Debería tener 6 Pokémon

        // Intentar agregar un séptimo Pokémon
        Pokemon extraPokemon = new Pokemon("Extra", 100, 25, "Normal", null, 50, 50, 50, 50, 50, 100, 0,
                new Movimiento[0]);
        entrenador.agregarPokemon(extraPokemon); // No debería agregarlo
        assertEquals(6, entrenador.getPokemones().size()); // Debería seguir teniendo 6 Pokémon
    }

    // Tests para la clase Movimiento
    public static class MovimientoTest {
        @Test
        public void testCreacionMovimiento() {
            Movimiento movimiento = new Movimiento("Placaje", 40, 100, 35, 0, "Normal", null);
            assertEquals("Placaje", movimiento.getNombre());
            assertEquals(40, movimiento.getPotencia());
            assertEquals("Normal", movimiento.getTipo());
        }
    }

}
