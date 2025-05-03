
import java.awt.geom.*;
import java.util.ArrayList;

/**
 *
 * 
 * Recipiente para guardar semilllas
 * 
 * @author Carolina Cepeda, Juanita Rubiano
 * @version 1.0
 */
public class Pit {
    private boolean big;
    private int semillas;
    private String bg_color;
    private String s_color;
    private boolean isVisible;
    private int xPosition;
    private int yPosition;
    private final int width;
    private final int height;
    private final int tamaño_s;
    private int tamaño_h;
    private Circle semilla;
    private ArrayList<String> referencias_semillas = new ArrayList<>();

    /**
     * Constructor de un marco de kalah predeterminado.
     */
    public Pit() {
        big = false;
        semillas = 0;
        bg_color = "white";
        s_color = "green";
        isVisible = true;
        xPosition = 30;
        yPosition = 30;
        width = 300;
        height = 300;
        tamaño_s = 10; // tamaño de la semilla
        tamaño_h = 100;
    }

    /**
     * -Hacer el hueco big ( un hueco del doble de tamaño que el predeterminado)
     */
    public void makeBig() {
        tamaño_h = tamaño_h * 2;
        big = true;
        draw();
    }

    /**
     * Adiciona semillas al marco.
     * 
     * @param seeds: int
     * @return void
     */
    public void putSeeds(int seeds) {
        semillas += seeds;
        draw();
    }

    /**
     * Elimina semillas del marco.
     * 
     * @param seeds: int
     * @return void
     */
    public void removeSeeds(int seeds) {
        erase();
        semillas -= seeds;
        draw();
    }

    /**
     * Muestre la cantidad de semillas en el juego actual.
     * 
     * @param ()
     * @return int
     */
    public int seeds() {
        return semillas;
    }

    /**
     * Cambie el color de las semillas y el fondo del juego.
     * 
     * @param color que se desea para el marco. color que se desdea para las
     *              semillas
     *              Los colores validos son "red", "yellow", "blue",
     *              "green","magenta" and "black".
     * @return void
     */
    public void changeColor(String background, String seeds) {
        bg_color = background;
        s_color = seeds;
        draw();
    }

    /**
     * Cambiar la posición del marco ( incluidas sus semillas)
     * 
     * @param int x, int y
     * @return int
     */

    public void moveTo(int x, int y) {
        erase();
        xPosition += x;
        yPosition += y;
        draw();
    }

    /**
     * Hace visible el marco y sus semillas
     * 
     * @param ()
     * @return void
     */
    public void makeVisible() {
        draw();
        isVisible = true;
    }

    /**
     * Hace invisible el marco y sus semillas
     * 
     * @param ()
     * @return void
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }
    /*
     * Draw the rectangle with current specifications on screen.
     */

    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();

            canvas.draw("Marco", "blue",
                    new java.awt.Rectangle(xPosition - 30, yPosition - 30,
                            width - 30, height - 30));

            canvas.draw("Hueco", bg_color,
                    new Ellipse2D.Double((xPosition), (yPosition),
                            tamaño_h, tamaño_h));

            for (int i = 0; i < semillas; i++) {
                int j = 0;

                if (big = true) {
                    j = 10;
                }
                String referencia = "semilla_" + this.toString() + i; 
                this.referencias_semillas.add(referencia);
                canvas.draw(referencia, s_color,
                        new Ellipse2D.Double((xPosition) + 10 + 10 * i + 2 * j, (yPosition) + 10 + 10 * i + 2 * j,
                                tamaño_s, tamaño_s));

            }
        }
    }

    /*
     * Erase the figure on screen.
     */
    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase("Marco");
            canvas.erase("Hueco");
            for (Object obj : this.referencias_semillas) {
                canvas.erase(obj);
            }
        }
    }

    public int ciclo1(int seeds_nuevas, int menos_seeds) {
        putSeeds(seeds_nuevas);
        removeSeeds(menos_seeds);
        return seeds();
    }

    public void ciclo2() {
        makeVisible();
        makeInvisible();
    }

    public void ciclo3(String pit, String seeds, int x, int y) {
        changeColor(pit, seeds);
        moveTo(x, y);
    }
}