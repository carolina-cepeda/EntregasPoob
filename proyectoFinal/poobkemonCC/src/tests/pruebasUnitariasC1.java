package tests;

import dominio.*;
import presentacion.EstadoJuego;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

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


    // TURNOS Y COMBATE
 
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


    //  ENTRENADOR

    private Movimiento crearMovimiento(String nombre, String tipo, int potencia, int pp) {
        return new Movimiento(nombre, potencia, 100, pp, 0, tipo, null);
    }

    private Movimiento[] movimientosBase() {
        return new Movimiento[]{
            crearMovimiento("Impactrueno", "ELECTRICO", 40, 30),
            crearMovimiento("Placaje", "NORMAL", 35, 35),
            crearMovimiento("Gruñido", "NORMAL", 0, 40),
            crearMovimiento("Ataque Rápido", "NORMAL", 40, 30)
        };
    }

    private Pokemon crearPokemon(String nombre) {
        return new Pokemon(nombre, 100, 10, "ELECTRICO", null, 55, 40, 50, 50, 90, 100, 100, movimientosBase());
    }

    @Test
    public void testAgregarPokemon() {
        Entrenador e = new Entrenador("Ash", "Rojo");
        Pokemon pikachu = crearPokemon("Pikachu");
        e.agregarPokemon(pikachu);
        assertEquals(1, e.getPokemones().size());
        assertEquals(pikachu, e.getPokemonActivo());
    }

    @Test
    public void testAgregar6Pokemon() {
        Entrenador e = new Entrenador("Ash", "Rojo");
        for (int i = 0; i < 7; i++) {
            e.agregarPokemon(crearPokemon("P" + i));
        }
        assertEquals(6, e.getPokemones().size());
    }

    @Test
    public void testCambiarPokemonExitoso() {
        Entrenador e = new Entrenador("Ash", "Rojo");
        Pokemon p1 = crearPokemon("Pikachu");
        Pokemon p2 = crearPokemon("Charmander");
        e.agregarPokemon(p1);
        e.agregarPokemon(p2);
        e.cambiarPokemon(p2);
        assertEquals(p2, e.getPokemonActivo());
    }

    @Test
    public void testCambiarPokemonFallidoPorSalud() {
        Entrenador e = new Entrenador("Ash", "Rojo");
        Pokemon p1 = crearPokemon("Pikachu");
        Pokemon p2 = crearPokemon("Charmander");
        p2.recibirDaño(100); // salud a 0
        e.agregarPokemon(p1);
        e.agregarPokemon(p2);
        e.cambiarPokemon(p2);
        assertEquals(p1, e.getPokemonActivo()); // no cambia
    }

    @Test
    public void testSeleccionarMovimientoConPP() {
        Entrenador e = new Entrenador("Ash", "Rojo");
        Pokemon atacante = crearPokemon("Pikachu");
        Pokemon objetivo = crearPokemon("Eevee");
        e.agregarPokemon(atacante);
        int saludAntes = objetivo.getSalud();
        e.seleccionarMovimiento(0, objetivo);
        assertTrue(objetivo.getSalud() < saludAntes);
    }

    @Test
    public void testSeleccionarMovimientoSinPP() {
        Entrenador e = new Entrenador("Ash", "Rojo");

        Movimiento sinPP = new Movimiento("Impactrueno", 40, 100, 0, 0, "ELECTRICO", null);
        Movimiento[] sinPPMovimientos = new Movimiento[]{sinPP, sinPP, sinPP, sinPP};

        Pokemon atacante = new Pokemon("Pikachu", 100, 10, "ELECTRICO", null, 55, 40, 50, 50, 90, 100, 100, sinPPMovimientos);
        Pokemon objetivo = crearPokemon("Eevee");

        e.agregarPokemon(atacante);
        int saludAntes = objetivo.getSalud();
        e.seleccionarMovimiento(0, objetivo);
        assertEquals(saludAntes, objetivo.getSalud()); // no hubo ataque
    }

    @Test
    public void testUsarItemCorrectamente() {
        Entrenador e = new Entrenador("Ash", "Rojo");
        Pokemon p = crearPokemon("Bulbasaur");
        p.recibirDaño(30);
        Pocion pocion = new Pocion("Potion");
        e.agregarItem(pocion);
        e.usarItem(pocion, p);
        assertTrue(p.getSalud() > 70); // salud era 70, ahora mínimo 90
        assertFalse(e.getItems().contains(pocion)); // item usado
    }

    @Test
    public void testUsarItemNoDisponible() {
        Entrenador e = new Entrenador("Ash", "Rojo");
        Pokemon p = crearPokemon("Bulbasaur");
        p.recibirDaño(30);
        Pocion pocion = new Pocion("Potion");
        e.usarItem(pocion, p); // sin agregarla
        assertTrue(p.getSalud() < p.getSaludInicial());
    }

    @Test
    public void testSeleccionarPokemonesAuto() {
        Entrenador e = new Entrenador("Ash", "Rojo");
        List<Pokemon> disponibles = Arrays.asList(
                crearPokemon("Pikachu"),
                crearPokemon("Bulbasaur"),
                crearPokemon("Charmander"),
                crearPokemon("Squirtle"),
                crearPokemon("Eevee")
        );
        e.seleccionarPokemonesAuto(disponibles);
        assertTrue(e.getPokemones().size() <= 6);
    }

    @Test
    public void testSeleccionarItemsAuto() {
        Entrenador e = new Entrenador("Ash", "Rojo");
        List<Item> itemsBase = Arrays.asList(
                new Pocion("Potion"),
                new Pocion("Potion"),
                new Pocion("SuperPotion"),
                new Pocion("Revive"),
                new Pocion("Potion")
        );
        e.seleccionarItemsAuto(itemsBase);
        int potions = 0, superPotions = 0, revives = 0;
        for (Item i : e.getItems()) {
            if (i.getNombre().equals("Potion")) potions++;
            else if (i.getNombre().equals("SuperPotion")) superPotions++;
            else if (i.getNombre().equals("Revive")) revives++;
        }
        assertTrue(potions <= 2);
        assertTrue(superPotions <= 1);
        assertTrue(revives <= 1);
    }

