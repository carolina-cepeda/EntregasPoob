public class main2 {    
    public static void main(String[] args) {
            MaxwellContest mc = new MaxwellContest(150,150);
            int w = 7, h = 4, d = 1, r = 1, b = 1;
            int[][] particles1 = {
                {2, 1, 4, 1},  // Partícula roja
                {-3, 1, 2, 0}  // Partícula azul
            };
            System.out.println(mc.solve(h, w, d, r, b, particles1));
            mc.simulate(w, h, d, r, b, particles1);


            w = 4; h = 4; d = 1; r = 2; b = 2;
            int[][] particles2 = {
                {3, 1, 2, 2},   // Partícula roja
                {-2, 3, -2, -1}, // Partícula roja
                {3, 2, 1, -2},  // Partícula azul
                {-2, 2, 2, 2}   // Partícula azul
            };
            System.out.println(mc.solve(h, w, d, r, b, particles2));
            
        }
    }