public class main2 {    
    public static void main(String[] args) {
            int w = 7, h = 4, d = 1, r = 1, b = 1;
            int[][] particles1 = {
                {2, 15, 4, 1},  // Partícula roja
                {-3, 15, 2, 0}  // Partícula azul
            };
            MaxwellContest mc1 = new MaxwellContest(150,150,d,r,b,particles1);
            System.out.println(mc1.solve(h, w, d, r, b, particles1));

            int[][] particlescanva = {
                {70+6, 4, 4, 1},  // Partícula roja
                {70, 4, 2, 0}  // Partícula azul
            };
            MaxwellContest mc2 = new MaxwellContest(150,150,d,r,b,particlescanva);
            mc2.simulate(w, h, d, r, b, particlescanva);


            w = 4; h = 4; d = 1; r = 2; b = 2;
            int[][] particles2 = {
                {3, 1, 2, 2},   // Partícula roja
                {-2, 3, -2, -1}, // Partícula roja
                {3, 2, 1, -2},  // Partícula azul
                {-2, 2, 2, 2}   // Partícula azul
            };

            MaxwellContest mc3 = new MaxwellContest(150,150,d,r,b,particles2);
            System.out.println(mc3.solve(h, w, d, r, b, particles2));
            
        }
    }