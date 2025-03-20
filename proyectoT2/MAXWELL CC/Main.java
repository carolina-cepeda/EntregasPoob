public class Main {
    public static void main(String[] args) {
        int[][] particlesData = {
                { 10, 12, 5, 8 },
                { 15, 12, 4, 7 }
            };


        MaxwellContainer container = new MaxwellContainer(150, 150, 45, 1, 1, particlesData);
        container.makeVisible();
        container.addHole(8,8,1);
        container.start(40);
    }
}