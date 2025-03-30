package maxwell;




/**
 * Demon of the maxwell container
 * 
 * @author Carolina Cepeda
 * @version 29/03/25
 */
public class Demon {
    private Triangle grafico;
    private int pX;
    private int pY;
    private int originalX;
    private int originalY;
    private boolean esVisible = false;

    /**
     * Constructor for objects of class Demon
     * @param w Ancho del contenedor
     * @param h Alto del contenedor
     * @param pY Posicion inicial en y
     * @param color Color del demonio
     */
    public Demon(int w,int h, int pY, String color) {
        this.esVisible = MaxwellContainer.getVisible();
        this.originalX = 0;
        this.originalY = pY;
        this.pX = (70 + w / 2);
        this.pY =  (15 + h) - originalY;
        grafico = new Triangle();
        grafico.changeSize(10, 10);
        grafico.changeColor(color);
        grafico.setXposition(pX);
        grafico.setYposition(pY);

    }
    /**
     * Metodo para eliminar el demonio
     */
    public void deleteDemon() {
        grafico.makeInvisible();
    }

    /**
     * Metodo para verificar si la particula choca con el demonio
     * @param p Particula a verificar
     * @param w Ancho del contenedor
     * @param h Alto del contenedor
     * @return boolean
     */

    public boolean pasar(Particle p,int w, int h) {
        if (p.EstoyAhi(this.originalX,this.originalY)){
            p.pasar(w,h);
            return true;
        }
        return false;
    }
    /**
     * metodo para hacer visible al demonio
     */

    public void makeVisible() {
        grafico.makeVisible();
        esVisible = true;
    }
    /**
     * metodo para hacer invisible al demonio
     */
    public void makeInvisible() {
        grafico.makeInvisible();
        esVisible = false;
    }
    /**
     * Metodo para obtener la posicion en y del demonio
     * @return int
     */
    public int getpY() {
        return originalY;
    }
    /**
     * Metodo para verificar si el demonio está en la altura dada
     * @param d
     * @return boolean
     */
    public boolean EstoyAhi(int d) {
        return d == originalY;
    }
}
