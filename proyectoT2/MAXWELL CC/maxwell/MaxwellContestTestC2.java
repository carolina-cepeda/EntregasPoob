package maxwell;


import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MaxwellContainerC2Test.
 *
 * @author Carolina cepeda
 * @version 21/03/25
 */
public class MaxwellContestTestC2 {

    @Test
    /*
     * problema de la maratón numero 1
     */
    public void shouldSolve() {
        int w = 7, h = 4, d = 1, r = 1, b = 1;
        int[][] particles1 = {
                { 2, 1, 4, 1 },
                { -3, 1, 2, 0 }
        };
        double solucionDada = MaxwellContest.solve(h, w, d, r, b, particles1);
        assertTrue(solucionDada == 24.0);
    }
    
    @Test
    public void shouldNotSolveCC() {
        int w = 7, h = 0, d = 0, r = 1, b = 1;
        int[][] particles1 = {
                { 2, 1, 4, 1 },
                { -3, 1, 2, 0 }
        };
        double solucionDada = MaxwellContest.solve(h, w, d, r, b, particles1);
        assertTrue(solucionDada == -1.0);
    }
    @Test
    public void shouldNotSimulateCC() {
        int w = 7, h = 0, d = 0, r = 1, b = 1;
        int[][] particles1 = {
                { 2, 1, 4, 1 },
                { -3, 1, 2, 0 }
        };
        double solucionDada = MaxwellContest.solve(h, w, d, r, b, particles1);
        assertTrue(solucionDada == -1.0);
        MaxwellContest maxwellContest = new MaxwellContest(h,w,d,b,r,particles1);
        maxwellContest.simulate(h, w, d, b, r, particles1);
    }

    @Test
    /*
    * problema de la maraton numero 2
    */
    public void shouldNotSolve() {
        int w = 4, h = 4, d = 1, r = 2, b = 2;
        int[][] particles2 = {
                { 3, 1, 2, 2 },
                { -2, 3, -2, -1 },
                { 3, 2, 1, -2 },
                { -2, 2, 2, 2 }
        };
        double solucionDada = MaxwellContest.solve(h, w, d, r, b, particles2);
        assertTrue(solucionDada == -1.0);

    }


    /**
     * Soluciona el problema de la maraton (caso alternativo).
     */
    @Test 
    public void accordingPHRshouldSolve2() {
        int w = 7;
        int h = 4;
        int d = 1;
        int r = 1;
        int b = 1;
        int[][] particles = { {2, 1, 4, 0}, {-3, 1, 2, 0} };
        double result = MaxwellContest.solve(h, w, d, b, r, particles);
        double expectedResult = 3;
        assertEquals(expectedResult, result);
    }


    /**
     * Inicia la simulación para el problema de la maraton.
     */
    @Test 
    public void accordingPHRshouldSimulate() {
        int w = 7;
        int h = 4;
        int d = 1;
        int r = 1;
        int b = 1;
        int[][] particles = { {2, 1, 4, 1}, {-3, 1, 2, 0} };
        double result = MaxwellContest.solve(h, w, d, b, r, particles);
        double expectedResult = 24;
        MaxwellContest maxwellContest = new MaxwellContest(h,w,d,b,r,particles);
        maxwellContest.simulate(h, w, d, b, r, particles);
        assertEquals(expectedResult, result);
    }

    /**
     * Inicia la simulación con tamaño aumentado para mejor visualización.
     */
    @Test 
    public void accordingPHRshouldSimulate2() {
        int w = 70;
        int h = 40;
        int d = 10;
        int r = 1;
        int b = 1;
        int[][] particles = { {20, 10, 40, 10}, {30, 10, 20, 0} };
        double result = MaxwellContest.solve(h, w, d, b, r, particles);
        double expectedResult = 24;
        MaxwellContest maxwellContest = new MaxwellContest(h,w,d,b,r,particles);
        maxwellContest.simulate(h, w, d, b, r, particles);
        assertEquals(expectedResult, result);
    }

    /**
     * No inicia la simulación para el problema de la maraton (caso negativo).
     */
    @Test 
    public void accordingPHRshouldNotSimulate() {
        int w = 4;
        int h = 4;
        int d = 1;
        int r = 2;
        int b = 2;
        int[][] particles = { {3, 1, 2, 2}, {-2, 3, -2, -1}, {3, 2, 1, -2}, {-2, 2, 2, 2} };
        double result = MaxwellContest.solve(h, w, d, b, r, particles);
        double expectedResult = -1;
        MaxwellContest maxwellContest = new MaxwellContest(h,w,d,b,r,particles);
        maxwellContest.simulate(h, w, d, b, r, particles);
        assertEquals(expectedResult, result);
    }
    
}
