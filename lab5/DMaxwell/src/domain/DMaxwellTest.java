package domain;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DMaxwellTest {
    DMaxwell dMaxwell;
    @Before
    public void setUp(){
        dMaxwell = new DMaxwell(10, 10, 5, 5, 2);
    }

    @After
    public void tearDown(){
        dMaxwell = null;
    }

    @Test
    public void shouldCreateDMaxwellCorrectly() {
        assertNotNull(dMaxwell);
        assertEquals(10, dMaxwell.getH());
        assertEquals(10, dMaxwell.getW());
        assertEquals(2, dMaxwell.getCantidadHoles());
        assertEquals(5, dMaxwell.getCantidadRojas());
        assertEquals(5, dMaxwell.getCantidadAzules());
        assertEquals(0, dMaxwell.getAfectadas());
        assertEquals(1, dMaxwell.getCantidadDemonios());

    }



    @Test // el random podria hacer fallar este test x los limites de las paredes, replantear o añadir set
    public void shouldMoveParticle(){
        int pyInicial = dMaxwell.getElementos().get(0).py;
        int pxInicial = dMaxwell.getElementos().get(0).px;
        dMaxwell.moverParticula(pxInicial,pyInicial, 1, 1);
        int pyFinal = dMaxwell.getElementos().get(0).py;
        int pxFinal = dMaxwell.getElementos().get(0).px;
        assertEquals(pyFinal, pyInicial + 1);
        assertEquals(pxFinal, pxInicial + 1);

    }

    @Test 
    public void shouldFallIntoHole(){
        dMaxwell.getElementos().get(0).setPosition(1, 1);
        dMaxwell.getElementos().get(10).setPosition(1, 2);
        dMaxwell.moverParticula(1, 1, 0,1);
        assertEquals(1, dMaxwell.getAfectadas());
    
    }

}
