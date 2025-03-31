package maxwell;


public  class Weak extends Demon{
    /**
     * Constructor for objects of class weak
     * @param w Ancho del contenedor
     * @param h Alto del contenedor
     * @param pY Posicion inicial en y
     */
    public Weak(int w,int h, int pY) {
        super(w,h,pY,"sienna");
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
        if (p.EstoyAhi(this.originalX,this.originalY)){
            p.pasar(w,h);
            this.deleteDemon();
            return true;
        }
        return false;


    }
}
