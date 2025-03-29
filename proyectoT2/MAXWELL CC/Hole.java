
/**
 * Write a description of class Hole here.
 * 
 * @author Carolina Cepeda
 * @version 25 de marzo
 */
public class Hole {
    private int positionX;
    private int positionY;
    private int originalX;
    private int originalY;
    private Circle grafico;
    private int particles;
    private boolean esVisible = false;

    /**
     * Constructor for objects of class Hole
     */
    public Hole(int px, int py, int particles,int w, int h) {
        this.esVisible = MaxwellContainer.getVisible();

        positionX = px;
        positionY = py;
        originalX = px+ (70+w) / 2;
        originalY =(15+h)- py;

        grafico = new Circle();
        grafico.changeSize(15);
        grafico.changeColor("black");
        grafico.setXposition(px);
        grafico.setYposition(py);

        this.particles = particles;

    }

    public boolean pasar(Particle p) {
        return (p.EstoyAhi(positionX, positionY));
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
        return positionY;

    }

    public int getpX() {
        return positionX;
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
