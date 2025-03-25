

/**
 * Write a description of class Particle here.
 * 
 * @author Carolina Cepeda
 * @version 10/03/25
 */
public class Particle {
    private String color;
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
    public Particle(String color, boolean isRed, int x, int y, int vX, int vY) {
        this.esVisible = MaxwellContainer.getVisible();
        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;
        this.isRed = isRed;
        this.color = color ;
        grafico = new Circle();
        grafico.changeSize(8);
        grafico.changeColor(color);
        grafico.setXposition(x);
        grafico.setYposition(y);

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
        return this.x;
    }

    public int getpY() {
        return this.y;
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
        for (int i = 0; i < 3; i++) {
            y += vY;

            if ("red".equals(color)) {
                x -= vX;
                moveSlow(-vX, vY);
            } else {
                x += vX;
                moveSlow(vX, vY);
            }
            if (esVisible) {
                makeVisible();
            }
            choque(w, h);
        }
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
        return (x == px && y == py);
    }

    public int[] format() {
        int[] info = new int[4];
        info[0] = x;
        info[1] = y;
        info[2] = vX;
        info[3] = vY;
        return info;
    }

    /*
     * metodo para pasar las particulas al otro lado si es necesario.
     */
    public void pasar() {
        if (isRed) {
            pasarRojo();
        } else {
            pasarBlue();
        }
        if (esVisible) {
            makeVisible();
        }
    }

    /*
     * Metodo para pasar las particulas rojas al lado correcto.
     */
    public void pasarRojo() {
        this.x += vX;
        this.y += vY;
        this.moveSlow(vX,vY);

    }
    /*
     * Metodo para pasar las particulas azules al lado correcto.
     */

    public void pasarBlue() {
        this.x += vX;
        this.y += vY;
        this.moveSlow(vX, vY);
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
