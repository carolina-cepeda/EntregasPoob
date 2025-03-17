public class Main {
    public static void main(String[] args) {
        int[][] particlesData = {
                { 0, 100, 120, 5, 8 },
                { 0, 125, 120, 4, 7 }
        };

        MaxwellContainer container = new MaxwellContainer(150, 150, 4, 2, 2, particlesData);
        container.start(20);
        container.makeVisible();
        container.particles();
    }
}