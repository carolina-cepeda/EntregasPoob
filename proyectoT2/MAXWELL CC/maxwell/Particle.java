package maxwell;
import shapes.*;


/**
 *Particula del contenedor
 * 
 * @author Carolina Cepeda
 * @version 29/03/25
 */
public class Particle {
    private String color;
    private int originalX;
    private int originalY;
    private int x;
    private int y;
    private int vX;
    private int vY;
    private Circle grafico;
    private boolean isRed;
    private boolean esVisible;

    /**
     * Constructor for objects of class Particle
     * @param color Color de la particula
     * @param isRed Si la particula es roja o azul
     * @param x Posicion inicial en x
     * @param y Posicion inicial en y
     * @param vX Velocidad en x
     * @param vY Velocidad en y
     * @param w Ancho del contenedor
     * @param h Alto del contenedor
     */
    public Particle(String color, boolean isRed, int x, int y, int vX, int vY,int w, int h) {
        this.esVisible = MaxwellContainer.getVisible();
        this.originalX = x;
        this.originalY = y;

        this.x = x + (70+w/2);
        this.y = (15+h) - originalY;
        this.vX = vX;
        this.vY = vY;
        this.isRed = isRed;
        this.color = color ;
        grafico = new Circle();
        grafico.changeSize(8);
        grafico.changeColor(color);
        grafico.setXposition(this.x);
        grafico.setYposition(this.y);

    }

    /**
     * metodo para hacer visible la particula
     */
    public void makeVisible() {
        grafico.makeVisible();
        esVisible = true;
    }

    /**
     * metodo para hacer invisible la particula
     */
    public void makeInvisible() {
        grafico.makeInvisible();
        esVisible = false;
    }

    /**
     * metodo para verificar que las particulas rojas esten
     * en el lado correcto del juego.
     * @param int alto del contenedor
     * @param int ancho.
     * @return boolean
     */
    public boolean isGoalR(int h, int w) {
        int maxX = (70 + (w / 2)) - 1;
        return (x >= 70 && x < maxX);
    }

    /**
     * metodo para verificar que las particulas azules esten
     * en el lado correcto del juego.
     * @param int alto del contenedor
     * @param int ancho.
     * @return boolean
     */
    public boolean isGoalB(int h, int w) {
        int minX = (70 + (w / 2));
        return (x >= minX && x <= (70 + w));
    }
    /**
     * metodo para retornar posicion en x del juego
     * @return int
     */
    public int getpX() {
        return this.originalX;
    }
    /**
     * metodo para retornar posicion en y del juego
     * @return int
     */
    public int getpY() {
        return this.originalY;
    }

    /**
     * Metodo para mover las particulas
     * basado en sus velocidades en x y y.
     * @param int ancho del contenedor, int alto.
     */
    public void moveV(int w, int h) {

        int aumentoY= this.vY /2 ;
        int aumentoX= this.vX /2 ;

        choque(w, h);
        originalY += aumentoY;
        y += aumentoY;
    
        originalX += aumentoX;
        x += aumentoX;
        moveSlow(aumentoX, aumentoY);
        
        if (esVisible) {
            makeVisible();
        }
        choque(w, h);

    }
    /**
     * Metodo para mover las particulas lentamente
     * @param int vx : velocidad en x
     * @param int vy : velocidad en y
     */
        private void moveSlow(int vx, int vy) {

        makeInvisible();
        grafico.slowMoveHorizontal(vx);
        grafico.slowMoveVertical(vy);
        makeVisible();
    }

    /**
     * Metodo para verificar si la particula
     * ha chocado con los muros del contenedor
     * @param int w ; ancho del contenedor
     * @param  int h : alto del contenedor
     */
    private void choque(int w, int h) {
        boolean choqueX = EnmuroX(w);
        boolean choqueY = EnmuroY(h);
        
        if (choqueX && choqueY) {
            this.vX = -vX;
            this.vY = -vY;
            if (x <= 70) x = 71;
            if (x >= 70 + w) x = 70 + w - 1;
            if (y <= 15) y = 16;
            if (y >= 15 + h) y = 15 + h - 1; 
        } 
        
        if (choqueX) {
            this.vX = -vX/2;
        } 
        else if (choqueY) {
            this.vY = -vY/2;
        }
    }
    /**
     * Metodo para mover la particula si choca con un demonio
     * @param w : ancho del contenedor
     * @param h : alto del contenedor
     */
    public void pasar(int w, int h) {
        int aumentoY= this.vY ;
        int aumentoX= this.vX ;
        originalY += aumentoY;
        y += aumentoY;

        if (isRed) {
            originalX -= aumentoX;
            x -= aumentoX;
            moveSlow(-aumentoX, aumentoY);
        } else {
            originalX += aumentoX;
            x += aumentoX;
            moveSlow(aumentoX, aumentoY);
        }
        if (esVisible) {
            makeVisible();
        }
    }
    /**
     * Metodo para verificar si la particula
     * ha chocado con los muros verticales del contenedor
     * @param int w
     * @return boolean
     */
    private boolean EnmuroX(int w) {
        return (x <= 70 || x >= 70 + w || x == 70 + w / 2);
    }

    /**
     * Metodo para verificar si la particula
     * ha chocado con los muros horizontales del contenedor
     * @param h
     * @return
     */
    private boolean EnmuroY(int h) {
        return (y <= 15 || y >= 15 + h);
    }

    /**
     * Metodo para verificar si la particula está en la posición dada
     * @param px
     * @param py
     * @return boolean
     */
    public boolean EstoyAhi(int px, int py) {
        return (originalX == px && originalY == py);
    }
    /**
     * Metodo para colocar la informacion de la particula en un formato
     * @return int[]
     */
    public int[] format() {
        int[] info = new int[4];
        info[0] = originalX;
        info[1] = originalY;
        info[2] = vX;
        info[3] = vY;
        return info;
    }
    /**
     * Metodo para verificar si la particula es roja
     * @return boolean
     */
    public boolean isRed(){
        return isRed ;
    }
    /**
     * Metodo para vretornar el color de la particula
     * @return String
     */
    public String getColor(){
        return color;
    }
    /**
     * Metodo para verificar si la particula es del color dado
     * @param color
     * @return boolean
     */
    public boolean esMiColor(String color){
        return this.color.equals(color);
    }

}
