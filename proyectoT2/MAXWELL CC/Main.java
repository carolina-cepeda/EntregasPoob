public class Main {
    public static void main(String[] args) {
        int[][] particlesData = {
                { 100, 120, 5, 8 },
                { 125, 120, 4, 7 }
            };


        MaxwellContainer container = new MaxwellContainer(150, 150, 45, 1, 1, particlesData);
        container.makeVisible();
        container.addHole(85,80,1);
        container.start(70);
    }
}