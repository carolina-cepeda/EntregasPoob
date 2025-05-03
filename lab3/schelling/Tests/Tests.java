package Tests;

import com.sun.source.tree.AssertTree;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.* ;
import java.awt.Color;
/**
 * The test class Tests.
 *
 * @author Carolina cepeda
 * @version 22/03/25
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
    public void shouldPersonNotMove(){
        Person persona2 = new Person(ciudad, 10, 10);
        if (persona2.getState() == 'D'){
            int AnteriorRow = persona2.getRow();
            int Anteriorcolumn = persona2.getColumn();
            ciudad.ticTac();
            int nuevaRow = persona2.getRow();
            int nuevaColumna = persona2.getColumn();

            assertTrue(AnteriorRow==nuevaRow && Anteriorcolumn==nuevaColumna,"la persona no debe moverse si esta insatisfecha");
        }
    }
    @Test
    public void shouldWalk(){
        Walker caminante = new Walker(ciudad, 20, 10);
        int anteriorFila = caminante.getRow();
        ciudad.ticTac();
        int nuevaFila = caminante.getRow();
        assertTrue(anteriorFila >= nuevaFila);

    }

    @Test
    public void shouldNotWalk(){
        Walker caminante = new Walker(ciudad, 20, 20);
        ciudad.setItem(20,20,caminante);
        Person personaObstaculo = new Person(ciudad, 19, 20);
        ciudad.setItem(19,20,personaObstaculo);
        int anteriorFila = caminante.getRow();
        ciudad.ticTac();
        int nuevaFila = caminante.getRow();
        assertTrue(anteriorFila == nuevaFila);

    }
    
    @Test
    public void shouldTrafficLight(){
        TrafficLight semaforo1 = new TrafficLight(ciudad, 20, 20);
        assertTrue(semaforo1.getColor() == Color.red);
    }

    @Test
    public void shouldbeActiveTrafficLight(){
        TrafficLight semaforo2 = new TrafficLight(ciudad, 16, 22);
        ciudad.ticTac();
        assertTrue(semaforo2.isActive());
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
        Solitaria solitario = new Solitaria(ciudad, 12, 15);
        ciudad.ticTac();
        assertTrue(solitario.isHappy());
    }

    @Test
    public void shouldNotBeHappySolitarios(){
        Solitaria solitario1 = new Solitaria(ciudad, 23, 15);
        Solitaria solitario2= new Solitaria(ciudad, 24, 14);
        Solitaria persona1 = new Solitaria(ciudad, 23, 14);
        Solitaria persona2 = new Solitaria(ciudad, 24, 15);
        ciudad.ticTac();
        assertTrue(solitario1.isIndifferent() && solitario2.isIndifferent());
    }
    @Test
    public void shouldCreateHole(){
        int row = 23, column= 24;
        Hole hole = new Hole(ciudad,row,column);
        assertTrue(hole.getRow() == row && hole.getColumn()==column);
    }
    @Test
    public void ShouldNotMoveHole(){
        Hole hole = new Hole(ciudad, 12, 20);
        int anterior = hole.getRow();
        ciudad.ticTac();
        assertTrue( anterior == hole.getRow());
        
    }
    @Test
    public void ShouldBeAnObstacle(){
        Walker walker = new Walker(ciudad, 10, 20);
        ciudad.setItem(10, 20, walker);
        Hole obstaculo = new Hole(ciudad, 9, 20);
        ciudad.setItem(9, 20, obstaculo);
        int fila1 = walker.getRow();
        ciudad.ticTac();
        int fila2 = walker.getRow();
        assertTrue( fila1 == fila2);
    }

    public void tearDown() {
        ciudad = null;
    }
}
