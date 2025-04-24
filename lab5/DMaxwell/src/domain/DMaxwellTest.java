package domain;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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

@Test
public void shouldFinishWhenAllParticlesFallIntoHolesProperly() {
    int fila = 0;
    Agujero agujero = new Agujero(6, 0);
    Agujero agujero2 = new Agujero(6, 1);
    dMaxwell.getElementos().add(agujero);
    dMaxwell.getElementos().add(agujero2);
    // Colocar agujeros en la columna 5, filas 0, 1, 2, 3 (una por fila)
    for (Elemento e : dMaxwell.getElementos()) {
        if (e instanceof Agujero o) {
            o.setPosition(6, fila++);
        }
    }

    fila = 0;
    
    List<Particula> particulas = new ArrayList<>();
    for (Elemento e : dMaxwell.getElementos()) {
        if (e instanceof Particula p) {
            p.setPosition(5, fila++);
            particulas.add(p);
        }
    }

    fila = 0;
    for (Particula p : particulas) {
        dMaxwell.moverParticula(5, fila, 1, 0);
        fila++;
    }

    double caidas = dMaxwell.calcularParticulasCaidas();
    System.out.println("Caídas: " + caidas + "%");

    assertEquals("Debería haber 100% de partículas caídas", 100.0, caidas, 0.1);
    assertTrue("El juego debería terminar por caída total", dMaxwell.finish());
    assertTrue(dMaxwell.isSimulacionTerminada());
    
}




@Test
public void shouldFinishWhenAllParticlesAreInCorrectPosition() {
    int filaRoja = 0;
    int filaAzul = 0;

    for (Elemento e : dMaxwell.getElementos()) {
        if (e instanceof Particula p) {
            if (p.isRed()) {
                p.setPosition(1, filaRoja++); 
            } else {
                p.setPosition(8, filaAzul++); 
            }
        }
    }

    boolean terminado = dMaxwell.finish();

    assertTrue("Debería terminar porque todas están en posición correcta", terminado);
    assertTrue(dMaxwell.isSimulacionTerminada());
}



}
