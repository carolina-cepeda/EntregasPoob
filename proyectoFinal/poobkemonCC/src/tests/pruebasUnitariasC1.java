package tests;

import dominio.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class pruebasUnitariasC1 {

    // --------- CREACIÓN Y PROPIEDADES ---------

    @Test
    public void testCreacionPokemonConMovimientos() {
        Movimiento[] movimientos = {
            new MovimientoNormal("Placaje", 40, 100, 35, 0, "Normal", null),
            new MovimientoNormal("Ascuas", 40, 100, 25, 0, "Fuego", null),
            new MovimientoNormal("Gruñido", 0, 100, 40, 0, "Normal", "Bajar ataque"),
            new MovimientoNormal("Lanzallamas", 90, 100, 15, 0, "Fuego", "Quemar")
        };
        Pokemon charmander = new Pokemon("Charmander", 200, 30, "Fuego", null, 150, 100, 120, 110, 160, 100, 100, movimientos);

        assertEquals("Charmander", charmander.nombre);
        assertEquals("Fuego", charmander.getTipoPrincipal());
        assertEquals(200, charmander.getSalud());
        assertEquals(4, charmander.getMovimientos().size());
    }

    // --------- ENTRENADOR E ÍTEMS ---------

    @Test
    public void testAgregarPokemonEItemAEntrenador() {
        Entrenador ash = new Entrenador("Ash", "Rojo");
        Pokemon pikachu = crearPokemonBasico("Pikachu");
        ash.agregarPokemon(pikachu);
        ash.agregarItem(new Pocion("Potion"));

        assertEquals("Ash", ash.getNombre());
        assertEquals(1, ash.getPokemones().size());
        assertEquals("Pikachu", ash.getPokemonActivo().nombre);
        assertEquals(1, ash.getItems().size());
    }

    @Test
    public void testUsoDePocionCuraCorrectamente() {
        Pokemon bulbasaur = crearPokemonBasico("Bulbasaur");
        bulbasaur.aumentoSalud(-50);
        assertEquals(150, bulbasaur.getSalud());

        Pocion pocion = new Pocion("SuperPotion"); 
        pocion.usar(bulbasaur);
        assertEquals(200, bulbasaur.getSalud());
    }

    @Test
    public void testUsoDeReviveNoPasaDeSaludMaxima() {
        Pokemon squirtle = crearPokemonBasico("Squirtle");
        Pocion revive = new Pocion("Revive"); 
        revive.usar(squirtle);
        assertEquals(200, squirtle.getSalud()); // debe quedarse en 200
    }

    // --------- BATALLA ---------

    @Test
    //fallando
   
    public void testBatallaEntreDosPokemones() {
        Movimiento[] movimientos = {
            new MovimientoNormal("Placaje", 40, 100, 35, 0, "Normal", null),
            new MovimientoNormal("Nada", 0, 100, 40, 0, "Normal", null),
            new MovimientoNormal("Nada2", 0, 100, 40, 0, "Normal", null),
            new MovimientoNormal("Nada3", 0, 100, 40, 0, "Normal", null),
        };

        Pokemon atacante = new Pokemon("Atacante", 300, 50, "Normal", null, 200, 150, 150, 150, 150, 100, 100, movimientos);
        Pokemon defensor = new Pokemon("Defensor", 300, 50, "Normal", null, 150, 100, 100, 100, 100, 100, 100, movimientos);

        Entrenador e1 = new Entrenador("Rojo", "Rojo");
        Entrenador e2 = new Entrenador("Azul", "Azul");
        e1.agregarPokemon(atacante);
        e2.agregarPokemon(defensor);

        Juego juego = new Juego();
        juego.empezarBatalla(e1, e2);

        int saludInicialDefensor = defensor.getSalud();

        juego.comenzarTurno("atacar", 0);
        juego.comenzarTurno("atacar", 1); 

        int saludFinalDefensor = defensor.getSalud();

        assertTrue(saludFinalDefensor < saludInicialDefensor);
    }

    @Test
    public void testCambioDeTurnoEnBatalla() {
        Entrenador e1 = new Entrenador("Jugador1", "Rojo");
        Entrenador e2 = new Entrenador("Jugador2", "Azul");

        e1.agregarPokemon(crearPokemonBasico("Pokemon1"));
        e2.agregarPokemon(crearPokemonBasico("Pokemon2"));

        Juego juego = new Juego();
        juego.empezarBatalla(e1, e2);

        Entrenador turnoAntes = juego.getTurnoActual();
        juego.comenzarTurno("atacar", 0);
        Entrenador turnoDespues = juego.getTurnoActual();

        assertNotEquals(turnoAntes, turnoDespues);
    }

    // --------- MODO DE JUEGO ---------

    @Test
    public void testModoNormalActivaBatalla() {
        Juego juego = new Juego();
        ModoJuego modo = new ModoJuegoMock(); // evitamos Scanner

        juego.seleccionarModoJuego(modo);
        assertTrue(juego.hayBatallaActiva());
    }

    // --------- MÉTODOS AUXILIARES ---------

    private Pokemon crearPokemonBasico(String nombre) {
        Movimiento[] movimientos = {
            new MovimientoNormal("Placaje", 40, 100, 35, 0, "Normal", null),
            new MovimientoNormal("Nada", 3, 100, 40, 0, "Normal", null),
            new MovimientoNormal("Nada2", 2, 100, 40, 0, "Normal", null),
            new MovimientoNormal("Nada3", 2, 100, 40, 0, "Normal", null)
        };
        return new Pokemon(nombre, 200, 30, "Normal", null, 150, 100, 120, 110, 160, 100, 100, movimientos);
    }

 // Mock del modo juego para evitar el uso de Scanner
 private static class ModoJuegoMock implements ModoJuego {
    @Override
    public void configurarJuego(Juego juego) {
        Entrenador e1 = new Entrenador("Mock1", "Rojo");
        Entrenador e2 = new Entrenador("Mock2", "Azul");
        e1.agregarPokemon(new Pokemon("Poke1", 100, 10, "Normal", null, 50, 50, 50, 50, 50, 50, 50,
                new Movimiento[]{new MovimientoNormal("Ataque", 40, 100, 30, 0, "Normal", null)}));
        e2.agregarPokemon(new Pokemon("Poke2", 100, 10, "Normal", null, 50, 50, 50, 50, 50, 50, 50,
                new Movimiento[]{new MovimientoNormal("Ataque", 40, 100, 30, 0, "Normal", null)}));
        juego.empezarBatalla(e1, e2);
    }
}
}