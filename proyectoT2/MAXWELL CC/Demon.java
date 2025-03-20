
/**
 * Write a description of class Demon here.
 * 
 * @author Carolina Cepeda
 * @version 10/03/25
 */
public class Demon {
    private Triangle grafico;
    private int pX;
    private int pY;
    private boolean esVisible = false;
    private int h;
    private int w;

    /**
     * Constructor for objects of class Demon
     */
    public Demon(int pX, int pY, String color,int h, int w) {
        this.esVisible = MaxwellContainer.getVisible();
        this.pX = pX;
        this.pY = pY;
        this.h = h;
        this.w = w;
        grafico = new Triangle();
        grafico.changeSize(10, 10);
        grafico.changeColor(color);
        grafico.setXposition(pX);
        grafico.setYposition(pY);

    }

    public void deleteDemon() {
        grafico.makeInvisible();
    }

    public boolean pasar(Particle p) {
        int posx = p.getpX();
        int posy = p.getpY();
        p.transformarDimensionACanvas();
        transformarDimensionACanvas();
        if (posx == pX && posy == pY) {
            p.pasar();
        }
        return (posx == pX && posy == pY);
    }

    public void makeVisible() {
        grafico.makeVisible();
        esVisible = true;
    }

    public void makeInvisible() {
        grafico.makeInvisible();
        esVisible = false;
    }

    public int getpY() {
        return pY;
    }

    public boolean EstoyAhi(int d) {
        TransformarDimensionAJuego();
        return d == pY;
    }

    public void transformarDimensionACanvas(){
        this.pX += 70 + w/2 ;
        this.pY += 15 + h;

    }

    public void TransformarDimensionAJuego(){
        this.pX -= (70 + w/2);
        this.pY -= (15+h); 
    }
}
