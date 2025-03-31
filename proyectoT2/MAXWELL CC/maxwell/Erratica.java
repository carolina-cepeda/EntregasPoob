package maxwell;
import java.util.Random;
public class Erratica  extends Particle{
    
    public Erratica(String color, boolean isRed, int x, int y, int vX, int vY,int w, int h) {
        super(color, isRed, x, y, vX, vY,w,h);
    }
    /**
     * Metodo para cambiar la velocidad de  las particulas
     * basado en sus velocidades en x y y de forma aleatoria entre 0,1, y -1.
     * @param int ancho del contenedor, int alto.
     */
    @Override
    protected void choque(int w, int h) {
        Random rand = new Random();
        
        boolean choqueX = EnmuroX(w);
        boolean choqueY = EnmuroY(h);
        
        if (choqueX) {
            this.vX -= 1;
            int factorX = rand.nextInt(3) - 1;  // -1, 0 o 1
            this.vX *= factorX; 
            
            if (x <= 70) x = 71;
            if (x >= 70 + w) x = 70 + w - 1;
        }

        if (choqueY) {
            this.vY -= 1;
            int factorY = rand.nextInt(3) - 1;  // -1, 0 o 1
            this.vY *= factorY;
            
            if (y <= 15) y = 16;
            if (y >= 15 + h) y = 15 + h - 1;
        }
        

    }
}
