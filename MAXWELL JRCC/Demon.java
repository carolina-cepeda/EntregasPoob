
/**
 * Write a description of class Demon here.
 * 
 * @author Carolina Cepeda
 * @version 10/03/25
 */
public class Demon {
    private Triangle grafico;

    /**
     * Constructor for objects of class Demon
     */
    public Demon(int pX, int pY, String color) {
        grafico = new Triangle();
        grafico.changeColor(color);
        grafico.setXposition(pX);
        grafico.setYposition(pY);

    }

    public void deleteDemon() {
        grafico.makeInvisible();
    }

    public boolean pasar(Particle p) {
        int posx = p.getpX();
        int posy = p.getpY();
        int posDX = grafico.getXposition();
        int posDY = grafico.getYposition();
        return (posx == posDX && posy == posDY);
    }

    public void makeVisible() {
        grafico.makeVisible();
    }

    public void makeInvisible() {
        grafico.makeInvisible();
    }

    public int getpY() {
        return grafico.getYposition();
    }
}
