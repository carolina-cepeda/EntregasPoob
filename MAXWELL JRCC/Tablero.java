import java.util.TreeMap;

/**
 * Tablero del juego
 * 
 * @authors Carolina Cepeda, Juanita Rubiano 
 * @version 10/03/25
 */
public class Tablero
{
    private MaxwellContainer MaxwellContainer;
    private Canvas canvas;
    private TreeMap <String,Hole> holes;
    private TreeMap <String,Particle> particles;
    private TreeMap <String,Demon> demons;
    /**
     * Constructor for objects of class Tablero based in canvas.
     */
    public Tablero(int h,int w)
    {
        canvas = Canvas.getCanvas();
        
    }
    
    

    
}
