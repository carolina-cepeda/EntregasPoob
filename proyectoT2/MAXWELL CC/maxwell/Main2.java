package maxwell;

public class Main2 {
  public static void main() {
    int[][] particulas = {
        {80, 85, 2, 2},
        {-30, 100, 2, 2},
        {82, 120, 2, 2}
    };

    MaxwellContainer mc = new MaxwellContainer(200, 200, 85, 1, 1, particulas);

    mc.addParticle(new Rotator("red", true, 50, 50, 1, 1,200,200));
    mc.addParticle(new Ephemeral("blue", false, -60, 60, 2, 1,200,200));
    mc.addParticle(new Erratica("green", false, -70, 70, 1, 2,200,200));
    mc.addParticle(new Flying("magenta", true, -80, 70, 3, 3,200,200));

    mc.addHole(new Hole(-80, 80, 1,200,200)); 
    mc.addHole(new Movil(10, 10, 2,200,200));  

    mc.addDemon(new Demon(200,200,20,"null")); 
    mc.addDemon(new Blue(200,200,30));
    mc.addDemon(new Weak(200,200,40));
    mc.makeVisible();
    mc.start(200);
}
}
