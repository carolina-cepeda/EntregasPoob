import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Write a description of class MaxwellContainer here.
 * 
 * @author Carolina Cepeda
 * @version 4/03/2025
 */
public class MaxwellContainer {
    private int demon;
    private Particle particle;
    private Canvas canvas;
    private Rectangle marco, separacion, interior;
    private ArrayList<Hole> holes = new ArrayList<>();
    private ArrayList<Particle> redParticles = new ArrayList<>();
    private ArrayList<Particle> blueParticles = new ArrayList<>();
    private ArrayList<Demon> demons = new ArrayList<>();
    private int h;
    private int w;
    private static boolean esVisible = false;
    private boolean isOk = false;

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
            String color = isRed ? "red" : "blue";

            addParticle(color, isRed, px, py, vx, vy);
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
        return (-w/2 <= px && px <=  w/2  && 0 <= py && py <= h);
    }

    /*
     * Metodo para añadir un demonio al juego
     * 
     * @param : entero d que se refiere a la posicion en y del demonio.
     */
    public void addDemon(int d) {
        int px = 0;

        if (!validarDemon(d) && validarPosition(px, d)) {

            Demon demonio = new Demon(px, d, "black",h,w);
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
        if (!demons.isEmpty()) {
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

        if (!validarParticle(px, py) && validarPosition(px, py)) {
            Particle p = new Particle(Color, isRed, px, py, vx, vy,h,w);

            if (isRed) {
                redParticles.add(p);
            }

            else {
                blueParticles.add(p);
            }

        } else {
            JOptionPane.showMessageDialog(null, "La partícula no está en una posición válida");
        }

    }

    private boolean validarParticle(int px, int py) {

        for (Particle red : redParticles) {
            if (red.EstoyAhi(px, py)) {
                return true;
            }
        }

        for (Particle blue : blueParticles) {
            if (blue.EstoyAhi(px, py)) {
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
            Hole ho = new Hole(px, py, particles,h,w);
            holes.add(ho);

        } else {
            JOptionPane.showMessageDialog(null, "La posición dada  esta fuera del rango");

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

        if (color.equals("red") && !redParticles.isEmpty()) {
            redParticles.get(0).makeInvisible();
            redParticles.remove(0);
        } else if (!blueParticles.isEmpty()) {
            blueParticles.get(0).makeInvisible();
            blueParticles.remove(0);
        } else {
            JOptionPane.showMessageDialog(null, "No hay particulas validas para eliminación.");

        }
    }

    /*
     * Metodo para saber si el juego ha terminado.
     */
    public boolean isGoal() {

        if (redParticles.isEmpty() && blueParticles.isEmpty()) {
            return true;
        }

        for (Particle pr : redParticles) {
            if (!pr.isGoalR(h, w)) {
                return false;
            }

        }
        for (Particle pb : blueParticles) {
            if (!pb.isGoalB(h, w)) {
                return false;
            }

        }

        return true;

    }

    /*
     * Metodo para consultar los demonios y sus posiciones en y
     */
    public int[] demons() {
        int[] infoDemons = new int[demons.size()];

        for (int j = 0; j < demons.size(); j++) {
            infoDemons[j] = demons.get(j).getpY();

        }

        return infoDemons;

    }

    /*
     * metodo para consultar las particulas ( su posicion y velocidad)
     */
    public int[][] particles() {
        int[][] infoParticles = new int[redParticles.size() + blueParticles.size()][4];

        for (int j = 0; j < redParticles.size(); j++) {
            infoParticles[j] = redParticles.get(j).format();
        }
        for (int i = 0; i < blueParticles.size(); i++) {
            int index = redParticles.size() + i; // Se empieza después de las rojas
            infoParticles[index] = blueParticles.get(index).format();

        }

        return infoParticles;
    }

    /*
     * Metodo para consultar los agujeros del juego
     */
    public int[][] holes() {
        int[][] infoHoles = new int[holes.size()][2];

        for (int j = 0; j < holes.size(); j++) {
            infoHoles[j] = holes.get(j).format(h,w);
        }

        return infoHoles;
    }

    /**
     * Metodo para hacer visible el juego
     */
    public void makeVisible() {
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
        for (Particle red : redParticles) {
            red.makeVisible();
        }

        for (Particle blue : blueParticles) {
            blue.makeVisible();
        }

    }

    /*
     * Metodo para hacer invisible el juego
     */
    public void makeInvisible() {
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
        for (Particle p : redParticles) {
            p.makeInvisible();
        }
        for (Particle p : blueParticles) {
            p.makeInvisible();
        }

    }

    /*
     * metodo para finalizar el juego
     */
    public void finish() {
        makeInvisible();
        demons.clear();
        holes.clear();
        redParticles.clear();
        blueParticles.clear();
    }

    public boolean ok() {
        return false;
    }

    public void start(int ticks) {
        startGame(redParticles, ticks);
        startGame(blueParticles, ticks);
    }

    private void startGame(ArrayList<Particle> particles, int ticks) {
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
                } else if (!afectadaPorDemonio) {
                    p.moveV();
                }
            }

            particles.removeAll(paraEliminar);
        }
    }
}


    public boolean isOk() {
        return isOk;
    }

    private boolean validarDimension(int h, int w) {
        return (0 < h && h <= 300 && 0 < w && w < 300);
    }

    public static boolean getVisible() {
        return esVisible;
    }
}
