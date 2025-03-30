package maxwell;
/**
 * Movil Hole classs
 */
public class Movil extends Hole{

    private int vY= 1;
    private int vX= 1;
    /**
     * Constructor for objects of class Movil
     * @param int px Posicion inicial en x
     * @param int py Posicion inicial en y
     * @param int w Ancho del contenedor
     * @param int h Alto del contenedor
    */
    public Movil(int px, int py, int w, int h,int particles) {
        super(px, py,particles,w,h);
    
    }

    /**
     * Metodo para mover las particulas
     * basado en sus velocidades en x y y.
     * @param int ancho del contenedor, int alto.
     */
    public void mover(int w, int h) {

        int aumentoY= this.vY /2 ;
        int aumentoX= this.vX /2 ;

        choque(w, h);
        originalY += aumentoY;
        positionY += aumentoY;
    
        originalX += aumentoX;
        positionY += aumentoX;
        moveSlow(aumentoX, aumentoY);
        
        if (esVisible) {
            makeVisible();
        }
        choque(w, h);

    }
    /**
     * Metodo para mover las particulas lentamente
     * @param int vx : velocidad en x
     * @param int vy : velocidad en y
     */
        private void moveSlow(int vx, int vy) {

        makeInvisible();
        grafico.slowMoveHorizontal(vx);
        grafico.slowMoveVertical(vy);
        makeVisible();
    }

    /**
     * Metodo para verificar si la particula
     * ha chocado con los muros del contenedor
     * @param int w ; ancho del contenedor
     * @param  int h : alto del contenedor
     */
    
    protected void choque(int w, int h) {
        boolean choqueX = EnmuroX(w);
        boolean choqueY = EnmuroY(h);
        while (!estoyAhi(0,0)) {
           
        if (choqueX) {
            this.vX = -vX;
            if (positionX <= 70) positionX = 71;
            if (positionX >= 70 + w) positionX = 70 + w - 1;
        }
        
        if (choqueY) {
            this.vY = -vY;
            if (positionY <= 15) positionY = 16;
            if (positionY >= 15 + h) positionY = 15 + h - 1;
        }
        }
    }  
    /**
     * Metodo para verificar si el agujero
     * ha chocado con los muros verticales del contenedor
     * @param int w ancho del contenedor
     * @return boolean
     */
    protected boolean EnmuroX(int w) {
        return (positionX <= 70 || positionX >= 70 + w || positionX== 70 + w / 2);
    }

    /**
     * Metodo para verificar si el agujero
     * ha chocado con los muros horizontales del contenedor
     * @param h altura del contenedor
     * @return boolean
     */
    protected boolean EnmuroY(int h) {
        return (positionY <= 15 || positionY >= 15 + h);
    }
    /**
     * Metodo para verificar si el agujero está en una posicion dada
     * @param int x posicion en x
     * @param int y posicion en y
     * @return boolean
     */
    private boolean estoyAhi(int x, int y) {
        return (originalX == x && originalY == y);
    }
}
