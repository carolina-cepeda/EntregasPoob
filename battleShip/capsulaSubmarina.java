
/**
 * Write a description of class capsulaSubmarina here.
 * 
 * @author Carolina Cepeda
 * @version 25/03
 */
public class capsulaSubmarina extends Ship {
    private int profundidad;
    private Ship nodriza;

    /**
     * Constructor for objects of class capsulaSubmarina
     */
    public capsulaSubmarina(int name) {
        super(name);

    }

    @Override
    public boolean weakMachines() {
        return false;
    }

    @Override
    public boolean estoyAhi(int longitude, int latitude) {
        return false;
    }

    public void setNodriza(Ship nodriza) {
        this.nodriza = nodriza;
    }

    public void verificarNodriza() {
        if (nodriza != null && nodriza.getCausaDestruccion() != null) {
            autoDestruir("la nave nodriza fue destruida");
        }
    }

}
