import java.awt.*;
import java.util.ArrayList;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 1.0 (15 July 2000)
 */

public class Triangle {

    public final int VERTICES = 3;

    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    private ArrayList<Triangle> triangles = new ArrayList<>();

    /**
     * Create a new triangle at default position with default color.
     * 
     * public Triangle(){
     * height = 30;
     * width = 40;
     * xPosition = 140;
     * yPosition = 15;
     * color = "green";
     * isVisible = false;
     * }
     */
    /**
     * Constructor de un triángulo predeterminado en una posición x, y dada
     * de su área.
     * 
     * @param (int x , int y)
     * @return void
     */

    public Triangle(int x, int y) {
        height = 30;
        width = 40;
        xPosition = x;
        yPosition = y;
        color = "green";
        isVisible = false;
    }

    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Move the triangle a few pixels to the right.
     */
    public void moveRight() {
        moveHorizontal(20);
    }

    /**
     * Move the triangle a few pixels to the left.
     */
    public void moveLeft() {
        moveHorizontal(-20);
    }

    /**
     * Move the triangle a few pixels up.
     */
    public void moveUp() {
        moveVertical(-20);
    }

    /**
     * Move the triangle a few pixels down.
     */
    public void moveDown() {
        moveVertical(20);
    }

    /**
     * Move the triangle horizontally.
     * 
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the triangle vertically.
     * 
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the triangle horizontally.
     * 
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance) {
        int delta;

        if (distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for (int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the triangle vertically.
     * 
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance) {
        int delta;

        if (distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for (int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size to the new size
     * 
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht  the new width in pixels. newWidht must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    /**
     * Change the color.
     * 
     * @param color the new color. Valid colors are "red", "yellow", "blue",
     *              "green",
     * 
     *              "magenta" and "black".
     */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    /*
     * Draw the triangle with current specifications on screen.
     */
    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width / 2), xPosition - (width / 2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
            for (Triangle triangle : triangles) {
                triangle.draw();
            }
        }
    }

    /*
     * Erase the triangle on screen.
     */
    private void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
            for (Triangle triangle : triangles) {
                canvas.erase(triangle);
            }
        }
    }

    /**
     * Calcula el área del triángulo
     *
     * @param (int height, int width)
     * @return int
     */
    public int area(int height, int width) {
        return ((width * height) / 2);
    }

    /**
     * Transforma un triágulo a equilatero manteniendo el valor
     * de su área.
     * 
     * @param ()
     * @return void
     */
    public void equilateral() {
        int areaValue = area(height, width);
        // Area triangulo_equilatero= √(4 * área / √3)
        double ladoequi = Math.sqrt(4 * areaValue / Math.sqrt(3));
        int ladointequi = (int) Math.round(ladoequi);
        changeSize(ladointequi, ladointequi);
    }

    public void shrink(int times, int heightFinal) {
        Triangle base = this;
        int dif = height - heightFinal;
        int partes = dif / times;
        int widthFinal = (width * heightFinal) / height;
        int difWidth = width - widthFinal;
        int widthPartes = difWidth / times;
        Triangle lastTriangle = new Triangle(xPosition + (width + 5), yPosition);
        lastTriangle.changeSize(height - partes, width - widthPartes);
        lastTriangle.makeVisible();
        this.triangles.add(lastTriangle);
        for (int j = 2; j <= times; j++) {
            Triangle newTriangle = new Triangle(xPosition + (j * (width + 1)), yPosition);
            this.triangles.add(newTriangle);
            newTriangle.changeSize(lastTriangle.height - partes, lastTriangle.width - widthPartes);
            lastTriangle = newTriangle;
            newTriangle.makeVisible();
        }
    }

    public int perimetroTriangle() {
        double hipotenusa = Math.sqrt(Math.pow(this.width, 2) + Math.pow(this.height, 2));
        return (int) (this.width + this.height + hipotenusa);
    }

}