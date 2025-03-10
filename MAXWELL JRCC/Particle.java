
/**
 * Write a description of class Particle here.
 * 
 * @author Juanita Rubiano, Carolina Cepeda
 * @version 10/03/25
 */
public class Particle {
    private String color;
    private int x;
    private int y;
    private int vX;
    private int vY;
    private Circle grafico;
    
    /**
     * Constructor for objects of class Particle
     */
    public Particle(String color, int vX, int vY,int x, int y)
    {   
        grafico = new Circle();
        grafico.changeColor (color);
        grafico.setXposition(x);
        grafico.setYposition(y);

        
    }
    
    
    
    
    public int getpX(){
        return this.x;
    }
    public int getpY(){
        return this.y;
    }
}
