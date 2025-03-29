import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase del contenedor MaxwellContainer
 * 
 * @author Carolina Cepeda
 * @version 28/03/2025
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
     * Método para validar la posicion de los elemento|s
     */
    public boolean validarPosition(int px, int py) {
        return ((-1 - w / 2) <= px && px <= (w / 2 + 1) && 0 <= py && py <= (h + 1));
    }

    /*
     * Metodo para añadir un demonio al juego
     * 
     * @param : entero d que se refiere a la posicion en y del demonio.
     */
    public void addDemon(int d) {
        int px = 0;

        if(!validarDemon(d) && validarPosition(px, d)) {
            Demon demonio = new Demon(w,h,d,"black");
            demons.add(demonio);
            isOk= true;
        } else {
            JOptionPane.showMessageDialog(null, "La posición dada no es válida");
            isOk = false;
        }

    }

    /*
     * Metodo para eliminar un demonio al juego
     * 
     * @param : entero d que se refiere a la posicion en y del demonio.
     */
    public void delDemon(int d) {
        if (!demons.isEmpty()){
            isOk = false;
            for (int i = 0; i < demons.size(); i++) {
                Demon demonio = demons.get(i);
                if (demonio.EstoyAhi(d)) {
                    demonio.makeInvisible();
                    demons.remove(i);
                    isOk = true;
                    break;
                }
            }
        }
    }

    /*
     * método para revisar si un demonio ya existe basado en su posición en y.
     */
    private boolean validarDemon(int d) {
        for (Demon demon : demons) {
            if (demon.EstoyAhi(d)) {
                return true;
            }
        }
        return false;

    }

    /*
     * Metodo para añadir una particula al juego
     */
    public void addParticle(String Color, boolean isRed, int px, int py, int vx, int vy) {

        if (!validarParticle(px, py,Color) && validarPosition(px, py)) {
            Particle p = new Particle(Color, isRed, px, py, vx, vy,w,h);

            particles.add(p);
            coloresUsados.add(Color);
            isOk = true;
        } else {
            JOptionPane.showMessageDialog(null, "La partícula no está en una posición válida");
            isOk = false;
        }

    }

    private boolean validarParticle(int px, int py, String color) {

        for (Particle p : particles) {
            if (p.EstoyAhi(px, py) || p.esMiColor(color)) {
                return true;
            }
        }

        return false;

    }

    /*
     * Metodo para añadir un agujero al juego
     * 
     * @param: posicion en x, posicion en y, cantidad de particulas maxima.
     */
    public void addHole(int px, int py, int particles) {

        if (validarPosition(px, py) && !validarHole(px, py)) {
            Hole ho = new Hole(px, py, particles,w,h);
            holes.add(ho);
            isOk = true;
        } else {
            JOptionPane.showMessageDialog(null, "La posición dada  esta fuera del rango");
            isOk = false;
        }

    }

    private boolean validarHole(int px, int py) {

        for (Hole hole : holes) {
            if (hole.EstoyAhi(px, py)) {
                return true;
            }
        }
        return false;

    }

    /*
     * metodo para eliminar una particula de un color dado
     */
    public void delParticle(String color) {
        ArrayList<Particle> paraEliminar = new ArrayList<>();
        isOk = false;
        for(Particle p : particles){
            if (p.getColor()!= null && p.getColor().equals(color)){
                p.makeInvisible();
                paraEliminar.add(p);
                coloresUsados.remove(color);
                isOk = true;
                break;
            }
        }
        particles.removeAll(paraEliminar);
        paraEliminar.clear();
    }

    /*
     * Metodo para saber si el juego ha terminado.
     */
    public boolean isGoal() {
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

    /*
     * Metodo para consultar los demonios y sus posiciones en y
     */
    public int[] demons() {
        if(!demons.isEmpty()){
            isOk = false;
            int[] infoDemons = new int[demons.size()];

            for (int j = 0; j < demons.size(); j++) {
                infoDemons[j] = demons.get(j).getpY();

            }
            Arrays.sort(infoDemons);
            isOk = true;

            return infoDemons;
        }
        isOk = false;
        return null;
    }

    /*
     * metodo para consultar las particulas ( su posicion y velocidad)
     *@return: un arreglo de enteros que contiene la posicion y velocidad de las
     * particulas
     */
    public int[][] particles() {

        int[][] infoParticles = new int[particles.size()][4];

        for (int j = 0; j < particles.size(); j++) {
            infoParticles[j] = particles.get(j).format();
        }

        Arrays.sort(infoParticles,Comparator.comparingInt((int[] p)-> p[0])
            .thenComparingInt(p -> p[1])
            .thenComparingInt(p -> p[2])
            .thenComparingInt(p -> p[3]));
        isOk = true;
        return infoParticles;

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
            Arrays.sort(infoHoles,Comparator.comparingInt((int[] h)-> h[0])
                .thenComparingInt(h -> h[1]));

            return infoHoles;
        }
        return null;
    }

    /**
     * Metodo para hacer visible el juego
     */
    public void makeVisible() {
        isOk = false;
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
        isOk = true;
    }

    /*
     * Metodo para hacer invisible el juego
     */
    public void makeInvisible() {
        isOk = false;
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

    /*
     * metodo para finalizar el juego
     */
    public void finish() {

        isOk = false;
        makeInvisible();
        demons.clear();
        holes.clear();
        particles.clear();
        coloresUsados.clear();
        isOk = true;
    }

    /*
     * metodo para iniciar el juego con un numero de ticks dado
     */
    public void start(int ticks) {
        for (int i = 0; i < ticks; i++) {
            if (isGoal()) {
                finish();
                JOptionPane.showMessageDialog(null, "El juego ha terminado.");
                break;
            } else {
                List<Particle> paraEliminar = new ArrayList<>();

                for (Particle p : particles) {
                    if (verificarChoqueAgujero(p, holes)) {
                        paraEliminar.add(p);
                    }
                    for (int j = 0; j < 2; j++) {
                        boolean afectadaPorDemonio = pasarPorDemonios(p, demons); 

                        if (!afectadaPorDemonio) { 
                            p.moveV(w, h);
                        }

                        if (verificarChoqueAgujero(p, holes)) {
                            paraEliminar.add(p);
                        }
                    }
                }
                for (Particle p : paraEliminar) {
                    delParticle(p.getColor());
                }

            }
        }
    }

    private boolean verificarChoqueAgujero(Particle p, ArrayList<Hole> holes) {
        for (Hole ho : holes) {
            if (ho.pasar(p)) {
                return true; 
            }
        }
        return false;
    }

    private boolean pasarPorDemonios(Particle p, ArrayList<Demon> demons) {
        boolean afectada= false;

        for (Demon d : demons) {
            if( d.pasar(p,w,h)){
                afectada= true;
                break;
            }
        }
        return afectada;
    }

    public boolean isOk() {
        return isOk;
    }

    private boolean validarDimension(int h, int w) {
        return (1 < h && h <= 300 && 1 < w && w < 300);
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
