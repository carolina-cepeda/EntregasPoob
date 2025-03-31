package maxwell;

public class Blue extends Demon {
    /**
     * Constructor for objects of class blue
     * @param w Ancho del contenedor
     * @param h Alto del contenedor
     * @param pY Posicion inicial en y
     */
    public Blue(int w,int h, int pY) {
        super(w,h,pY,"neonblue");
    }

    /**
     * Metodo para saber si la particula choca con el demonio y pasa
     * @param p Particula a verificar
     * @param w Ancho del contenedor
     * @param h Alto del contenedor
     * @return boolean
     */
    @Override
    public boolean pasar(Particle p,int w, int h) {
        if (p.EstoyAhi(this.originalX,this.originalY) && !p.isRed()){
            p.pasar(w,h);
            return true;
        }
        return false;
    }
}
