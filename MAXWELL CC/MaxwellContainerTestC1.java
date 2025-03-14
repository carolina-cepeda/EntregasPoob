
import static org.junit.jupiter.api.Assertions.*;
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
    public void shouldAgregarUnDemonioALaLista() {
        container.addDemon(30);
        int[] demons = container.demons();
        assertTrue(contains(demons, 30), "Debería agregar un demonio a la lista");
    }

    @Test
    public void shouldNoAgregarDemoniosDuplicados() {
        container.addDemon(80);
        container.addDemon(80);
        int[] demons = container.demons();
        assertEquals(1, contadorOcurrencias(demons, 80), "No debería agregar demonios duplicados");
    }

    @Test
    public void shouldEliminarUnDemonioDeLaLista() {
        container.addDemon(80);
        container.delDemon(80);
        int[] demons = container.demons();
        assertFalse(contains(demons, 80), "Debería eliminar el demonio de la lista");
    }

    @Test
    public void shouldNoFallarSiElDemonioNoExiste() {
        container.delDemon(80);
        int[] demons = container.demons();
        assertFalse(contains(demons, 80), "debería fallar si el demonio no existe");
    }

    @Test
    public void shouldAgregarUnaParticulaALaLista() {
        container.addParticle("red", true, 100, 120, 3, 4);

        int[][] particles = container.particles();

        assertTrue(containsParticle(particles, 100, 120, 3, 4), "Debería agregar una partícula a la lista");
    }

    @Test
    public void shouldNoAgregarParticulasDuplicadas() {
        container.addParticle("red", true, 100, 120, 3, 4);
        container.addParticle("red", true, 100, 120, 3, 4);
        int[][] particles = container.particles();
        assertEquals(1, contadorParticula(particles, 100, 120, 3, 4), "No debería agregar partículas duplicadas");
    }

    @Test
    public void shouldEliminarUnaParticulaDeLaLista() {
        container.addParticle("red", true, 100, 120, 3, 4);
        container.delParticle("red");
        int[][] particles = container.particles();
        assertFalse(containsParticle(particles, 100, 120, 3, 4), "Debería eliminar la partícula de la lista");
    }

    @Test
    public void shouldFallarSiLaParticulaNoExiste() {
        container.delParticle("red");
        int[][] particles = container.particles();
        assertFalse(containsParticle(particles, 100, 120, 3, 4), "No debería fallar si la partícula no existe");
    }

    @Test
    public void shouldRetornarTrueSiTodasLasParticulasEstanEnLaPosicionObjetivo() {
        container.addParticle("red", true, 70, 100, 0, 0);
        container.addParticle("blue", false, 85, 100, 0, 0);
        assertTrue(container.isGoal(), "Debería retornar true cuando el juego termina.");
    }

    @Test
    public void shouldRetornarFalseSiNoTodasLasParticulasEstanEnLaPosicionObjetivo() {
        container.addParticle("red", true, 100, 100, 0, 0);
        container.addParticle("blue", false, 85, 100, 0, 0);
        assertFalse(container.isGoal(), "Debería retornar false cuando el juego no haya terminado.");
    }

    @AfterEach
    public void tearDown() {
        container = null;
    }

    // Métodos auxiliares
    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }

    private int contadorOcurrencias(int[] array, int value) {
        int count = 0;
        for (int i : array) {
            if (i == value) {
                count++;
            }
        }
        return count;
    }

    private boolean containsParticle(int[][] particles, int px, int py, int vx, int vy) {

        for (int[] particle : particles) {
            if (particle[0] == px && particle[1] == py && particle[2] == vx && particle[3] == vy) {
                return true;
            }
        }
        return false;
    }

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
