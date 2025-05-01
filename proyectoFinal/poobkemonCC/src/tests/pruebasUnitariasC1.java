package test;

import dominio.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class pruebasUnitariasC1 {

    @Test
    public void testCreacionPokemon() {
        Movimiento[] movimientos = {
            new MovimientoNormal("Placaje", 40, 100, 35, 0, "Normal", null),
            new MovimientoNormal("Ascuas", 40, 100, 25, 0, "Fuego", null),
            new MovimientoNormal("Gruñido", 0, 100, 40, 0, "Normal", "Bajar ataque"),
            new MovimientoNormal("Lanzallamas", 90, 100, 15, 0, "Fuego", "Quemar")
        };
        Pokemon charmander = new Pokemon("Charmander", 200, 30, "Fuego", null, 150, 100, 120, 110, 160, 100, 100, movimientos);
        assertEquals("Fuego", charmander.getTipoPrincipal());
        assertEquals(200, charmander.getSalud());
        assertEquals(4, movimientos.length);
    }

    @Test
    public void testEntrenadorYItem() {
        Entrenador ash = new Entrenador("Ash", "Rojo");
        Pokemon pikachu = crearPokemonBasico("Pikachu");
        ash.agregarPokemon(pikachu);
        ash.agregarItem(new Pocion("Potion"));
        assertEquals("Ash", ash.getNombre());
        assertEquals(1, ash.getPokemones().size());
        assertEquals(1, ash.getItems().size());
    }

    @Test
    public void testUsoItem() {
        Pokemon bulbasaur = crearPokemonBasico("Bulbasaur");
        bulbasaur.aumentoSalud(-50);
        assertEquals(150, bulbasaur.getSalud());

        Pocion pocion = new Pocion("SuperPotion");
        pocion.usar(bulbasaur);
        assertEquals(200, bulbasaur.getSalud());
    }

    @Test
    public void testBatallaAtaque() {
        Movimiento[] movimientos = {
            new MovimientoNormal("Placaje", 40, 100, 35, 0, "Normal", null),
            new MovimientoNormal("Nada", 0, 100, 40, 0, "Normal", null),
            new MovimientoNormal("Nada2", 0, 100, 40, 0, "Normal", null),
            new MovimientoNormal("Nada3", 0, 100, 40, 0, "Normal", null),
        };
        Pokemon p1 = new Pokemon("A", 300, 50, "Normal", null, 200, 150, 150, 150, 150, 100, 100, movimientos);
        Pokemon p2 = new Pokemon("B", 300, 50, "Normal", null, 150, 100, 100, 100, 100, 100, 100, movimientos);

        Entrenador e1 = new Entrenador("Rojo", "Rojo");
        Entrenador e2 = new Entrenador("Azul", "Azul");
        e1.agregarPokemon(p1);
        e2.agregarPokemon(p2);

        Juego juego = new Juego();
        juego.empezarBatalla(e1, e2);

        juego.ejecutarTurno("atacar", 0);  // Primer turno
        juego.ejecutarTurno("atacar", 0);  // Segundo turno

        assertTrue(p1.getSalud() < 300 || p2.getSalud() < 300);  // Uno recibió daño
    }

    @Test
    public void testModoJuegoNormal() {
        Juego juego = new Juego();
        ModoJuego normal = new Normal();
        juego.seleccionarModoJuego(normal);
        assertTrue(juego.hayBatallaActiva());
    }

    private Pokemon crearPokemonBasico(String nombre) {
        Movimiento[] movimientos = {
            new MovimientoNormal("Placaje", 40, 100, 35, 0, "Normal", null),
            new MovimientoNormal("Nada", 0, 100, 40, 0, "Normal", null),
            new MovimientoNormal("Nada2", 0, 100, 40, 0, "Normal", null),
            new MovimientoNormal("Nada3", 0, 100, 40, 0, "Normal", null)
        };
        return new Pokemon(nombre, 200, 30, "Normal", null, 150, 100, 120, 110, 160, 100, 100, movimientos);
    }
}
