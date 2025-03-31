package maxwell;

public class Main {
    public static void main(){
        int[][] particulas={
            {80,85,2,2},
            {-30,100,2,2},
            {82,120,2,2}
    };
     MaxwellContainer mc = new MaxwellContainer(200,200,85,1,1,particulas);
     mc.addParticle("sienna",false,-80,83,0,-3);
     mc.addHole(-80,80,1); //hole sirve
     mc.makeVisible();
     mc.start(200);
}
}
