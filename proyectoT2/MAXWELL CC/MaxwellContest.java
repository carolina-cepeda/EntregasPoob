import javax.swing.JOptionPane;

public class MaxwellContest extends MaxwellContainer {

    static final double EPSILON = 1e-9;
    static final int MAXIMO_ITERACIONES = 10000;

    public MaxwellContest(int h, int w, int d, int b, int r, int[][] particles) {
        super(h,w,d,b,r,particles);
    }

    /**
     * Calcula la posición reflejada de una partícula en un contenedor de altura h.
     */
    static double reflejo(double u, double h) {
        double mod = u % (2 * h);
        if (mod < 0) mod += 2 * h;
        return (mod <= h + EPSILON) ? mod : 2 * h - mod;
    }

    /**
     * Calcula el tiempo mínimo en el que una partícula alcanza la altura d.
     */
    static double minTiempo(int px, int py, double vx, double vy, int w, int h, int d) {
        double mejorTiempo = Double.POSITIVE_INFINITY;
        
        if (vx == 0) return mejorTiempo; 


        int pStart = (vx > 0) ? (int) Math.ceil((double) px / (2 * w)) 
                              : (int) Math.floor((double) px / (2 * w));

        int incremento = (vx > 0) ? 1 : -1;

        for (int k = pStart; k != pStart + (incremento * MAXIMO_ITERACIONES); k += incremento) {
            double tiempo = (2 * w * k - px) / vx;
            if (tiempo < 0) continue;

            double y = (Math.abs(vy) < EPSILON) ? py : reflejo(py + vy * tiempo, h);

            if (Math.abs(y - d) < EPSILON) {
                mejorTiempo = Math.min(mejorTiempo, tiempo);
                break;
            }
        }
        
        return mejorTiempo;
    }

    /**
     * Resuelve el problema y retorna el tiempo necesario o "imposible".
     */
    public static double solve(int h, int w, int d, int r, int b, int[][] particles) {
        int totalParticulas = r + b;
        double tiempoTotal = 0.0;

        for (int i = 0; i < totalParticulas; i++) {
            int px = (int) particles[i][0];
            int py = (int) particles[i][1];
            int vx = (int) particles[i][2];
            double vy = particles[i][3];

            boolean enLaIzquierda = (px < 0);
            boolean necesitaReflejo = (i < r) ? !enLaIzquierda : enLaIzquierda;

            if (necesitaReflejo) {
                double posibleTiempo = minTiempo(px, py, vx, vy, w, h, d);
                if (posibleTiempo == Double.POSITIVE_INFINITY) {
                    return -1.0 ;
                } else {
                    tiempoTotal = Math.max(tiempoTotal, posibleTiempo);
                }
            }
        }

        return Math.round(tiempoTotal * 10.0) / 10.0;
    }
    
    public void simulate(int h, int w, int d, int b, int r, int[][] particles) {
    double tiempoTotal = solve(h, w, d, r, b, particles);

    if (tiempoTotal == -1) { 
        System.out.println("no es posible hacer la simulación");
        return;
    }

    int ticks = (int) Math.ceil(tiempoTotal);
    this.makeVisible();
    this.start(ticks);

    if (this.isGoal()) {
        this.finish();
        JOptionPane.showMessageDialog(null, "El juego ha terminado.");
    }
}
}
