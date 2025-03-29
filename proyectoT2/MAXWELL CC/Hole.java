
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

        originalX = px;
        originalY = py;
        positionX = originalX + (70+w) / 2;
        positionY =(15+h)- originalY;

        grafico = new Circle();
        grafico.changeSize(15);
        grafico.changeColor("black");
        grafico.setXposition(positionX);
        grafico.setYposition(positionY);

        this.particles = particles;

    }

    public boolean pasar(Particle p) {
        return (p.EstoyAhi(originalX, originalY));
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
        return originalY;

    }

    public int getpX() {
        return originalX;
    }

    public int getParticles() {
        return particles;
    }

    public boolean EstoyAhi(int px, int py) {
        return (px == originalX && py == originalY);
    }

    public int[] format() {
        int[] info = new int[2];
        info[0] = originalX;
        info[1] = originalY;
        return info;
    }

}
