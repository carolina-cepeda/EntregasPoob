package maxwell;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MaxwellContainerC1Test.
 *
 * @author Carolina cepeda
 * @version 29/03/25
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
        assertTrue(demons.length ==1 && demons[0]==30, "Debería agregar un demonio a la lista");
    }

    @Test
    public void shouldNotaddDemon() {
        container.addDemon(80);
        container.addDemon(80);
        int[] demons = container.demons();
        assertEquals(1,demons.length, "No debería agregar demonios duplicados");
    }

    @Test
    public void shoulddelDemon() {
        container.addDemon(8);
        container.delDemon(8);
        int[] demons = container.demons();
        int[] esperado = new int[]{};
        assertTrue(demons.length == 0, "Debería eliminar el demonio de la lista");
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
        int[] esperado = {100,120,3,4};

        assertTrue(particles.length==1 && Arrays.equals(particles[0],esperado) , "Debería agregar una partícula a la lista");
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
        container.addParticle("blue", true, 100, 120, 3, 4);
        container.delParticle("red");
        int[][] particles = container.particles();
        assertTrue(particles.length ==1, "No debería fallar si la partícula no existe");
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
        int[][] esperado = new int[][]{};
        assertArrayEquals(esperado, holes);

    }

    @Test
    public void shouldisGoal() {
        container.addParticle("red", true, -75, 100, 0, 0);
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
    void testAgujerosFuncionan() {
        int[][] particulas = {
            {85, 80, 8, 3},
            {30, 100, 7, 5}
        };
        container = new MaxwellContainer(200, 200, 70, 1, 1, particulas);
        container.addHole(85, 80, 1);
        container.start(200);
        assertTrue(container.isGoal(), "La partícula debería haber caído en el agujero.");
    }
    
    @Test
    void testDemonioSirve() {
        int[][] particulas = {
            {0, 85, 8, 3},
            {30, 100, 7, 5}
        };
        container = new MaxwellContainer(200, 200, 85, 1, 1, particulas);
        container.addHole(80, 80, 1);
        container.start(200);

        assertTrue(container.isGoal(), "El demonio de Maxwell debería haber funcionado correctamente.");
    }
    @Test
    public void shouldDemons() {
        container.addDemon(100);
        assertEquals(1, container.demons().length);

    }

    @Test
    public void shouldNotDemons() {
        container.addDemon(10000);
        int[] esperado = new int[]{};
        assertArrayEquals(esperado, container.demons());

    }

    @Test
    public void shouldParticles() {
        container.addParticle("blue", true, 12, 12, 10, 10);
        int[][] particles = container.particles();
        assertEquals(1, particles.length);
    }

    @Test
    public void shouldSortParticles(){
        container.addParticle("blue", true, 12, 12, 1, 1);
        container.addParticle("red", true, 10, 10, 2, 2);
        container.addParticle("green", true, 8, 8, 3, 3);

        int[][] particles = container.particles();
        int[][] expectedParticles = { { 8, 8, 3, 3 }, { 10, 10, 2, 2 }, { 12, 12, 1, 1} };

        assertTrue(Arrays.deepEquals(particles, expectedParticles), "Las partículas deberían estar ordenadas por posición.");
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

}
