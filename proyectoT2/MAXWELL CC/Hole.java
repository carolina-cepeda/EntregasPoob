
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
        positionX = px;
        positionY = py;
        grafico = new Circle();
        grafico.changeSize(15);
        grafico.changeColor("black");
        grafico.setXposition(px);
        grafico.setYposition(py);

        this.particles = particles;

    }

    public boolean pasar(Particle p) {
        int posx = p.getpX();
        int posy = p.getpY();
        int posHX = grafico.getXposition();
        int posHY = grafico.getYposition();
        return (posx == posHX && posy == posHY);
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

    public int getParticles() {
        return particles;
    }

}
