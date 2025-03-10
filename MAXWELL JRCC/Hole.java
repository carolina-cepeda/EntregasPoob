
/**
 * Write a description of class Hole here.
 * 
 * @author Juanita Rubiano, Carolina Cepeda
 * @version 10 de marzo
 */
public class Hole
{
    private int positionX;
    private int positionY;
    private String color;
    private Circle grafico;

    /**
     * Constructor for objects of class Hole
     */
    public Hole(int px, int py, String color)
    {
        grafico = new Circle();
        grafico.changeColor (color);
        grafico.setXposition(positionX);
        grafico.setYposition(positionY);
   
    }
    
    public void makeVisible(){
        grafico.makeVisible();
    }
    
    public void makeInvisible(){
        grafico.makeInvisible();
    }
    
    // getPosition

}
