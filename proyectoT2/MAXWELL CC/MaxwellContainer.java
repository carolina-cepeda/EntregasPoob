import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase del contenedor MaxwellContainer
 * 
 * @author Carolina Cepeda
 * @version 25/03/2025
 */
public class MaxwellContainer {
    private int demon;
    private Particle particle;
    private Canvas canvas;
    private Rectangle marco, separacion, interior;
    private ArrayList<Hole> holes = new ArrayList<>();
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Demon> demons = new ArrayList<>();
    private int h;
    private int w;
    private static boolean esVisible = false;
    private boolean isOk = false;
    private List<String> coloresUsados = new ArrayList<>();

    /*
     * primer constructor
     */
    public MaxwellContainer(int h, int w) {

        isOk = validarDimension(h, w);
        if (!isOk)
            return;

        this.h = h;
        this.w = w;
        esVisible = false;
        canvas = Canvas.getCanvas();

        marco = createRectangle(h, w, "magenta", 0, 0);
        interior = createRectangle(h - 8, w - 8, "yellow", 4, 4);
        separacion = createRectangle(h, 2, "magenta", w / 2, 0);

    }

    /*
     * Segundo constructor
     */
    public MaxwellContainer(int h, int w, int d, int b, int r, int[][] particles) {

        isOk = validarDimension(h, w);
        if (!isOk)
            return;

        this.h = h;
        this.w = w;
        esVisible = false;
        canvas = Canvas.getCanvas();

        marco = createRectangle(h, w, "magenta", 0, 0);
        interior = createRectangle(h - 8, w - 8, "yellow", 4, 4);
        separacion = createRectangle(h, 2, "magenta", w / 2, 0);

        addDemon(d);

        for (int i = 0; i < particles.length; i++) {
            int px = particles[i][0];
            int py = particles[i][1];
            int vx = particles[i][2];
            int vy = particles[i][3];

            boolean isRed = (i < r);
            String color = getColor();
            if (color!=null){
                addParticle(color, isRed, px, py, vx, vy);
            }
        }
    }

    private Rectangle createRectangle(int height, int width, String color, int moveX, int moveY) {
        Rectangle rect = new Rectangle();
        rect.changeSize(height, width);
        rect.changeColor(color);
        rect.moveHorizontal(moveX);
        rect.moveVertical(moveY);
        return rect;
    }

    /*
     * Método para validar la posicion de los elementos
     */
    public boolean validarPosition(int px, int py) {
        return (70 <= px && px <= 70 + w && 15 <= py && py <= 15 + h);
    }

    /*
     * Metodo para añadir un demonio al juego
     * 
     * @param : entero d que se refiere a la posicion en y del demonio.
     */
    public void addDemon(int d) {
        int px = 70 + w / 2;
 
        if(isOk() && !validarDemon(d) && validarPosition(px, d)) {

            Demon demonio = new Demon(px, d, "black");
            demons.add(demonio);

        } else {
            JOptionPane.showMessageDialog(null, "La posición dada no es válida");

        }
    }

   /*
     * Metodo para eliminar un demonio al juego
     * 
     * @param : entero d que se refiere a la posicion en y del demonio.
     */
    public void delDemon(int d) {
        if ( isOk() && !demons.isEmpty()){
            for (int i = 0; i < demons.size(); i++) {
                Demon demonio = demons.get(i);

                if (demonio.EstoyAhi(d)) {
                    demonio.makeInvisible();
                    demons.remove(i);
                }
            }
        }
    }
    

