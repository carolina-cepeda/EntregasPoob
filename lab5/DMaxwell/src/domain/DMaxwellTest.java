package domain;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DMaxwellTest {
    DMaxwell dMaxwell;
    @Before
    public void setUp(){
        dMaxwell = new DMaxwell(10, 10, 2, 2, 2);

        dMaxwell.getElementos().get(0).setPosition(1, 1); //b1
        dMaxwell.getElementos().get(1).setPosition(2, 2); //b2
        dMaxwell.getElementos().get(2).setPosition(3, 3); //r1
        dMaxwell.getElementos().get(3).setPosition(4, 4); //r2
        dMaxwell.getElementos().get(4).setPosition(5, 5); //o1
        dMaxwell.getElementos().get(5).setPosition(6, 6); //o2
        dMaxwell.getElementos().get(6).setPosition(7, 7); //d
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
        assertEquals(2, dMaxwell.getCantidadRojas());
        assertEquals(2, dMaxwell.getCantidadAzules());
        assertEquals(0, dMaxwell.getAfectadas());
        assertEquals(1, dMaxwell.getCantidadDemonios());

    }



    @Test 
    public void shouldMoveParticle(){
        int pyInicial = dMaxwell.getElementos().get(0).py;
        int pxInicial = dMaxwell.getElementos().get(0).px;
        dMaxwell.moverParticula(pxInicial,pyInicial, 1, 0);
        int pyFinal = dMaxwell.getElementos().get(0).py;
        int pxFinal = dMaxwell.getElementos().get(0).px;
        assertEquals(pyInicial ,pyFinal);
        assertEquals(pxInicial +1 ,pxFinal);

    }

    @Test 
    public void shouldFallIntoHole(){
        dMaxwell.getElementos().get(0).setPosition(1, 1);
        dMaxwell.getElementos().get(5).setPosition(1, 2);
        dMaxwell.moverParticula(1, 1, 0,1);
        assertEquals(1, dMaxwell.getAfectadas()); 
    
    }

    @Test
    public void shouldPassParticleToRightWhenDemon(){
        dMaxwell.getElementos().get(0).setPosition(1, 1);
        dMaxwell.getElementos().get(6).setPosition(1, 2);
        dMaxwell.moverParticula(1, 1, 0,1);
        assertEquals(0,dMaxwell.getElementos().get(0).px); 
    }

    @Test
    public void shouldNotLetParticlePass(){
        dMaxwell.getElementos().get(3).setPosition(1, 1);
        dMaxwell.getElementos().get(6).setPosition(1, 2);
        dMaxwell.moverParticula(1, 1, 0,-1);
        assertEquals(1,dMaxwell.getElementos().get(0).px); 
    }

    @Test
    public void shouldCalculateParticulasCaidas(){
        dMaxwell.getElementos().get(0).setPosition(1, 1);
        dMaxwell.getElementos().get(5).setPosition(1, 2);
        dMaxwell.moverParticula(1, 1, 0,1);
        assertEquals(25,dMaxwell.calcularParticulasCaidas(),0.001);
        assertEquals(1,dMaxwell.getAfectadas()); 
    }
    @Test
    public void shouldNotCalculateParticulasCaidas(){
        dMaxwell.getElementos().get(0).setPosition(1, 1);
        dMaxwell.getElementos().get(5).setPosition(1, 2);
        dMaxwell.moverParticula(1, 1, 0,-1);
        assertEquals(0,dMaxwell.calcularParticulasCaidas(),0.001);
        assertEquals(0,dMaxwell.getAfectadas());
    }

    @Test
    public void shouldCalculateParticulasCorrectas(){
        assertEquals(50.0,dMaxwell.calcularParticulasCorrectas(),0.001);
        assertEquals(0,dMaxwell.getAfectadas()); 
    }

    @Test
    public void shouldCalculate1ParticulaCorrecta(){
        dMaxwell.getElementos().get(0).setPosition(6, 1);
        assertEquals(25.0,dMaxwell.calcularParticulasCorrectas(),0.001);
        assertEquals(0,dMaxwell.getAfectadas()); 
    }
    
    @Test
    public void shouldNotLetParticlesMoveIntoSamePosition() {
    dMaxwell.getElementos().get(0).setPosition(1, 1);
    dMaxwell.getElementos().get(1).setPosition(0, 0);

    dMaxwell.moverParticula(0, 0, 1, 1); // intenta mover la partícula [1] a (1,1)

    assertEquals(0, dMaxwell.getElementos().get(1).getPx());
    assertEquals(0, dMaxwell.getElementos().get(1).getPy());
}

}
