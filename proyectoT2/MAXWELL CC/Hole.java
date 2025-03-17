
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
    private boolean esVisible = false;

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
        esVisible = true;
    }

    public void makeInvisible() {
        grafico.makeInvisible();
        esVisible = false;
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

    public boolean EstoyAhi(int px, int py) {
        return (px == positionX && py == positionY);
    }

    public int[] format() {
        int[] info = new int[2];
        info[0] = positionX;
        info[1] = positionY;
        return info;
    }

}
