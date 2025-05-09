package pruebas;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.* ;
import java.awt.Color;
import java.io.File;
/**
 * The test class Tests.
 *
 * @author Carolina cepeda
 * @version 22/03/25
 */

public class Tests{
    private City ciudad;

    @Before
    public void setUp() {
        ciudad = new City();
    }

    @Test
    public void shouldPerson(){
        Person persona1 = new Person(ciudad, 20, 10);
        char estadoInicial = persona1.getState();
        ciudad.ticTac();
        char nuevoEstado = persona1.getState();
        assertTrue("El estado de la persona debe cambiar", estadoInicial != nuevoEstado);

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

            assertTrue("La persona no debe moverse si está insatisfecha", AnteriorRow == nuevaRow && Anteriorcolumn == nuevaColumna);
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

    @Test
public void shouldSaveCityToFile() {
    try {
        File archivo = new File("oneCity.dat");
        ciudad.save(archivo);
        assertTrue("El archivo no fue creado.", archivo.exists());
        assertTrue("El archivo está vacío.", archivo.length() > 0);
        archivo.delete(); 
    } catch (CityException e) {
        fail("No se esperaba excepción al guardar: " + e.getMessage());
    }
}

@Test
public void shouldOpenCityFromFile() {
    try {
       
        ciudad.setItem(5, 5, new Solitaria(ciudad, 5, 5));
        File archivo = new File("oneCity.dat");
        ciudad.save(archivo);

        City ciudadCargada = City.open(archivo);
        assertTrue("El item no fue restaurado correctamente", ciudadCargada.getItem(5, 5) != null);
        assertTrue("El tipo de objeto no coincide", Solitaria.class.equals(ciudadCargada.getItem(5, 5).getClass()));
        archivo.delete(); 
    } catch (CityException e) {
        fail("No se esperaba excepción al abrir: " + e.getMessage());
    }
}


    @After
    public void tearDown() {
        ciudad = null;
    }
}
