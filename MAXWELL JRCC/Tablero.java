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
    private Rectangle marco, separacion, interior;
    private TreeMap <String,Hole> holes;
    private TreeMap <String,Particle> particles;
    private TreeMap <String,Demon> demons;
    
    /**
     * Constructor for objects of class Tablero based in canvas.
     */
    public Tablero(int h,int w)
    {
        canvas = Canvas.getCanvas();
        
        marco = new Rectangle();
        marco.changeSize(h,w);
        marco.changeColor("red");
        interior = new Rectangle();
        interior.changeSize(h-8,w-8);
        interior.moveHorizontal(4);
        interior.moveVertical(4);
        
        
        separacion = new Rectangle();
        separacion.changeSize(h,2);
        separacion.changeColor("black");
        separacion.moveHorizontal(w/2);
        
        
        
    }
    
    public void makeVisible(){
        marco.makeVisible();
        interior.makeVisible();
        separacion.makeVisible();
        
    }
    
    public void makeInvisible(){
        marco.makeInvisible();
        interior.makeInvisible();
        separacion.makeInvisible();

    }
    
    
    

    
}
