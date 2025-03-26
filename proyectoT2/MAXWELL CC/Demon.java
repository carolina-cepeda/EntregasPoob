
/**
 * Write a description of class Demon here.
 * 
 * @author Carolina Cepeda
 * @version 10/03/25
 */
public class Demon {
    private Triangle grafico;
    private int pX;
    private int pY;
    private boolean esVisible = false;

    /**
     * Constructor for objects of class Demon
     */
    public Demon(int pX, int pY, String color) {
        this.esVisible = MaxwellContainer.getVisible();
        this.pX = pX;
        this.pY = pY;
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
        }
        return (p.EstoyAhi(this.pX, this.pY));
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
        return pY;
    }

    public boolean EstoyAhi(int d) {
        return d == pY;
    }
}