// POKEMONES



    private Pokemon crearPokemon(String nombre, String tipo) {
        return new Pokemon(nombre, 100, 10, tipo, null, 55, 40, 50, 50, 90, 100, 100, movimientosBase());
    }

    @Test
    public void testGetSalud() {
        Pokemon p = crearPokemon("Pikachu", "ELECTRICO");
        assertEquals(100, p.getSalud());
    }

    @Test
    public void testAumentoSalud() {
        Pokemon p = crearPokemon("Bulbasaur", "PLANTA");
        p.recibirDaño(30);
        int saludAntes = p.getSalud();
        p.aumentoSalud(20);
        assertEquals(saludAntes + 20, p.getSalud());
    }

    @Test
    public void testRecibirDaño() {
        Pokemon p = crearPokemon("Charmander", "FUEGO");
        p.recibirDaño(50);
        assertEquals(50, p.getSalud());
    }

    @Test
    public void testRecibirDañoMayorALaSalud() {
        Pokemon p = crearPokemon("Squirtle", "AGUA");
        p.recibirDaño(150);
        assertEquals(0, p.getSalud());
    }

    @Test
    public void testGetTipoPrincipalYSecundario() {
        Pokemon p = new Pokemon("Eevee", 100, 10, "NORMAL", "NULO", 55, 50, 50, 50, 60, 100, 100, movimientosBase());
        assertEquals("NORMAL", p.getTipoPrincipal());
        assertEquals("NULO", p.getTipoSecundario());
    }

    @Test
    public void testReducirPP() {
        Pokemon p = crearPokemon("Pikachu", "ELECTRICO");
        int[] ppAntes = p.getPP().clone();
        p.reducirPP(10);
        for (int i = 0; i < ppAntes.length; i++) {
            assertEquals(Math.max(0, ppAntes[i] - 10), p.getPP()[i]);
        }
    }

    @Test
    public void testMovimientosInicialesYPP() {
        Pokemon p = crearPokemon("Pikachu", "ELECTRICO");
        assertEquals(4, p.getMovimientos().size());
        assertEquals(30, p.getPP()[0]); // Impactrueno
        assertEquals(35, p.getPP()[1]); // Placaje
    }

    @Test
    public void testAtacarReduceSaludDelObjetivo() {
        Pokemon atacante = crearPokemon("Pikachu", "ELECTRICO");
        Pokemon objetivo = crearPokemon("Squirtle", "AGUA");

        int saludInicial = objetivo.getSalud();
        atacante.Atacar(0, objetivo); // Impactrueno

        assertTrue(objetivo.getSalud() < saludInicial);
    }

    @Test
    public void testAtacarConMovimientoSinPPNoHaceDaño() {
        Movimiento sinPP = new Movimiento("Impactrueno", 40, 100, 0, 0, "ELECTRICO", null);
        Movimiento[] sinPPMovimientos = new Movimiento[]{sinPP, sinPP, sinPP, sinPP};

        Pokemon atacante = new Pokemon("Pikachu", 100, 10, "ELECTRICO", null, 55, 40, 50, 50, 90, 100, 100, sinPPMovimientos);
        Pokemon objetivo = crearPokemon("Bulbasaur", "PLANTA");

        int saludInicial = objetivo.getSalud();
        atacante.Atacar(0, objetivo);
        assertEquals(saludInicial, objetivo.getSalud());
    }

    @Test
    public void testAtacarConIndiceInvalidoNoHaceNada() {
        Pokemon atacante = crearPokemon("Pikachu", "ELECTRICO");
        Pokemon objetivo = crearPokemon("Bulbasaur", "PLANTA");
        int saludInicial = objetivo.getSalud();
        atacante.Atacar(-1, objetivo);
        atacante.Atacar(5, objetivo); // fuera de rango
        assertEquals(saludInicial, objetivo.getSalud());
    }

    @Test
    public void testGetNombre() {
        Pokemon p = crearPokemon("Pikachu", "ELECTRICO");
        assertEquals("Pikachu", p.getNombre());
    }

    @Test
    public void testGetAtaque() {
        Pokemon p = crearPokemon("Pikachu", "ELECTRICO");
        assertEquals(55, p.getAtaque());
    }

    @Test
    public void testGetSaludInicial() {
        Pokemon p = crearPokemon("Pikachu", "ELECTRICO");
        assertEquals(100, p.getSaludInicial());
    }




