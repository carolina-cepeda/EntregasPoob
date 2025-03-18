
/**
 * Write a description of class capsulaSubmarina here.
 * 
 * @author Carolina Cepeda
 * @version 18/03
 */
public class capsulaSubmarina extends Machine {
    private int profundidad;

    /**
     * Constructor for objects of class capsulaSubmarina
     */
    public capsulaSubmarina() {
        super();

    }

    @Override
    public boolean weakMachines() {
        return false;
    }

    @Override
    public boolean estoyAhi(int longitude, int latitude) {
        return false;
    }

}