    /*
     * método para revisar si un demonio ya existe basado en su posición en y.
     */
    private boolean validarDemon(int d) {
        if(isOk){
            for (Demon demon : demons) {
                if (demon.EstoyAhi(d)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /*
     * Metodo para añadir una particula al juego
     */
    public void addParticle(String Color, boolean isRed, int px, int py, int vx, int vy) {
        if(isOk()){
            if (!validarParticle(px, py,Color) && validarPosition(px, py)) {
                Particle p = new Particle(Color, isRed, px, py, vx, vy);

            particles.add(p);
            coloresUsados.add(Color);

            } else {
                JOptionPane.showMessageDialog(null, "La partícula no está en una posición válida");
            }
        }
    }

    private boolean validarParticle(int px, int py, String color) {
        if(isOk()){
            for (Particle p : particles) {
                if (p.EstoyAhi(px, py) || p.esMiColor(color)) {
                    return true;
                }
            }

            return false;
        }
        return false;
    }

    /*
     * Metodo para añadir un agujero al juego
     * 
     * @param: posicion en x, posicion en y, cantidad de particulas maxima.
     */
    public void addHole(int px, int py, int particles) {
        if(isOk()){
            if (validarPosition(px, py) && !validarHole(px, py)) {
                Hole ho = new Hole(px, py, particles);
                holes.add(ho);

            } else {
                JOptionPane.showMessageDialog(null, "La posición dada  esta fuera del rango");

            }
        }
    }

    private boolean validarHole(int px, int py) {
        if(isOk()){
            for (Hole hole : holes) {
                if (hole.EstoyAhi(px, py)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /*
     * metodo para eliminar una particula de un color dado
     */
    public void delParticle(String color) {
        if(isOk()){
            for(Particle p : particles){
            if (p.getColor()!= null && p.getColor().equals(color)){
                p.makeInvisible();
                particles.remove(p);
            }
            }
        }
    }

    /*
     * Metodo para saber si el juego ha terminado.
     */
    public boolean isGoal() {
        if (isOk()){
            for (Particle p : particles) {
                if (p.isRed()) {
                    if (!p.isGoalR(h, w)) {
                        return false;
                    }
                } else {
                    if (!p.isGoalB(h, w)) {
                        return false;
                    }
                }
            }

        return true;
    }
        return false;
    }

    /*
     * Metodo para consultar los demonios y sus posiciones en y
     */
    public int[] demons() {
        if(isOk()){
            int[] infoDemons = new int[demons.size()];

            for (int j = 0; j < demons.size(); j++) {
                infoDemons[j] = demons.get(j).getpY();

            }

            return infoDemons;
        }
        return null;
    }

    /*
     * metodo para consultar las particulas ( su posicion y velocidad)
     */
    public int[][] particles() {
        if(isOk()){
        int[][] infoParticles = new int[particles.size()][4];

        for (int j = 0; j < particles.size(); j++) {
            infoParticles[j] = particles.get(j).format();
        }

        return infoParticles;
        }
        return null;
    }

    /*
     * Metodo para consultar los agujeros del juego
     */
    public int[][] holes() {
            if (isOk()){
            int[][] infoHoles = new int[holes.size()][2];

            for (int j = 0; j < holes.size(); j++) {
                infoHoles[j] = holes.get(j).format();
            }

            return infoHoles;
        }
        return null;
    }

    /**
     * Metodo para hacer visible el juego
     */
    public void makeVisible() {
        if (isOk()){
            esVisible = true;
            marco.makeVisible();
            interior.makeVisible();
            separacion.makeVisible();

            for (Demon d : demons) {
                d.makeVisible();
            }
            for (Hole ho : holes) {
                ho.makeVisible();
            }
            for (Particle p : particles) {
                p.makeVisible();
            }
        }
    }

    /*
     * Metodo para hacer invisible el juego
     */
    public void makeInvisible() {
        if (isOk()){
            marco.makeInvisible();
            interior.makeInvisible();
            separacion.makeInvisible();
            esVisible = false;

            for (Demon d : demons) {
                d.makeInvisible();
            }

            for (Hole ho : holes) {

                ho.makeInvisible();
            }
            for (Particle p : particles) {
                p.makeInvisible();
            }
        }
    }

    /*
     * metodo para finalizar el juego
     */
    public void finish() {
        if (isOk()){
        makeInvisible();
        demons.clear();
        holes.clear();
        particles.clear();
        }
    }

    /*
    * metodo para iniciar el juego con un numero de ticks dado
    */
    public void start( int ticks) {
        if (isOk()){
            for (int i = 0; i < ticks; i++) {
                if (isGoal()) {
                    finish();
                    JOptionPane.showMessageDialog(null, "El juego ha terminado.");
                    break;
                } else {
                    List<Particle> paraEliminar = new ArrayList<>();

                    for (Particle p : particles) {
                        boolean afectadaPorDemonio = false;
                        boolean afectadaPorAgujero = false;

                        for (Demon d : demons) {
                            afectadaPorDemonio |= d.pasar(p);
                        }

                        for (Hole ho : holes) {
                            afectadaPorAgujero |= ho.pasar(p);
                        }

                        if (afectadaPorAgujero) {
                            paraEliminar.add(p);
                        } else if (!afectadaPorDemonio)
                            p.moveV(w, h);
                        }
                    

                    particles.removeAll(paraEliminar);
                }
            }
        }
}


    public boolean isOk() {
        return isOk;
    }

    private boolean validarDimension(int h, int w) {
        return (20 < h && h <= 300 && 20 < w && w < 300);
    }

    public static boolean getVisible() {
        return esVisible;
    }


    private String getColor() {
        List<String> colores = Arrays.asList("red", "blue", "yellow", "green", "magenta", "white", "orange", "cyan", "pink", "gray",
        "darkgray", "lightgray", "brown", "purple", "violet", "gold", "silver", "beige", "turquoise",
        "indigo", "maroon", "navy", "olive", "teal", "salmon", "coral", "khaki", "lavender", "orchid",
        "plum", "crimson", "chartreuse", "lime", "aquamarine", "peru", "seagreen", "slategray", "dodgerblue",
        "firebrick", "deeppink", "hotpink");
        List<String> coloresDisponibles = new ArrayList<>(colores);
        coloresDisponibles.removeAll(coloresUsados);
        if (coloresDisponibles.isEmpty()){
            return null;
        }
        else{
            return coloresDisponibles.get(0);
        }
            
        }
    }
