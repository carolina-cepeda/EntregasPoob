public class Main2 {
    public static void main(String[] args) {
        int w = 7, h = 4, d = 1, r = 1, b = 1;
        int[][] particles1 = {
                { 2, 1, 4, 1 },
                { -3, 1, 2, 0 }
        };

        MaxwellContainer mc = new MaxwellContainer(h, w, d, r, b, particles1);

        double result = MaxwellContest.solve(h, w, d, r, b, particles1);

        int solucionDada = (int) Math.ceil(result);
        mc.makeVisible();
        mc.start(solucionDada);
    }
}