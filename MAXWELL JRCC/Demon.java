
/**
 * Write a description of class Demon here.
 * 
 * @author Juanita Rubiano, Carolina Cepeda
 * @version 8/03/25
 */
public class Demon
{
    private Triangle triangulo;

    /**
     * Constructor for objects of class Demon
     */
    public Demon(int pX, int pY, String color)
    {
        triangulo = new Triangle();
        triangulo.changeColor (color);
        triangulo.setXposition(pX);
        triangulo.setYposition(pY);

    }

    public void deleteDemon(){
        triangulo.makeInvisible();
    }

    public boolean pasar( Particle p){
        int posx = p.getpX();
        int posy = p.getpY();
        int posDX = triangulo.getXposition();
        int posDY = triangulo.getYposition();
        return ( posx==posDX && posy == posDY) ;
    }
    public void makeVisible(){
        triangulo.makeVisible();
    }

    public void makeInvisible(){
        triangulo.makeInvisible();
    }

}
