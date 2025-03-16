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
    private ArrayList<Hole> holes;
    private ArrayList<Particle> redParticles;
    private ArrayList<Particle> blueParticles;
    private ArrayList<Demon> demons;
    private int h;
    private int w;
    private boolean esVisible;
    private boolean isOk;

    /*
     * primer constructor
     */
    public MaxwellContainer(int h, int w) {

        this.h = h;
        this.w = w;
        this.esVisible = false;

        holes = new ArrayList<>();
        redParticles = new ArrayList<>();
        blueParticles = new ArrayList<>();
        demons = new ArrayList<>();

        canvas = Canvas.getCanvas();

        marco = new Rectangle();
        marco.changeSize(h, w);
        marco.changeColor("magenta");

        interior = new Rectangle();
        interior.changeSize(h - 8, w - 8);
        interior.moveHorizontal(4);
        interior.moveVertical(4);
        interior.changeColor("yellow");

        separacion = new Rectangle();
        separacion.changeSize(h, 2);
        separacion.changeColor("magenta");
        separacion.moveHorizontal(w / 2);

    }

    /*
     * Segundo constructor
     */
    public MaxwellContainer(int h, int w, int d, int b, int r, int[][] particles) {

        this.h = h;
        this.w = w;
        this.esVisible = false;

        holes = new ArrayList<>();
        redParticles = new ArrayList<>();
        blueParticles = new ArrayList<>();
        demons = new ArrayList<>();

        canvas = Canvas.getCanvas();

        marco = new Rectangle();
        marco.changeSize(h, w);
        marco.changeColor("magenta");

        interior = new Rectangle();
        interior.changeSize(h - 8, w - 8);
        interior.moveHorizontal(4);
        interior.moveVertical(4);
        interior.changeColor("yellow");

        separacion = new Rectangle();
        separacion.changeSize(h, 2);
        separacion.changeColor("magenta");
        separacion.moveHorizontal(w / 2);

        addDemon(d);

        for (int i = 0; i < particles.length; i++) {
            int px = particles[i][0];
            int py = particles[i][1];
            int vx = particles[i][2];
            int vy = particles[i][3];
            if (i < r) {
                particle = new Particle("red", px, py, vx, vy);
                redParticles.add(particle);
            } else {
                particle = new Particle("blue", px, py, vx, vy);

                blueParticles.add(particle);
            }

        }
    }

    /*
     * Metodo para añadir un demonio al juego
     * 
     * @param : entero d que se refiere a la posicion en y del demonio.
     */
    public void addDemon(int d) {
        if (!validarDemon(d)) {
            int px = separacion.getPositionX();
            if (15 <= d && d <= 7 + h) {
                Demon demonio = new Demon(px, d, "black");
                demons.add(demonio);

                if (esVisible) {
                    makeVisible();
                }

            } else {
                JOptionPane.showMessageDialog(null, "La posición dada en y esta fuera del rango");

            }

        } else {
            JOptionPane.showMessageDialog(null, "Ya existe un demonio en esa posición");

        }
    }

    private boolean validarDemon(int d) {
        for (Demon demon : demons) {
            if (demon.getpY() == d) {
                return true;
            }
        }
        return false;

    }

    /*
     * Metodo para añadir una particula al juego
     */
    public void addParticle(String Color, boolean isRed, int px, int py, int vx, int vy) {

        if (!validarParticle(px, py)) {
            if (70 <= px && px <= 70 + w && 15 <= py && py <= 15 + h) {
                Particle p = new Particle(Color, px, py, vx, vy);

                if (isRed) {
                    redParticles.add(p);
                }

                else {
                    blueParticles.add(p);
                }
            } else {
                JOptionPane.showMessageDialog(null, "La posición dada  esta fuera del rango");

            }
        } else {
            JOptionPane.showMessageDialog(null, "Ya existe una particula en esa posición");
        }

        if (esVisible) {
            makeVisible();
        }
    }

    private boolean validarParticle(int px, int py) {
        for (Particle red : redParticles) {
            if (red.getpY() == py && red.getpX() == px) {
                return true;
            }
        }
        for (Particle blue : blueParticles) {
            if (blue.getpY() == py && blue.getpX() == px) {
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
        if (70 <= px && px <= 62 + w && 15 <= py && py <= 7 + h) {
            Hole ho = new Hole(px, py, particles);
            holes.add(ho);

        } else {
            JOptionPane.showMessageDialog(null, "La posición dada  esta fuera del rango");

        }
        if (esVisible) {
            makeVisible();
        }
    }

    public void delParticle(String color) {

        if (color.equals("red") && !redParticles.isEmpty()) {
            redParticles.remove(0);
        } else if (!blueParticles.isEmpty()) {
            blueParticles.remove(0);
        } else {
            JOptionPane.showMessageDialog(null, "No hay particulas validas para eliminación.");

        }

        if (esVisible) {
            makeVisible();
        }
    }

    public void delDemon(int d) {
        if (!demons.isEmpty()) {
            for (int i = 0; i < demons.size(); i++) {
                Demon demonio = demons.get(i);
                if (demonio.getpY() == d) {
                    demonio.makeInvisible();
                    demons.remove(i);
                }
            }

            if (esVisible) {
                makeVisible();
            }
        }

    }

    public void start(int ticks) {
        startBlue(ticks);
        startRed(ticks);
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
            infoParticles[j][0] = redParticles.get(j).getpX();
            infoParticles[j][1] = redParticles.get(j).getpY();
            infoParticles[j][2] = redParticles.get(j).getvX();
            infoParticles[j][3] = redParticles.get(j).getvY();
        }
        for (int i = 0; i < blueParticles.size(); i++) {
            int index = redParticles.size() + i; // Se empieza después de las rojas
            infoParticles[index][0] = blueParticles.get(i).getpX();
            infoParticles[index][1] = blueParticles.get(i).getpY();
            infoParticles[index][2] = blueParticles.get(i).getvX();
            infoParticles[index][3] = blueParticles.get(i).getvY();
        }

        return infoParticles;
    }

    /*
     * Metodo para consultar los agujeros del juego
     */
    public int[][] holes() {
        int[][] infoHoles = new int[holes.size()][2];

        for (int j = 0; j < holes.size(); j++) {
            infoHoles[j][0] = holes.get(j).getpX();
            infoHoles[j][1] = holes.get(j).getpY();
        }

        return infoHoles;
    }

    /**
     * Metodo para hacer visible el juego
     */
    public void makeVisible() {
        esVisible = true;
        canvas.wait(20);

        if (marco != null) {

            marco.makeVisible();
        }

        if (interior != null) {
            interior.makeVisible();
        }
        if (separacion != null) {

            separacion.makeVisible();
        }

        if (demons != null) {
            for (Demon d : demons) {
                if (d != null) {
                    d.makeVisible();
                }
            }
        }

        if (holes != null) {

            for (Hole ho : holes) {
                if (ho != null) {
                    ho.makeVisible();
                }
            }
        }

        if (redParticles != null) {
            for (Particle p : redParticles) {
                if (p != null) {
                    p.makeVisible();
                }
            }
        }

        if (blueParticles != null) {
            for (Particle p : blueParticles) {
                if (p != null) {
                    p.makeVisible();
                }
            }
        }

    }

    /*
     * Metodo para hacer invisible el juego
     */
    public void makeInvisible() {
        canvas.wait(20);
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

    /*
     * metodo para iniciar el juego en particulas rojas
     */
    public void startRed(int ticks) {
        for (int i = 0; i < ticks; i++) {

            if (isGoal()) {
                finish();
                JOptionPane.showMessageDialog(null, "El juego ha terminado.");
                break;
            } else {
                List<Particle> paraEliminarRojo = new ArrayList<>();

                for (Particle p : redParticles) {
                    boolean afectadaPorDemonio = false;
                    boolean afectadaPorAgujero = false;

                    for (Demon d : demons) {
                        if (d.pasar(p)) {
                            afectadaPorDemonio = true;
                            p.pasarRojo(h, w);
                            break;
                        }
                    }

                    for (Hole ho : holes) {
                        if (ho.pasar(p)) {
                            afectadaPorAgujero = true;
                            paraEliminarRojo.add(p);
                            break;
                        }
                    }

                    if (!afectadaPorDemonio && !afectadaPorAgujero) {
                        int xAnterior = p.getpX();
                        int yAnterior = p.getpY();

                        p.moveV();

                        if (p.getpX() < 70 || p.getpX() > 70 + w || p.getpY() < 15 || p.getpY() > 15 + h) {
                            p.setpX(xAnterior);
                            p.setpY(yAnterior);
                        }
                    }

                    if (esVisible) {
                        makeVisible();
                    }
                }

                redParticles.removeAll(paraEliminarRojo);
            }
        }
    }

    public void startBlue(int ticks) {
        for (int i = 0; i < ticks; i++) {

            if (isGoal()) {
                finish();
                JOptionPane.showMessageDialog(null, "El juego ha terminado.");
                break;
            } else {

                List<Particle> paraEliminarAzul = new ArrayList<>();

                for (Particle p : blueParticles) {
                    boolean afectadaPorDemonio = false;
                    boolean afectadaPorAgujero = false;

                    for (Demon d : demons) {
                        if (d.pasar(p)) {
                            afectadaPorDemonio = true;
                            p.pasarBlue(h, w);

                            break;
                        }
                    }

                    for (Hole ho : holes) {
                        if (ho.pasar(p)) {
                            afectadaPorAgujero = true;
                            paraEliminarAzul.add(p);
                            break;
                        }
                    }

                    if (!afectadaPorDemonio && !afectadaPorAgujero) {
                        int xAnterior = p.getpX();
                        int yAnterior = p.getpY();

                        p.moveV();

                        if (p.getpX() < 70 || p.getpX() > 70 + w || p.getpY() < 15 || p.getpY() > 15 + h) {
                            p.setpX(xAnterior);
                            p.setpY(yAnterior);
                        }
                    }
                }
                blueParticles.removeAll(paraEliminarAzul);
            }

            if (esVisible) {
                makeVisible();
            }
        }
    }

}
