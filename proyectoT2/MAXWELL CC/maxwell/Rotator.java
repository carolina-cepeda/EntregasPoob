package maxwell;
/**
 * Rotator particle class, particula que intercambia velocidades cuando se choca con los muros
 */
public class Rotator extends Particle{
/**
 * constructor de rotator particle
 * @param color
 * @param isRed
 * @param x
 * @param y
 * @param vx
 * @param vy
 * @param w
 * @param h
 */
    public Rotator(String color, boolean isRed, int x, int y, int vx, int vy,int w, int h) {
        super(color,isRed, x, y, vx, vy, w, h);
    }

      /**
     * Metodo para verificar si la particula
     * ha chocado con los muros del contenedor
     * e intercambiar sus velocidades
     * @param int w ; ancho del contenedor
     * @param  int h : alto del contenedor
     */
    @Override
    protected void choque(int w, int h) {
        boolean choqueX = EnmuroX(w);
        boolean choqueY = EnmuroY(h);
        int vXOriginal = this.vX;
        int vYOriginal = this.vY;
        if (choqueX) {
            this.vX=vYOriginal;
            this.vX = -vX;
            if (x <= 70) x = 71;
            if (x >= 70 + w) x = 70 + w - 1;
        }
        
        if (choqueY) {
            this.vY= vXOriginal;
            this.vY = -vY;
            if (y <= 15) y = 16;
            if (y >= 15 + h) y = 15 + h - 1;
        }

    }
}
