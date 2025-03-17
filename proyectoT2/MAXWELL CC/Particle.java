
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
    private boolean isRed;
    private boolean esVisible;

    /**
     * Constructor for objects of class Particle
     */
    public Particle(String color, boolean isRed, int x, int y, int vX, int vY) {
        this.esVisible = MaxwellContainer.getVisible();
        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;
        this.isRed = isRed;
        grafico = new Circle();
        grafico.changeSize(8);
        grafico.changeColor(color);
        grafico.setXposition(x);
        grafico.setYposition(y);

    }

    /*
     * metodo para hacer visible la particula
     */
    public void makeVisible() {
        grafico.makeVisible();
        esVisible = true;
    }

    /*
     * metodo para hacer invisible la particula
     */
    public void makeInvisible() {
        grafico.makeInvisible();
        esVisible = false;
    }

    /*
     * metodo para verificar que las particulas rojas esten
     * en el lado correcto del juego.
     */
    public boolean isGoalR(int h, int w) {
        int maxX = (70 + (w / 2));
        return (70 <= x && x <= maxX);
    }

    /*
     * metodo para verificar que las particulas azules esten
     * en el lado correcto del juego.
     */
    public boolean isGoalB(int h, int w) {
        int minX = (70 + (w / 2));
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

    /*
     * Metodo para mover en una direccion aleatoria en y a la particula
     * basado en sus velocidades en x y y.
     */
    public void moveV() {
        Random random = new Random();
        int[] options = { -1, 0, 1 };
        y += vY;

        if ("red".equals(color)) {
            x -= vX;
            moveSlow(-vX, vY);
        }

        else {
            x += vX;
            moveSlow(vX, vY);
        }

        if (esVisible) {
            makeVisible();
        }

    }

    public void moveSlow(int vx, int vy) {
        makeInvisible();
        grafico.moveHorizontal(vX);
        grafico.moveVertical(vy);
        makeVisible();
    }

    public void setpX(int dado) {
        this.x = dado;
    }

    public void setpY(int dado) {
        this.y = dado;
    }

    public boolean EstoyAhi(int px, int py) {
        return (x == px && y == py);
    }

    public int[] format() {
        int[] info = new int[4];
        info[0] = x;
        info[1] = y;
        info[2] = vX;
        info[3] = vY;
        return info;
    }

    public void pasar(int h, int w) {
        if (isRed) {
            pasarRojo(h, w);
        } else {
            pasarBlue(h, w);
        }
        if (esVisible) {
            makeVisible();
        }
    }

    /*
     * Metodo para pasar las particulas rojas al lado correcto.
     */
    public void pasarRojo(int h, int w) {
        Random random = new Random();
        int esperadoX = random.nextInt(((w + 62) - 69) / 2) + 70; // Rango [70, w+62 /2]
        int esperadoY = random.nextInt((h + 7) - 14) + 15;

        grafico.slowMoveHorizontal(esperadoX - x);
        grafico.slowMoveVertical(esperadoY - y);
        this.x = esperadoX - x;
        this.y = esperadoY - y;

    }
    /*
     * Metodo para pasar las particulas azules al lado correcto.
     */

    public void pasarBlue(int h, int w) {

        Random random = new Random();
        int esperadoX = random.nextInt(w + 62) + (((w + 62) - 69) / 2); // Rango [70, w+62 /2]
        int esperadoY = random.nextInt((h + 7) - 14) + 15;

        grafico.slowMoveHorizontal(esperadoX - x);
        grafico.slowMoveVertical(esperadoY - y);

        this.x = esperadoX - x;
        this.y = esperadoY - y;
    }

}
