
import java.awt.*;
/**
 * Write a description of class MaxwellContainer here.
 * 
 * @author Juanita Rubiano , Carolina Cepeda
 * @version 4/03/2025
 */
public class MaxwellContainer{
    private int demon;
    private Particle particle;

    public MaxwellContainer ( int h, int w ){
        
        Tablero tablero= new Tablero(h,w);
        
    }
    
    public MaxwellContainer( int h, int w, int d, int b, int r, int[][] particles){
        // creacion contenedor
        this.addDemon(d);
        
    }

    public void addDemon (int d){
        
    }
    
    public void addParticle (String color, boolean isRed,int px, int py, int vx, int vy){
        
    }
    
    public void addHole( int px, int py, int particles){
        
    }
    
    public void delParticle( String color){
        
    }
    
    public void delDemon (int d){
        
    }
    
    public void start( int ticks){
        
    }
    
    public boolean isGoal(){
        return false;
    }
    
    public int[] demons(){
        return new int[0];
    }
    
    public int[][] particles(){
        return new int[0][0];
    }
    
    public int[][] holes(){
        return new int[0][0];
    }
    
    public void makeVisible(){
        
    }
    
    public void makeInvisible(){
        
    }
    
    public void finish(){
        
    }
    
    public boolean ok(){
       return false; 
    }    
    
}
