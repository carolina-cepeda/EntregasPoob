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

    public void tearDown() {
        ciudad = null;
        
    }
}
