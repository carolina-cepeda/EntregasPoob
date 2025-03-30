package maxwell;
/**
 * Ephemeral particle class, particles that dissapear after a number of choques
 */
public class Ephemeral extends Particle{
    /**
     * constructor de ephemeral particle
     * @param color color of the particle
     * @param isRed booleano de si la particula es roja o azul
     * @param x  posicion  x de la particula
     * @param y  posicion y de la particula
     * @param vx velocidad en x de la particula
     * @param vy velocidad en y de la particula
     * @param w ancho del contenedor
     * @param h alto del contenedor
     */
    public Ephemeral(String color, boolean isRed, int x, int y, int vx, int vy,int w, int h) {
        super(color,isRed, x, y, vx, vy, w, h);
    }

    /**
     * Metodo para verificar si la particula
     * ha chocado con los muros del contenedor
     * y disminuir sus velocidades
     * @param int w ; ancho del contenedor
     * @param  int h : alto del contenedor
     */
    @Override
    protected void choque(int w, int h) {
        boolean choqueX = EnmuroX(w);
        boolean choqueY = EnmuroY(h);
        if (choqueX) {
            this.vX-=1;
            this.vX = -vX;
            if (x <= 70) x = 71;
            if (x >= 70 + w) x = 70 + w - 1;
        }
        
        if (choqueY) {
            this.vY-=1;
            this.vY = -vY;
            if (y <= 15) y = 16;
            if (y >= 15 + h) y = 15 + h - 1;
        }

        if (this.vX == 0 && this.vY == 0) {
            this.makeInvisible();
        }
    }
}
