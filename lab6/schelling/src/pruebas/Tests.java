package pruebas;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.* ;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
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

@Test
public void shouldExportCityToTextFile() {
    try {
        File archivo = new File("cityTestExport.txt");

        // Poner algunos elementos en la ciudad
        ciudad.setItem(5, 5, new Person(ciudad, 5, 5));
        ciudad.setItem(10, 10, new Walker(ciudad, 10, 10));
        ciudad.setItem(15, 15, new Hole(ciudad, 15, 15));
        ciudad.setItem(20, 20, new Solitaria(ciudad, 20, 20));

        ciudad.exportar(archivo);
        assertTrue(archivo.exists());
        assertTrue(archivo.length() > 0);

        // Leer archivo para verificar contenido
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        String line;
        boolean foundPerson = false, foundWalker = false, foundHole = false, foundSolitaria = false;
        while ((line = reader.readLine()) != null) {
            if (line.equals("Person 5 5")) foundPerson = true;
            if (line.equals("Walker 10 10")) foundWalker = true;
            if (line.equals("Hole 15 15")) foundHole = true;
            if (line.equals("Solitaria 20 20")) foundSolitaria = true;
        }
        reader.close();
        archivo.delete();

        assertTrue(foundPerson && foundWalker && foundHole && foundSolitaria);
    } catch (Exception e) {
        fail("No se esperaba excepción: " + e.getMessage());
    }
}

@Test
public void shouldImportCityFromTextFile() {
    try {
        File archivo = new File("cityTestImport.txt");

        PrintWriter writer = new PrintWriter(archivo);
        writer.println("Person 6 6");
        writer.println("Walker 7 7");
        writer.println("Hole 8 8");
        writer.println("Solitaria 9 9");
        writer.close();

        City nuevaCiudad = City.importar(archivo);
        archivo.delete();

        assertTrue(nuevaCiudad.getItem(6, 6) instanceof Person);
        assertTrue(nuevaCiudad.getItem(7, 7) instanceof Walker);
        assertTrue(nuevaCiudad.getItem(8, 8) instanceof Hole);
        assertTrue(nuevaCiudad.getItem(9, 9) instanceof Solitaria);
    } catch (Exception e) {
        fail("No se esperaba excepción: " + e.getMessage());
    }
}


    @After
    public void tearDown() {
        ciudad = null;
    }
}
