
/**
 * Write a description of class MaxwellContainer here.
 * 
 * @author Carolina Cepeda
 * @version 4/03/2025
 */
public class MaxwellContainer {
    private int demon;
    private Particle particle;
    private Tablero tablero;
    private boolean isOk;

    public MaxwellContainer(int h, int w) {

        tablero = new Tablero(h, w);

    }

    public MaxwellContainer(int h, int w, int d, int b, int r, int[][] particles) {

        tablero = new Tablero(h, w);

        tablero.addDemon(d);
    }

    public void addDemon(int d) {
        tablero.addDemon(d);
    }

    public void addParticle(String color, boolean isRed, int px, int py, int vx, int vy) {

        tablero.addParticle(color, isRed, px, py, vx, vy);
    }

    public void addHole(int px, int py, int particles) {

        tablero.addHole(px, py, particles);
    }

    public void delParticle(String color) {

    }

    public void delDemon(int d) {
        tablero.delDemon(d);

    }

    public void start(int ticks) {
        tablero.start(ticks);
    }

    public boolean isGoal() {
        return tablero.isGoal();
    }

    public int[] demons() {
        return tablero.demons();
    }

    public int[][] particles() {
        return tablero.particles();
    }

    public int[][] holes() {
        return tablero.holes();
    }

    public void makeVisible() {
        tablero.makeVisible();

    }

    public void makeInvisible() {

        tablero.makeInvisible();
    }

    public void finish() {

        tablero.finish();
    }

    public boolean ok() {
        return false;
    }

}
