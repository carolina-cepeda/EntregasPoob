
/**
 * Write a description of class Particle here.
 * 
 * @author Juanita Rubiano, Carolina Cepeda
 * @version8/03/25
 */
public class Particle extends Circle{

    private int vX;
    private int vY;
    /**
     * Constructor for objects of class Particle
     */
    public Particle(String color, int vX, int vY,int x, int y)
    {
        super();
        this.changeColor(color);
        this.xPosition = x;
        this.yPosition= y;
        this.vX = vX;
        this.vY = vY;
        this.makeVisible();
    }
    
    
    
    
    public int getpX(){
        return this.xPosition;
    }
    public int getpY(){
        return this.yPosition;
    }
}
