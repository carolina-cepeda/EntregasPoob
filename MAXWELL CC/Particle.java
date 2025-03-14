
import java.util.Random;

/**
 * Write a description of class Particle here.
 * 
 * @author Carolina Cepeda
 * @version 10/03/25
 */
public class Particle {
    private String color;
    private int x;
    private int y;
    private int vX;
    private int vY;
    private Circle grafico;

    /**
     * Constructor for objects of class Particle
     */
    public Particle(String color, int x, int y, int vX, int vY) {
        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;

        grafico = new Circle();
        grafico.changeSize(8);
        grafico.changeColor(color);
        grafico.setXposition(x);
        grafico.setYposition(y);

    }

    public void makeVisible() {
        grafico.makeVisible();
    }

    public void makeInvisible() {
        grafico.makeInvisible();
    }

    public void pasarRojo(int h, int w) {
        Random random = new Random();
        int esperadoX = random.nextInt(((w + 62) - 69) / 2) + 70; // Rango [70, w+62 /2]
        int esperadoY = random.nextInt((h + 7) - 14) + 15;

        grafico.slowMoveHorizontal(esperadoX - x);
        grafico.slowMoveVertical(esperadoY - y);
        this.x = esperadoX - x;
        this.y = esperadoY - y;

    }

    public void pasarBlue(int h, int w) {

        Random random = new Random();
        int esperadoX = random.nextInt(w + 62) + (((w + 62) - 69) / 2); // Rango [70, w+62 /2]
        int esperadoY = random.nextInt((h + 7) - 14) + 15;

        grafico.slowMoveHorizontal(esperadoX - x);
        grafico.slowMoveVertical(esperadoY - y);

        this.x = esperadoX - x;
        this.y = esperadoY - y;
    }

    public boolean isGoalR(int h, int w) {
        int maxX = ((w + 70) / 2);
        return (70 <= x && x <= maxX);
    }

    public boolean isGoalB(int h, int w) {
        int minX = ((w + 70) / 2);
        int maxX = w + 70;
        return (minX <= x && x <= maxX);
    }

    public int getpX() {
        return this.x;
    }

    public int getpY() {
        return this.y;
    }

    public int getvX() {
        return this.vX;
    }

    public int getvY() {
        return this.vY;
    }

    public void moveV() {
        Random random = new Random();
        int[] options = { -1, 0, 1 };
        int multX = options[random.nextInt(options.length)];
        int multY = options[random.nextInt(options.length)];
        x += (vX * multX);
        y += (vY * multY);
    }

    public void setpX(int dado) {
        this.x = dado;
    }

    public void setpY(int dado) {
        this.y = dado;
    }

}