//MOVIMIENTOS
        @Test
        public void testConstructorYGetters() {
            Movimiento mov = new Movimiento("Llama", 90, 100, 15, 0, "FUEGO", "Quemar");

            assertEquals("Llama", mov.getNombre());
            assertEquals(90, mov.getPotencia());
            assertEquals(100, mov.getPrecision());
            assertEquals(15, mov.getPP());
            assertEquals(0, mov.getPrioridad());
            assertEquals("FUEGO", mov.getTipo());
            assertEquals("Quemar", mov.getEfectoSecundario());
        }

        @Test
        public void testMovimientoSinEfectoSecundario() {
            Movimiento mov = new Movimiento("Placaje", 50, 95, 35, 0, "NORMAL", null);

            assertEquals("Placaje", mov.getNombre());
            assertNull(mov.getEfectoSecundario());
        }

// ITEMS
    @Test
    public void testGetNombreItem() {
        Item pocion = new Pocion("Potion");
        assertEquals("Potion", pocion.getNombre());
    }

    @Test
    public void testUsarItemAumentaSalud() {
        Movimiento[] movs = new Movimiento[]{
            new Movimiento("Placaje", 50, 100, 35, 0, "NORMAL", null),
            new Movimiento("Gruñido", 0, 100, 40, 0, "NORMAL", null),
            new Movimiento("Ataque Rápido", 40, 100, 30, 1, "NORMAL", null),
            new Movimiento("Impactrueno", 40, 100, 30, 0, "ELECTRICO", null)
        };

        Pokemon pikachu = new Pokemon("Pikachu", 100, 10, "ELECTRICO", null, 55, 40, 50, 50, 90, 100, 100, movs);
        pikachu.recibirDaño(50); // salud queda en 50

        Item pocion = new Pocion("Potion");
        pocion.usar(pikachu); // debe quedar en 70

        assertEquals(70, pikachu.getSalud());
    }
    

    //MODO NORMAL
    @Test
    public void testModoNormalJugadorVsMaquina() throws ExceptionPOOBkemon {
        Juego juego = new Juego();
        Normal modo = new Normal();
        modo.setTipoJuego(2); // PvM
        modo.configurarJuego(juego);


        modo.prepararBatalla("Ash", "CPU", 1, 0); // tipoIA1 = 1 (defensivo), tipoIA2 se ignora en PvM

        for (int i = 0; i < 3; i++) {
            juego.agregarPokemonAEntrenador(1, crearPokemon("P" + i));
        }
        juego.agregarItemAEntrenador(1, new Pocion("Potion"));

        // Iniciar batalla
        modo.iniciarBatalla();

        EstadoJuego estado = juego.obtenerEstadoActual();
        assertNotNull(estado);
        assertNotNull(estado.pokemonActivo);
        assertNotNull(estado.pokemonOponente);
    }

//MODO SUPERVIVENCIA
@Test
public void testModoSupervivenciaPreparaBatalla() throws ExceptionPOOBkemon {
    Juego juego = new Juego();
    Supervivencia modo = new Supervivencia();
    modo.configurarJuego(juego);

    modo.prepararBatalla("Red", "Blue");

    EstadoJuego estado = juego.obtenerEstadoActual();

    assertNotNull(estado);
    assertTrue(estado.nombreJugador.equals("Red") || estado.nombreJugador.equals("Blue"));
    assertTrue(estado.nombreOponente.equals("Red") || estado.nombreOponente.equals("Blue"));
    assertNotNull(estado.pokemonActivo);
    assertNotNull(estado.pokemonOponente);
    assertNotEquals(estado.pokemonActivo, estado.pokemonOponente);
}

}
