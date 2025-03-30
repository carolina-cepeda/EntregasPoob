

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TestComunC1.
 *
 * @author  poob 2025-1
 * @version 29/03/25
 */
public class TestComunC1
{
    /**

     * Deberia de crear un contenedor con dimenciones validas.

     */

    @Test

    public void accordingPHRshouldCreateMaxwellContainerWithValidDimensions() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.makeInvisible();

        assertTrue(container.Ok()); 

    }

    

    /**

     * No deberia de crear un contenedor con dimenciones invalidas.

     */

    @Test

    public void accordingPHRshouldNotCreateMaxwellContainerWithInvalidDimensions() {

        MaxwellContainer container = new MaxwellContainer(1, 300);

        assertFalse(container.Ok()); 

    }

    

    /**

     * Deberia de agregar un demonio en una posicion valida.

     */

    @Test

    public void accordingPHRshouldAddDemonWithValidPosition() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.addDemon(50); // Posicion valida

        container.makeInvisible();

        assertTrue(container.Ok()); 

    }

    

    /**

     * No deberia de agregar un demonio en una posicion invalida.

     */

    @Test

    public void accordingPHRshouldNotAddDemonWithInvalidPosition() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.addDemon(150); // Posicion invalida

        container.makeInvisible();

        assertFalse(container.Ok()); 

    }

    

    /**

     * Deberia de eliminar un demonio correctamente.

     */

    @Test

    public void accordingPHRshouldDeleteExistingDemon() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.makeInvisible();

        container.addDemon(50); // Agregar un demonio 

        container.delDemon(50); // Eliminarlo

        container.makeInvisible();

        assertTrue(container.Ok()); 

    }

    

     /**

     * No deberia de eliminar un demonio que no existe.

     */

    @Test

    public void accordingPHRshouldNotDeleteNonExistingDemon() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.makeInvisible();

        container.delDemon(30); // Intentar eliminar un demonio inexistente

        assertFalse(container.Ok());

    }

    

    /**

     * Deberia de agregar particulas correctamente.

     */

    @Test

    public void accordingPHRshouldAddParticles() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.addParticle("red", true, 10, 10, 1, 1);

        container.addParticle("blue", false, 10, 10, 1, 1);

        container.makeInvisible();

        assertTrue(container.Ok());

    }

    

    /**

     * Deberia de eliminar una particula roja correctamente.

     */

    @Test

    public void accordingPHRshouldDeleteRedParticle() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.addParticle("red", true, 10, 10, 1, 1); // Agregar una particula roja

        container.delParticle("red"); // Eliminar la particula roja

        container.makeInvisible();

        assertTrue(container.Ok());

    }

    

    /**

     * Deberia de eliminar una particula azul correctamente.

     */

    @Test

    public void accordingPHRshouldDeleteBlueParticle() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.addParticle("blue", false, 10, 10, 1, 1); // Agregar una particula azul

        container.delParticle("blue"); // Eliminar la particula azul

        container.makeInvisible();

        assertTrue(container.Ok()); 

    }

    

    /**

     * No deberia de eliminar una particula roja que no existe.

     */

    @Test

    public void accordingPHRshouldNotDeleteRedParticleIfEmpty() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.makeInvisible();

        container.delParticle("red"); // Intentar eliminar cuando no hay rojas

        assertFalse(container.Ok());

    }

    

    /**

     * No deberia de eliminar una particula azul que no existe.

     */

    @Test

    public void accordingPHRshouldNotDeleteBlueParticleIfEmpty() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.makeInvisible();

        container.delParticle("blue"); // Intentar eliminar cuando no hay azules

        assertFalse(container.Ok());

    }

    

    /**

     * Deberia de agregar un agujero negro correctamente.

     */

    @Test

    public void accordingPHRshouldAddHole() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.makeInvisible();

        container.addHole(50, 50, 10); // Se agrega un agujero negro con posiciones validas

        container.makeInvisible();

        assertTrue(container.Ok());

    }

    

    /**

     * No deberia de agregar un agujero negro con posicion invalida.

     */

    @Test

    public void accordingPHRshouldNotAddHole() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.makeInvisible();

        container.addHole(200, 200, 10); // Se agrega un agujero negro con posiciones invalidas

        container.makeInvisible();

        assertFalse(container.Ok());

    }

    

    /**

     * Deberia de agregar de agujero mover las particulas correctamente.

     */

    @Test

    public void accordingPHRshouldMoveParticles() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        // Agregar particulas

        container.addParticle("red", true, 50, 50, 2, 1); 

        container.addParticle("blue", false, 50, 50, -1, 2); 

        container.makeInvisible();

        // Mover las particulas durante 100 ticks

        container.start(100);

        assertTrue(container.Ok());

    } 

    

     /**

     * No cumple las condiciones deseadas.

     */

    @Test

    public void accordingPHRshouldGoalFalse() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        // Agregar elementos al contenedor 

        container.addParticle("red", true, 10, 10, 1, 1);  

        container.addParticle("blue", false, 10, 10, 1, 1);

        container.makeInvisible();

        assertFalse(container.isGoal());

    }

    

    /**

     * Si cumple las condiciones deseadas.

     */

    @Test

    public void accordingPHRshouldGoalTrue() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        // Agregar elementos al contenedor 

        container.addParticle("red", true, -100, 10, 1, 1);  

        container.addParticle("blue", false, 110, 10, 1, 1);

        container.makeInvisible();

        assertTrue(container.isGoal());

    }

    

    /**

     * Deberia de hacer el contenedor visible.

     */

    @Test

    public void accordingPHRshouldMakeVisible() {

      MaxwellContainer container = new MaxwellContainer(100, 100);

      container.makeInvisible();

      // Agregar elementos al contenedor

      container.addParticle("red", true, 20, 50, 1, 1); 

      container.addParticle("blue", false, 50, 50, 1, 1); 

      container.addHole(75, 75, 2); 

      container.addDemon(50); 

      container.makeVisible();

    }

    

    /**

     * Deberia de hacer el contenedor invisible.

     */

    @Test

    public void accordingPHRshouldMakeInvisible() {

      MaxwellContainer container = new MaxwellContainer(100, 100);

      // Agregar elementos al contenedor

      container.addParticle("red", true, 20, 50, 1, 1); 

      container.addParticle("blue", false, 50, 50, 1, 1); 

      container.addHole(75, 75, 2); 

      container.addDemon(50);

      container.makeInvisible();

    }

    

    /**

     * Deberia de terminar con la simulacion.

     */

    @Test

    public void accordingPHRshouldFinishSimulation() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.addParticle("red", true, 50, 50, 2, 1); 

        container.addParticle("red", true, 80, 50, 2, 1); 

        container.addParticle("blue", false, 50, 20, -1, 2); 

        container.addHole(120, 10, 2);

        container.addHole(10, 10, 2);

        container.start(10);

        container.finish();

        assertTrue(container.Ok());

    }

    

    /**

     * Deberia de dar las posiciones de los demonios ordenadas de menor a mayor.

     */

    @Test

    public void accordingPHRshouldGetDemons() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.makeVisible();

        container.addDemon(50);

        container.addDemon(20);

        container.addDemon(80);

        container.makeInvisible();

        // Array esperado con las posiciones de los demonios ordenadas

        int[] expectedDemons = {20, 50, 80};

        assertArrayEquals(expectedDemons, container.demons());

        assertTrue(container.Ok());

    }

    

    /**

     * Deberia de dar las posiciones de las particulas de menor a mayor.

     */

    @Test

    public void accordingPHRshouldGetParticles() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.addParticle("red", true, 30, 40, 1, 1);

        container.addParticle("blue", false, 80, 20, -1, 0);

        container.addParticle("red", true, 10, 50, 0, -1);

        container.addParticle("blue", false, 70, 30, 2, 1);

        container.makeInvisible();

        int[][] expectedParticles = {

            {70,30,2,1},

            {80,20,-1,0},

            {110,50,0,-1},

            {130,40,1,1}

        };

        assertArrayEquals(expectedParticles, container.particles());

        assertTrue(container.Ok());

    }

    

    /**

     * Deberia de dar las posiciones de los huecos de menor a mayor.

     */

    @Test

    public void accordingPHRshouldGetHoles() {

        MaxwellContainer container = new MaxwellContainer(100, 100);

        container.addHole(50, 50, 3);

        container.addHole(20, 30, 5);

        container.addHole(80, 10, 2);

        container.makeInvisible();

        int[][] expectedHoles = {

            {20,30,5},

            {50,50,3},

            {80,10,2},

        };

        assertArrayEquals(expectedHoles, container.holes()); 

        assertTrue(container.Ok());

    }

    

    /**

     * Deberia de crear una simulacion con los datos dados.

     */

    @Test

    public void accordingPHRshouldCreateSimulationGivenInfo() {

        int h = 100;

        int w = 100;

        int d = 50; // Posicin del demonio

        int r = 2;  // partículas rojas

        int b = 1;  // partícula azul

        int[][] particles = {

            {10, 20, 2, 4}, 

            {30, 40, -2, 3}, 

            {10, 50, 2, -3} 

        };

        MaxwellContainer container = new MaxwellContainer(h, w, d, r, b, particles);

        container.makeInvisible();

        assertTrue(container.Ok());

    }

    

    /**

     * Tears down the test fixture.

     *

     * Called after every test case method.

     */

    @AfterEach

    public void tearDown()

    {

    }

    //HernandezSelma
   @Test
   public void deberiaCrearContenedorConDimensionesValidas() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       assertTrue(container.Ok(), "El contenedor debería crearse correctamente con dimensiones válidas.");
   } //PREGUNTAR: Dice que hubo un fallo peor al intentarlo en BlueJ funciona correctamente
    @Test
   public void noDeberiaCrearContenedorConDimensionesInvalidas() {
       MaxwellContainer container = new MaxwellContainer(201, 201);
       assertFalse(container.Ok(), "El contenedor no debería crearse con dimensiones inválidas.");
   }
    // Pruebas para el método create
   @Test
   public void deberiaEstablecerDimensionesValidasConCreate() {
       MaxwellContainer container = new MaxwellContainer(500, 100);
   //container.create
       assertTrue(container.Ok(), "El método create debería establecer dimensiones válidas.");
   }//PREGUNTAR: Dice que hubo un fallo peor al intentarlo en BlueJ funciona correctamente
    @Test
   public void noDeberiaEstablecerDimensionesInvalidasConCreate() {
       MaxwellContainer container = new MaxwellContainer(500, 500);
   //container.create
       assertFalse(container.Ok(), "El método create no debería establecer dimensiones inválidas.");
   }
    // Pruebas para el método addDemon
   @Test
   public void deberiaAgregarDemonioEnPosicionValida() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.addDemon(50);
       assertTrue(container.Ok(), "El demonio debería añadirse correctamente en una posición válida.");
   }
    @Test
   public void deberiaAsignarDemonioAPosicionCeroSiEsInvalida() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.addDemon(-10);
       assertFalse(container.Ok(), "El demonio no debería añadirse en una posición inválida.");
   }
    // Pruebas para el método delDemon
   @Test
   public void deberiaEliminarDemonioSiEstaPresente() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.addDemon(50);
       container.delDemon(50); // no fue dado argumento
       assertTrue(container.Ok(), "El demonio debería eliminarse correctamente si está presente.");
   }
    @Test
   public void noDeberiaEliminarDemonioSiNoEstaPresente() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.delDemon(100);
       assertFalse(container.Ok(), "No debería eliminarse un demonio si no está presente.");
   }
    // Pruebas para el método addParticle
   @Test
   public void deberiaAgregarParticulaRojaConParametrosValidos() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.addParticle("red",true, 10, 10, 1, 1);
       assertTrue(container.Ok(), "La partícula roja debería añadirse correctamente con parámetros válidos.");
   }
    @Test
   public void noDeberiaAgregarParticulaConParametrosInvalidos() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.addParticle("red",true, 0, 0, 0, 0);
       assertFalse(container.Ok(), "No debería añadirse una partícula con parámetros inválidos.");
   }
    // Pruebas para el método delParticle
   @Test
   public void deberiaEliminarParticulasRojasSiEstanPresentes() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.addParticle("red",true, 10, 10, 1, 1);
       container.delParticle("red");
       assertTrue(container.Ok(), "Las partículas rojas deberían eliminarse correctamente si están presentes.");
   }
    @Test
   public void noDeberiaEliminarParticulasSiColorEsInvalido() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.addParticle("red",true, 10, 10, 1, 1);
       container.delParticle("green");
       assertFalse(container.Ok(), "No debería eliminarse ninguna partícula si el color es inválido.");
   }
    // Pruebas para el método addHole
   @Test
   public void deberiaAgregarAgujeroNegroConParametrosValidos() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.addHole(10, 10, 5);
       assertTrue(container.Ok(), "El agujero negro debería añadirse correctamente con parámetros válidos.");
   }
    @Test
   public void noDeberiaAgregarAgujeroNegroConParametrosInvalidos() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.addHole(200, 200, -5);
       assertFalse(container.Ok(), "No debería añadirse un agujero negro con parámetros inválidos.");
   }

    // Pruebas para el método finish
   @Test
   public void deberiaFinalizarSimuladorSiEsVisible() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.makeVisible();
       container.finish();
       assertTrue(container.Ok(), "El simulador debería finalizar correctamente si está visible.");
   }
    @Test
   public void noDeberiaFinalizarSimuladorSiNoEsVisible() {
       MaxwellContainer container = new MaxwellContainer(100, 100);
       container.finish();
       assertFalse(container.Ok(), "El simulador no debería finalizar si no está visible.");
   }
   
    @Test
   public void accordingANShouldRemoveDemon() {
       MaxwellContainer container = new MaxwellContainer(150,150);
       container.makeInvisible();
       container.delDemon(50); 
       assertEquals(0, container.demons().length); // falla debido a que la declaré como null
       assertTrue(container.Ok()); 
   }
   
   @Test
   public void accordingANShouldNotRemoveDemon() {
       MaxwellContainer container = new MaxwellContainer(150,150);
       container.makeInvisible();
       container.addDemon(80); //añadidura
       container.delDemon(100);  
       assertEquals(1, container.demons().length); //falla debido a que no habia ningun demonio en la lista entonces se añade un demonio
       assertFalse(container.Ok()); 
   }
   
   @Test
   public void accordingANshouldAddDemon() {
       MaxwellContainer container = new MaxwellContainer(150,150);
       container.makeInvisible();
       container.addDemon(50);
       assertEquals(1, container.demons().length);
   }
    @Test
   public void accordingANshouldAddParticle() {
       MaxwellContainer container = new MaxwellContainer(150,150);
       container.makeInvisible();
       container.addParticle("particle1", true, 10, 10, 1, 1);
       assertEquals(6, container.particles().length);
   }
    @Test
   public void accordingANshouldNotAddParticle() {
       MaxwellContainer container = new MaxwellContainer(150,150);
       container.makeInvisible();
       container.addParticle("particle2", false, -10, -10, 1, 1);
       assertEquals(5, container.particles().length);
   }
    @Test
   public void accordingANshouldAddHole() {
       MaxwellContainer container = new MaxwellContainer(150,150);
       container.makeInvisible();
       container.addHole(30, 40, 5);
       assertEquals(1, container.holes().length);
   }
   /**
    * Prueba la eliminacion de una particula.
    * Verifica que la partícula se elimina correctamente y que ya no esté presente en el contenedor de partículas.
    */
   @Test
    public void accordingPRshouldDeleteParticle() {
    MaxwellContainer container = new MaxwellContainer(150,150);
    // Primero, agregamos una partícula con color "rojo"
    container.addParticle("red", true, 1, 1, 1, 1);
    assertEquals(1, container.particles().length);

    // Borramos la partícula por color
    container.delParticle("red");
    assertEquals(0, container.particles().length,
    "La partícula 'red' debería haberse eliminado");
    }
    /**
     * Prueba la adición de un Hole al contenedor.
    * Verifica que el Hole se agregue correctamente y que esté presente en la lista de Holes.
    */
    @Test
    public void accordingPRshouldAddHole() {
        MaxwellContainer container = new MaxwellContainer(150,150);
        // Verificamos que no haya holes inicialmente
        assertEquals(0, container.holes().length);
    
        // Agregamos un hole
        container.addHole(5, 5, 10);
    
        // Verificamos que ahora haya 1
        assertEquals(1, container.holes().length);
        // Comprobamos sus atributos
        assertEquals(5, container.holes()[0][0],
        "El Px del primer hole debería ser 5");
        assertEquals(5, container.holes()[0][1],
       "El Py del primer hole debería ser 5");
        assertEquals(10, container.holes()[0][2],
        "El número de partículas que puede comer debería ser 10"); //falla debido a que no hay los agujeros necesarios en la lista
    }

   /**
     * prueba que la simulación funcione por una cantidad  de ticks.
    * Verifica que la inicializacion de la simulacion y la contabilidad de los ticks de la misma.
    */
    @Test
    public void accordingPRshouldStartSimulation() {
    MaxwellContainer container = new MaxwellContainer(150,150);
    container.addParticle("red", true, 0, 0, 1, 1); //rojo en vez de red
    container.addParticle("blue", false, 1, 1, 1, 1);
    container.start(5);
    assertTrue(true, "No ha arrojado excepción al simular 5 ticks");
} 

