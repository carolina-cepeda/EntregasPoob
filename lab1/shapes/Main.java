public class Main {
    public static void main(String[] args) {
        Canvas c = Canvas.getCanvas();

        Pit pit = new Pit();
        pit.putSeeds(3);
        pit.makeVisible();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pit.makeInvisible();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
