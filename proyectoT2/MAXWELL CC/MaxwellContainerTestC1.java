
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MaxwellContainerC1Test.
 *
 * @author Carolina cepeda
 * @version 13/03/25
 */

public class MaxwellContainerTestC1 {
    private MaxwellContainer container;

    @BeforeEach
    public void setUp() {
        // Inicializa un objeto MaxwellContainer antes de cada prueba
        container = new MaxwellContainer(200, 200);
    }

    @Test
    public void ShouldMC1() {
        assertNotNull(container);
    }

    @Test //mejorar
    public void ShouldMC2() {
        assertNotNull(container);
    }

    @Test
    public void shouldaddDemon() {
        container.addDemon(30);
        int[] demons = container.demons();
        assertTrue(contains(demons, 30), "Debería agregar un demonio a la lista");
    }

    @Test
    public void shouldNotaddDemon() {
        container.addDemon(80);
        container.addDemon(80);
        int[] demons = container.demons();
        assertEquals(1, contadorOcurrencias(demons, 80), "No debería agregar demonios duplicados");
    }

    @Test
    public void shoulddelDemon() {
        container.addDemon(80);
        container.delDemon(80);
        int[] demons = container.demons();
        assertTrue(demons ==null, "Debería eliminar el demonio de la lista");
    }

    @Test
    public void shouldNotdelDemon() {
        container.delDemon(80);
        container.addDemon(100);
        int[] demons = container.demons();
        assertTrue(demons.length == 1 && demons[0]==100 , "debería fallar si el demonio no existe");
    }

    @Test
    public void shouldaddParticle() {
        container.addParticle("red", true, 100, 120, 3, 4);

        int[][] particles = container.particles();

        assertTrue(containsParticle(particles, 100, 120, 3, 4), "Debería agregar una partícula a la lista");
    }

    @Test
    public void shouldNotAddDuplicateParticle() {
        container.addParticle("red", true, 100, 120, 3, 4);
        container.addParticle("red", true, 100, 120, 3, 4);
        int[][] particles = container.particles();
        assertTrue(particles.length == 1, "No debería agregar partículas duplicadas");
    }

    @Test
    public void shouldDelParticle() {
        container.addParticle("red", true, 100, 120, 3, 4);
        container.delParticle("red");
        int[][] particles = container.particles();
        assertEquals(0,particles.length);
    }

    @Test
    public void shouldnotDelParticle() {
        container.delParticle("red");
        int[][] particles = container.particles();
        assertFalse(containsParticle(particles, 100, 120, 3, 4), "No debería fallar si la partícula no existe");
    }

    @Test
    public void shouldAddHole() {
        container.addHole(100, 120, 2);
        int[][] holes = container.holes();
        assertEquals(1, holes.length);

    }

    @Test
    public void shouldNotAddHole() {
        container.addHole(-5000, 0, 2);
        int[][] holes = container.holes();
        assertEquals(null, holes);

    }

    @Test
    public void shouldisGoal() {
        container.addParticle("red", true, 75, 100, 0, 0);
        container.addParticle("blue", false, 185, 100, 0, 0);
        assertTrue(container.isGoal(), "Debería retornar true cuando el juego termina.");
    }

    @Test
    public void shouldNotisGoalTrue() {
        container.addParticle("red", true, 100, 100, 0, 0);
        container.addParticle("blue", false, 85, 100, 0, 0);
        assertFalse(container.isGoal(), "Debería retornar false cuando el juego no haya terminado.");
    }

    @Test
    public void shouldDemons() {
        container.addDemon(100);
        assertEquals(1, container.demons().length);

    }

    @Test
    public void shouldNotDemons() {
        container.addDemon(10000);
        assertEquals(null, container.demons());

    }

    @Test
    public void shouldParticles() {
        container.addParticle("blue", true, 120, 120, 10, 10);
        assertEquals(1, container.particles().length);
    }

    @Test
    public void shouldNotParticles() {
        assertEquals(0, container.particles().length);
    }

    @Test
    public void shouldHoles() {
        container.addHole(80, 130, 2);
        assertEquals(1, container.holes().length);
    }

    @Test
    public void shouldNotHoles() {
        container.addHole(80, 130, 2);
        assertEquals(1, container.holes().length);
    }

    @Test
    public void shouldStart() {
        int ticks = 5;
        container.addParticle("red", true, 100, 100, 20, 20);
        container.addHole(100, 100, 2);
        container.start(ticks);
        assertTrue(container.particles().length == 0);
    }

    public void tearDown() {
        container = null;
    }

    // Métodos auxiliares
    /*
     * metodo que verifica que un valor este en un arreglo
     */
    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    /*
     * metodo que verifica cuantas veces se encuentra un valor en un arreglo
     */
    private int contadorOcurrencias(int[] array, int value) {
        int count = 0;
        for (int i : array) {
            if (i == value) {
                count++;
            }
        }
        return count;
    }

    /*
     * metodo que verifica si una particula esta contenida en un arreglo de
     * particulas
     */
    private boolean containsParticle(int[][] particles, int px, int py, int vx, int vy) {

        for (int[] particle : particles) {
            if (particle[0] == px && particle[1] == py && particle[2] == vx && particle[3] == vy) {
                return true;
            }
        }
        return false;
    }

    /*
     * metodo que cuenta la cantidad de veces que se encuentra una particula en el
     * arreglo
     * 
     */

    private int contadorParticula(int[][] particles, int px, int py, int vx, int vy) {
        int count = 0;
        for (int[] particle : particles) {
            if (particle[0] == px && particle[1] == py && particle[2] == vx && particle[3] == vy) {
                count++;
            }
        }
        return count;
    }
}
