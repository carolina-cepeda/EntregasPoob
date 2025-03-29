
/**
 *Particula del contenedor
 * 
 * @author Carolina Cepeda
 * @version 26/03/25
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

    /*
     * metodo para hacer visible la particula
     */
    public void makeVisible() {
        grafico.makeVisible();
        esVisible = true;
    }

    /*
     * metodo para hacer invisible la particula
     */
    public void makeInvisible() {
        grafico.makeInvisible();
        esVisible = false;
    }

    /*
     * metodo para verificar que las particulas rojas esten
     * en el lado correcto del juego.
     */
    public boolean isGoalR(int h, int w) {
        int maxX = (70 + (w / 2)) - 1;
        return (x >= 70 && x < maxX);
    }

    /*
     * metodo para verificar que las particulas azules esten
     * en el lado correcto del juego.
     */
    public boolean isGoalB(int h, int w) {
        int minX = (70 + (w / 2));
        return (x >= minX && x <= (70 + w));
    }

    public int getpX() {
        return this.originalX;
    }

    public int getpY() {
        return this.originalY;
    }

    public int getvX() {
        return this.vX;
    }

    public int getvY() {
        return this.vY;
    }

    /*
     * Metodo para mover las particulas
     * basado en sus velocidades en x y y.
     */
    public void moveV(int w, int h) {

        int aumentoY= this.vY /2 ;
        int aumentoX= this.vX /2 ;
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
        choque(w, h);
    }

    private void moveSlow(int vx, int vy) {

        makeInvisible();
        grafico.slowMoveHorizontal(vx);
        grafico.slowMoveVertical(vy);
        makeVisible();
    }

    private void choque(int w, int h) {
        boolean choqueX = EnmuroX(w);
        boolean choqueY = EnmuroY(h);

        if (choqueX) {
            this.vX = -vX;
        }
        if (choqueY) {
            this.vY = -vY;
        }
    }

    
    public void pasar(int w, int h) {

        int aumentoY= this.vY /2 ;
        int aumentoX= this.vX /2 ;
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
    private boolean EnmuroX(int w) {
       return (x <= 70 || x >= 70 + w || x == 70 + w / 2);
    }

    private boolean EnmuroY(int h) {
        return (y <= 15 || y >= 15 + h);
    }

    public void setpX(int dado) {
        this.x = dado;
    }

    public void setpY(int dado) {
        this.y = dado;
    }

    public boolean EstoyAhi(int px, int py) {
        return (originalX == px && originalY == py);
    }

    public int[] format() {
        int[] info = new int[4];
        info[0] = originalX;
        info[1] = originalY;
        info[2] = vX;
        info[3] = vY;
        return info;
    }

    public boolean isRed(){
        return isRed ;
    }

    public String getColor(){
        return color;
    }

    public boolean esMiColor(String color){
        return this.color.equals(color);
    }

}
