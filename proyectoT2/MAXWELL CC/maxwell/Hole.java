package maxwell;


/**
 * Write a description of class Hole here.
 * 
 * @author Carolina Cepeda
 * @version 29  de marzo
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
     * @param px Posicion en x
     * @param py Posicion  en y
     * @param particles Cantidad de particulas
     * @param w Ancho del contenedor
     * @param h Alto del contenedor
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

    /**
     * Metodo para revisar si la particula cae en el agujero
     * @param p
     * @return boolean
     */
    public boolean pasar(Particle p) {
        if(particles>0){
            if (p.EstoyAhi(this.originalX, this.originalY)) {
                particles--;
                return true;
            }
        }
        return false;
    }
/**
 * metodo para hacer visible al agujero
 */
    public void makeVisible() {
        grafico.makeVisible();
        esVisible = true;
    }
    /**
     * metodo para hacer invisible al agujero
     */
    public void makeInvisible() {
        grafico.makeInvisible();
        esVisible = false;
    }

    /**
     * Metodo para obtener la posicion en Y del agujero
     * @return int
     */
    public int getpY() {
        return originalY;

    }
    /**
     * Metodo para obtener la posicion en X del agujero
     * @return int
     */
    public int getpX() {
        return originalX;
    }
    /**
     * Metodo para obtener la cantidad de particulas que pueden colocarse en el agujero
     * @return int
     */
    public int getParticles() {
        return particles;
    }
    /**
     * metodo para verificar si el agujero está en la posicion dada
     * @param px : posicion en x
     * @param py : posicion en y
     * @return boolean
     */
    public boolean EstoyAhi(int px, int py) {
        return (px == originalX && py == originalY);
    }
    /**
     * metodo para retornar al agujero en un formato dado
     * @return int[] : arreglo con la posicion en x y y del agujero
     */
    public int[] format() {
        int[] info = new int[2];
        info[0] = originalX;
        info[1] = originalY;
        return info;
    }

}
