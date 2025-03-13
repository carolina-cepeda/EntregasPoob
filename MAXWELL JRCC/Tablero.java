import java.util.ArrayList;

/**
 * Tablero del juego
 * 
 * @authors Carolina Cepeda
 * @version 10/03/25
 */
public class Tablero {
    private MaxwellContainer MaxwellContainer;
    private Canvas canvas;
    private Rectangle marco, separacion, interior;
    private ArrayList<Hole> holes;
    private ArrayList<Particle> particles;
    private ArrayList<Demon> demons;
    private int h;
    private int w;

    /**
     * Constructor for objects of class Tablero based in canvas.
     */
    public Tablero(int h, int w) {
        this.h = h;
        this.w = w;

        holes = new ArrayList<>();
        particles = new ArrayList<>();
        demons = new ArrayList<>();

        canvas = Canvas.getCanvas();

        marco = new Rectangle();
        marco.changeSize(h, w);
        marco.changeColor("red");
        interior = new Rectangle();
        interior.changeSize(h - 8, w - 8);
        interior.moveHorizontal(4);
        interior.moveVertical(4);

        separacion = new Rectangle();
        separacion.changeSize(h, 2);
        separacion.changeColor("black");
        separacion.moveHorizontal(w / 2);

    }

  public void makeVisible() {
    System.out.println("makeVisible() called");

    if (marco != null) {
        System.out.println("marco is not null");
        marco.makeVisible();
    } else {
        System.out.println("marco is NULL!");
    }

    if (interior != null) {
        System.out.println("interior is not null");
        interior.makeVisible();
    } else {
        System.out.println("interior is NULL!");
    }

    if (separacion != null) {
        System.out.println("separacion is not null");
        separacion.makeVisible();
    } else {
        System.out.println("separacion is NULL!");
    }

    if (demons != null) {
        System.out.println("demons list is initialized, size: " + demons.size());
        for (Demon d : demons) {
            if (d != null) {
                d.makeVisible();
            } else {
                System.out.println("Found NULL demon in list!");
            }
        }
    } else {
        System.out.println("demons list is NULL!");
    }

    if (holes != null) {
        System.out.println("holes list is initialized, size: " + holes.size());
        for (Hole ho : holes) {
            if (ho != null) {
                ho.makeVisible();
            } else {
                System.out.println("Found NULL hole in list!");
            }
        }
    } else {
        System.out.println("holes list is NULL!");
    }

    if (particles != null) {
        System.out.println("particles list is initialized, size: " + particles.size());
        for (Particle p : particles) {
            if (p != null) {
                p.makeVisible();
            } else {
                System.out.println("Found NULL particle in list!");
            }
        }
    } else {
        System.out.println("particles list is NULL!");
    }
}


    public void makeInvisible() {
        marco.makeInvisible();
        interior.makeInvisible();
        separacion.makeInvisible();

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

    public void addDemon(int d) {
        for (int i = 0; i < d; i++) {
            int px = separacion.getPositionX();
            int py = 100;
            Demon demonio = new Demon(px, py,"black");
            demons.add(demonio);
        }
    }

    public void delDemon(int d) {
        for (int i = 0; i < demons.size(); i++) {
            Demon demonio = demons.get(i);
            if (demonio.getpY() == d) {
                demonio.makeInvisible();
                demons.remove(i);
            }
        }
    }

    public void addParticle(String Color, int px, int py, int vx, int vy) {
        Particle p = new Particle(Color, px, py, vx, vy);
        particles.add(p);

    }

    public void addHole(int px, int py, int particles) {
        Hole ho = new Hole(px, py, particles);
        holes.add(ho);
    }

    public boolean isGoal() {
        return false;
    }

    public int[] demons() {
        int[] infoDemons = new int[demons.size()];

        for (int j = 0; j < demons.size(); j++) {
            infoDemons[j] = demons.get(j).getpY();

        }

        return infoDemons;

    }

    public int[][] particles() {
        int[][] infoParticles = new int[particles.size()][2];

        return infoParticles;
    }

    public int[][] holes() {
        int[][] infoHoles = new int[holes.size()][2];

        for (int j = 0; j < holes.size(); j++) {
            infoHoles[j][0] = holes.get(j).getpX();
            infoHoles[j][1] = holes.get(j).getpY();
        }

        return infoHoles;
    }

    public void finish() {
        makeInvisible();
        demons.clear();
        holes.clear();
        particles.clear();

    }

}