@Test

    public void shouldAddParticle() {
        MaxwellContainer container = new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

            {50, 50, 1, 1, 0}, 

            {150, 150, -1, -1, 1}

        });

        container.addParticle("blue", false, 30, 30, 1, 1);

        container.addParticle("red", true, 150, 150, -1, -1);

        assertEquals(4, container.particles().length, "Debe haber 4 partículas en total");

    }



    @Test

    public void shouldAddHole() {
        MaxwellContainer container = new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

            {50, 50, 1, 1, 0}, 

            {150, 150, -1, -1, 1}

        });

        container.addHole(30, 30, 1);

        assertEquals(1, container.holes().length, "Debe haber 1 agujero");

    }



    @Test

    public void shouldMoveParticles() {
        MaxwellContainer container = new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

            {50, 50, 1, 1, 0}, 

            {150, 150, -1, -1, 1}

        });

        int[][] initialPositions = container.particles();

        container.start(5);

        int[][] newPositions = container.particles();

        assertNotEquals(initialPositions[0][0], newPositions[0][0], "Las partículas deben haberse movido");

    }



    @Test

    public void shouldAbsorbParticlesWithHoles() {
        MaxwellContainer container = new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

            {50, 50, 1, 1, 0}, 

            {150, 150, -1, -1, 1}

        });

        container.addHole(50, 50, 5);

        int initialCount = container.particles().length;

        container.start(10);

        assertTrue(container.particles().length < initialCount, "Las partículas deberían haber sido absorbidas");

    }



    @Test

    public void shouldBounceOffWalls() {
        MaxwellContainer container = new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

            {50, 50, 1, 1, 0}, 

            {150, 150, -1, -1, 1}

        });

        container.addParticle("blue", false, 195, 195, 5, 5);

        container.start(1);

        int[][] positions = container.particles();

        assertTrue(positions[positions.length - 1][2] < 0 || positions[positions.length - 1][3] < 0, 

                   "La partícula debería haber rebotado en la pared");

    }



    @Test

    public void noShouldCreateContainerWithInvalidParticles() {
        MaxwellContainer container = new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

            {50, 50, 1, 1, 0}, 

            {150, 150, -1, -1, 1}

        });

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {

            new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

                {2, 2, 1, 1, 1},  // Roja en posición incorrecta

                {200, 200, -1, -1, 0} // Azul en posición incorrecta

            });

        });

        assertEquals("ERROR: Contenedor no creado porque hay partículas en posiciones inválidas.", exception.getMessage());

    }



    @Test

    public void noShouldAddParticleOutOfBounds() {
        MaxwellContainer container = new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

            {50, 50, 1, 1, 0}, 

            {150, 150, -1, -1, 1}

        });

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {

            container.addParticle("blue", false, 250, 250, 1, 1);

        });

        assertEquals("La partícula está fuera de los límites", exception.getMessage());

    }



    @Test

    public void noShouldAddHoleOutOfBounds() {
        MaxwellContainer container = new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

            {50, 50, 1, 1, 0}, 

            {150, 150, -1, -1, 1}

        });

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {

            container.addHole(250, 250, 5);

        });

        assertEquals("El agujero está fuera de los límites", exception.getMessage());

    }



    @Test

    public void noShouldAbsorbParticlesWithoutHoles() {
        MaxwellContainer container = new MaxwellContainer(200, 200, 2, 1, 1, new int[][]{

            {50, 50, 1, 1, 0}, 

            {150, 150, -1, -1, 1}

        });

        int initialCount = container.particles().length;

        container.start(10);

        assertEquals(initialCount, container.particles().length, "No debería haber partículas absorbidas sin agujeros");

    }


}

   

