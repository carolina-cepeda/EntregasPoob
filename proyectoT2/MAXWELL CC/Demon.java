


/**
 * Demon of the maxwell container
 * 
 * @author Carolina Cepeda
 * @version 25/03/25
 */
public class Demon {
    private Triangle grafico;
    private int pX;
    private int pY;
    private int originalX;
    private int originalY;
    private boolean esVisible = false;

    /**
     * Constructor for objects of class Demon
     */
    public Demon(int w,int h, int pY, String color) {
        this.esVisible = MaxwellContainer.getVisible();
        this.originalX = 0;
        this.originalY = pY;
        this.pX = (70 + w / 2);
        this.pY =  (15 + h) - originalY;
        grafico = new Triangle();
        grafico.changeSize(10, 10);
        grafico.changeColor(color);
        grafico.setXposition(pX);
        grafico.setYposition(pY);

    }

    public void deleteDemon() {
        grafico.makeInvisible();
    }

    public boolean pasar(Particle p) {
        if (p.EstoyAhi(this.pX,this.pY)) {
            p.pasar();
            return true;
        }
        return false;
    }

    public void makeVisible() {
        grafico.makeVisible();
        esVisible = true;
    }

    public void makeInvisible() {
        grafico.makeInvisible();
        esVisible = false;
    }

    public int getpY() {
        return originalY;
    }

    public boolean EstoyAhi(int d) {
        return d == pY;
    }
}
