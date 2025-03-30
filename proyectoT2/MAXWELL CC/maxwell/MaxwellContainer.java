package maxwell;
import java.util.ArrayList ;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;
import shapes.*;

/**sss
 * Clase del contenedor MaxwellContainer
 * 
 * @author Carolina Cepeda
 * @version 29/03/2025
 */
public class MaxwellContainer {
    private Canvas canvas;
    private Rectangle marco, separacion, interior;
    private ArrayList<Hole> holes = new ArrayList<>();
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Demon> demons = new ArrayList<>();
    private int h;
    private int w;
    private static boolean esVisible = false;
    private boolean Ok = false;
    private List<String> coloresUsados = new ArrayList<>();

    /**
     * primer constructor
     * @param h: altura del contenedor
     * @param w: ancho del contenedor
     */
    public MaxwellContainer(int h, int w) {

        Ok = validarDimension(h, w);
        if (!Ok)
            return;

        this.h = h;
        this.w = w;
        esVisible = false;
        canvas = Canvas.getCanvas();

        marco = createRectangle(h, w, "magenta", 0, 0);
        interior = createRectangle(h - 8, w - 8, "yellow", 4, 4);
        separacion = createRectangle(h, 2, "magenta", w / 2, 0);

    }

    /**
     * Segundo constructor
     * @param h: altura del contenedor
     * @param w: ancho del contenedor
     * @param d: posicion en y del demonio
     * @param b: cantidad de particulas azules
     * @param r: cantidad de particulas rojas
     * @param particles: arreglo de enteros que contiene la posicion y velocidad
     */
    public MaxwellContainer(int h, int w, int d, int b, int r, int[][] particles) {

        Ok = validarDimension(h, w);
        if (!Ok)
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

    /**
     * Método para crear un rectángulo graficamente
     * @param height
     * @param width
     * @param color
     * @param moveX
     * @param moveY
     * @return
     */
    private Rectangle createRectangle(int height, int width, String color, int moveX, int moveY) {
        Rectangle rect = new Rectangle();
        rect.changeSize(height, width);
        rect.changeColor(color);
        rect.moveHorizontal(moveX);
        rect.moveVertical(moveY);
        return rect;
    }

    /**
     * Método para validar la posicion de los elementos
     * @param px: posicion en x
     * @param py: posicion en y
     */
    public boolean validarPosition(int px, int py) {
        return ((-1 - w / 2) <= px && px <= (w / 2 + 1) && 0 <= py && py <= (h + 1));
    }

    /**
     * Metodo para añadir un demonio al juego
     * 
     * @param : entero d que se refiere a la posicion en y del demonio.
     */
    public void addDemon(int d) {
        int px = 0;

        if(!validarDemon(d) && validarPosition(px, d)) {
            Demon demonio = new Demon(w,h,d,"black");
            demons.add(demonio);
            Ok= true;
        } else {
            JOptionPane.showMessageDialog(null, "La posición dada no es válida");
            Ok = false;
        }

    }

    /**
     * Metodo para eliminar un demonio al juego
     * 
     * @param : entero d que se refiere a la posicion en y del demonio.
     */
    public void delDemon(int d) {
        if (!demons.isEmpty()){
            Ok = false;
            for (int i = 0; i < demons.size(); i++) {
                Demon demonio = demons.get(i);
                if (demonio.EstoyAhi(d)) {
                    demonio.makeInvisible();
                    demons.remove(i);
                    Ok = true;
                    break;
                }
            }
        }
    }

    /**
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

    /**
     * Metodo para añadir una particula al juego
     * @param Color: color de la particula
     * @param isRed: booleano que indica si la particula es roja o azul
     * @param px: posicion en x
     * @param py: posicion en y
     * @param vx: velocidad en x
     * @param vy: velocidad en y
     */
    public void addParticle(String Color, boolean isRed, int px, int py, int vx, int vy) {

        if (!validarParticle(px, py,Color) && validarPosition(px, py)) {
            Particle p = new Particle(Color, isRed, px, py, vx, vy,w,h);

            particles.add(p);
            coloresUsados.add(Color);
            Ok = true;
        } else {
            JOptionPane.showMessageDialog(null, "La partícula no está en una posición válida");
            Ok = false;
        }

    }
    /**
    * Metodo para verificar si una particula ya existe en la posicion dada o su color.
    * @param px: posicion en x
    * @param py: posicion en y
    * @param color: color de la particula
    */
    private boolean validarParticle(int px, int py, String color) {

        for (Particle p : particles) {
            if (p.EstoyAhi(px, py) || p.esMiColor(color)) {
                return true;
            }
        }

        return false;

    }

    /**
     * Metodo para añadir un agujero al juego
     * 
     * @param px: posicion en x
     * @param py: posicion en y
     * @param particles: cantidad de particulas que el agujero puede absorber
     */
    public void addHole(int px, int py, int particles) {

        if (validarPosition(px, py) && !validarHole(px, py)) {
            Hole ho = new Hole(px, py, particles,w,h);
            holes.add(ho);
            Ok = true;
        } else {
            JOptionPane.showMessageDialog(null, "La posición dada  esta fuera del rango");
            Ok = false;
        }

    }
    /**
     * Metodo para validar si un agujero del juego ya existe
     * @param px
     * @param py
     * @return
     */
    private boolean validarHole(int px, int py) {

        for (Hole hole : holes) {
            if (hole.EstoyAhi(px, py)) {
                return true;
            }
        }
        return false;

    }

    /**
     * metodo para eliminar una particula de un color dado
     * @param color
     */
    public void delParticle(String color) {
        ArrayList<Particle> paraEliminar = new ArrayList<>();
        Ok = false;
        for(Particle p : particles){
            if (p.getColor()!= null && p.getColor().equals(color)){
                p.makeInvisible();
                paraEliminar.add(p);
                coloresUsados.remove(color);
                Ok = true;
                break;
            }
        }
        particles.removeAll(paraEliminar);
        paraEliminar.clear();
    }

    /**
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

    /**
     * Metodo para consultar los demonios y sus posiciones en y
     */
    public int[] demons() {
            Ok = false;
            int[] infoDemons = new int[demons.size()];

            for (int j = 0; j < demons.size(); j++) {
                infoDemons[j] = demons.get(j).getpY();

            }
            Arrays.sort(infoDemons);
            Ok = true;

            return infoDemons;
    
    }

    /**
     * metodo para consultar las particulas ( su posicion y velocidad)
     *@return: un arreglo de enteros que contiene la posicion y velocidad de las
     * particulas
     */
    public int[][] particles() {
        Ok = false;
        int[][] infoParticles = new int[particles.size()][4];

        for (int j = 0; j < particles.size(); j++) {
            infoParticles[j] = particles.get(j).format();
        }

        Arrays.sort(infoParticles,Comparator.comparingInt((int[] p)-> p[0])
            .thenComparingInt(p -> p[1])
            .thenComparingInt(p -> p[2])
            .thenComparingInt(p -> p[3]));
        Ok = true;
        return infoParticles;

    }

    /**
     * Metodo para consultar los agujeros del juego
     */
    public int[][] holes() {
            Ok = false;
            int[][] infoHoles = new int[holes.size()][2];

            for (int j = 0; j < holes.size(); j++) {
                infoHoles[j] = holes.get(j).format();
            }
            Arrays.sort(infoHoles,Comparator.comparingInt((int[] h)-> h[0])
                .thenComparingInt(h -> h[1]));
            Ok = true;

            return infoHoles;
    }

    /**
     * Metodo para hacer visible el juego
     */
    public void makeVisible() {
        Ok = false;
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
        Ok = true;
    }

    /**
     * Metodo para hacer invisible el juego
     */
    public void makeInvisible() {
        Ok = false;
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

    /**
     * metodo para finalizar el juego
     */
    public void finish() {

        Ok = false;
        makeInvisible();
        demons.clear();
        holes.clear();
        particles.clear();
        coloresUsados.clear();
        Ok = true;
    }

    /**
     * metodo para iniciar el juego con un numero de ticks dado
     * @param ticks : numero de movimientos que puede hacer cada particula
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
                    if (verificarChoqueAgujero(p)) {
                        paraEliminar.add(p);
                    }
                    for (int j = 0; j < 2; j++) {
                        boolean afectadaPorDemonio = pasarPorDemonios(p); 

                        if (!afectadaPorDemonio && !verificarChoqueAgujero(p)) { 
                            p.moveV(w, h);
                        }

                        if (verificarChoqueAgujero(p) ){
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
    /**
     * Metodo para verificar si una particula ha pasado por un agujero
     * @param particula
     * @return
     */
    private boolean verificarChoqueAgujero(Particle p) {
        if(p instanceof Flying){
            return false;
        }
        for (Hole ho : holes) {
            if (ho.pasar(p)) {
                return true; 
            }
        }
        return false;
    }
    /**
     * Metodo para verificar si una particula ha pasado por un demonio
     * @param particle
     * @return
     */
    private boolean pasarPorDemonios(Particle p) {
        boolean afectada= false;

        for (Demon d : demons) {
            if( d.pasar(p,w,h)){
                afectada= true;
                if(d instanceof Weak){
                    demons.remove(d);
                }
                break;
            }
        }
        return afectada;
    }
    /**
    * metodo para verificar si la ultima operación es válida
    */
    public boolean Ok() {
        return Ok;
    }
    /**
     * Metodo para validar las dimensiones del contenedor
     * @param h
     * @param w
     * @return
     */
    private boolean validarDimension(int h, int w) {
        return (1 < h && h <= 300 && 1 < w && w < 300);
    }

    /**
     * metodo para retornar si el contenedor es visible
     * @return boolean
     */
    public static boolean getVisible() {
        return esVisible;
    }

    /**
     * metodo para retornar  un color al azar para la particula
     */
    private String getColor() {
        List<String> colores = Arrays.asList("red", "blue", "green", "magenta", "white", "orange", "cyan", "pink", "gray",
                "darkgray", "lightgray", "brown", "purple", "violet", "gold", "silver", "beige", "turquoise",
                "indigo", "maroon", "navy", "olive", "teal", "salmon", "coral", "khaki", "lavender", "orchid",
                "plum", "crimson", "chartreuse", "lime", "aquamarine", "peru", "seagreen", "slategray", "dodgerblue",
                "firebrick", "deeppink", "hotpink","rosybrown","midnightblue","sienna","peachpuff","palegreen","mistyrose",
                "darkorchid","steelblue","powderblue","neonblue");
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
