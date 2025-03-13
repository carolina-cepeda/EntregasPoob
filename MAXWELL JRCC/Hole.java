
/**
 * Write a description of class Hole here.
 * 
 * @author Carolina Cepeda
 * @version 10 de marzo
 */
public class Hole {
    private int positionX;
    private int positionY;
    private Circle grafico;
    private int particles;

    /**
     * Constructor for objects of class Hole
     */
    public Hole(int px, int py, int particles) {
        grafico = new Circle();
        grafico.changeColor("black");
        grafico.setXposition(positionX);
        grafico.setYposition(positionY);

        this.particles = particles;

    }

    public void makeVisible() {
        grafico.makeVisible();
    }

    public void makeInvisible() {
        grafico.makeInvisible();
    }

    // getPosition
    public int getpY() {
        return grafico.getYposition();

    }

    public int getpX() {
        return grafico.getXposition();
    }

}
