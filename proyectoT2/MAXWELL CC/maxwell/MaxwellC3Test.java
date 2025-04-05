package maxwell;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MaxwellC3Test.
 *
 * @author  carolina cepeda
 * @version 
 */
public class MaxwellC3Test
{
    private MaxwellContainer mc;
    /**
     * Default constructor for test class MaxwellC3Test
     */
    public MaxwellC3Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        int[][] particulas = {
                {55, 50, 8, 3},
                {30, 100, 7, 5}
            };
        mc = new MaxwellContainer(150,150,70,1,1,particulas);
    }

    @Test
    public void shouldEphemeral() {
        Particle efimera = new Ephemeral("neonblue", true, 12, 13, 2, 2, 150, 150);
        mc.addParticle(efimera);
        
        int cantidadAntes = mc.particles().length;

        int xInicial = efimera.getpX();
        int yInicial = efimera.getpY();

        mc.start(10);
        assertTrue(xInicial != efimera.getpX());
        assertTrue(yInicial != efimera.getpY());
        
        mc.start(100);
        int cantidadDespues = mc.particles().length;
        assertNotEquals(cantidadAntes, cantidadDespues);

    }

    @Test
    public void shouldErratica(){
        
        Particle erratica = new Erratica("neonblue", true, 22, 15, 5, 5, 150, 150);
        mc.addParticle(erratica);

        int xInicial = erratica.getpX();
        int yInicial = erratica.getpY();

        mc.start(10);
        assertTrue(xInicial != erratica.getpX());
        assertTrue(yInicial != erratica.getpY());

    }

    @Test
    public void shouldFlying(){
        mc.addHole(70,51,2);
        Particle vola = new Flying("neonblue", true,70, 50, 0, 1, 150, 150);
        mc.addParticle(vola);

        int cantidadAntes = mc.particles().length;
        mc.start(2);
        int cantidadDespues = mc.particles().length;
        
        assertEquals(cantidadAntes,cantidadDespues);

    }

    @Test
    public void shouldRotator(){
        Particle rotator = new Rotator("neonblue", true,70, 0, 3, 0, 150, 150);
        mc.addParticle(rotator);
        mc.start(5);
        assertEquals(0,rotator.getvX());
        assertEquals(3,rotator.getvY());

    }

    @Test
    public void shouldWeak(){
        Demon demonio = new Weak(150,150,30);
        Particle particula = new Particle("neonblue", true,50, 30, -3, 0, 150, 150);
        mc.addParticle(particula);
        mc.addDemon(demonio);
        mc.start(10);
        
        assertEquals(1,mc.demons().length);

    }

    @Test
    public void shouldBlue(){
        Demon demonio = new Blue(150,150,30);
        Particle particula = new Particle("neonblue", true,75, 30, -3, 0, 150, 150);
        assertFalse(demonio.pasar(particula,150,150));
    
    }

    @Test
    public void shouldMovil(){
        Hole hole = new Movil(2,2,150,150,2);
        mc.start(4);
        assertNotEquals(2,hole.getpX());
        assertNotEquals(2,hole.getpY());

    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        mc = null;
    }
}
