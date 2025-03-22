package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.* ;
/**
 * The test class Tests.
 *
 * @author Carolina cepeda
 * @version 20/03/25
 */

public class Tests{
    private City ciudad;

    @BeforeEach 
    public void setUp() {
        ciudad = new City();
    }

    @Test
    public void shouldPerson(){
        Person persona1 = new Person(ciudad, 20, 10);
        char estadoInicial = persona1.getState();
        ciudad.ticTac();
        char nuevoEstado = persona1.getState();
        assertNotEquals(estadoInicial,nuevoEstado,"el estado de la persona debe cambiar");

    }

    @Test
    public void shouldPersonMove(){
        Person persona2 = new Person(ciudad, 10, 10);
        persona2.decide();
        if (persona2.getState() == 'D'){
            int AnteriorRow = persona2.getRow();
            int Anteriorcolumn = persona2.getColumn();
            ciudad.ticTac();
            int nuevaRow = persona2.getRow();
            int nuevaColumna = persona2.getColumn();

            assertFalse(AnteriorRow==nuevaRow && Anteriorcolumn==nuevaColumna,"la persona debe moverse si esta insatisfecha");
        }
    }
    @Test
    public void shouldWalk(){
        Walker caminante = new Walker(ciudad, 20, 10);
        caminante.decide();
        int anteriorFila = caminante.getRow();
        ciudad.ticTac();
        int nuevaFila = caminante.getRow();
        assertTrue(anteriorFila >= nuevaFila);

    }

    @Test
    public void shouldNotWalk(){
        Walker caminante = new Walker(ciudad, 20, 20);
        Person personaObstaculo = new Person(ciudad, 19, 20);
        caminante.decide();
        int anteriorFila = caminante.getRow();
        ciudad.ticTac();
        int nuevaFila = caminante.getRow();
        assertTrue(anteriorFila == nuevaFila);

    }
    
    @Test
    public void shouldTrafficLight(){

    }

    @Test
    public void shouldNotTrafficLight(){

    }

    @Test
    public void shouldnotMoveSolitaria(){
        Solitaria solitario = new Solitaria(ciudad, 3, 3);
        int pxActual = solitario.getRow();
        int pyActual = solitario.getColumn();
        ciudad.ticTac();
        assertTrue(pxActual == solitario.getRow() && pyActual== solitario.getColumn());
    }

    @Test
    public void shouldBeHappySolitaria(){
        Solitaria solitario = new Solitaria(ciudad, 23, 15);
        ciudad.ticTac();
        assertTrue(solitario.getState()=='h');
    }
   

    public void tearDown() {
        ciudad = null;
        
    }
}
