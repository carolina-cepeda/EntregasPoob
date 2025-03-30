package maxwell;

public class Flying extends Particle {
    /**
     * constructor de flying particle
     * @param color color of the particle
     * @param isRed booleano de si la particula es roja o azul
     * @param x  posicion  x de la particula
     * @param y  posicion y de la particula
     * @param vx velocidad en x de la particula
     * @param vy velocidad en y de la particula
     * @param w ancho del contenedor
     * @param h alto del contenedor
     */
    public Flying(String color, boolean isRed, int x, int y, int vx, int vy,int w, int h) {
        super(color,isRed, x, y, vx, vy, w, h);
    }
    
}
