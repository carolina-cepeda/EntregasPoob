
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
}
