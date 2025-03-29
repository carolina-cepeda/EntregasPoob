public class Main {
    public static void main(){
        int[][] particulas={
            {10,120,5,8},
            {20,120,6,7}
    };
     MaxwellContainer mc = new MaxwellContainer(200,200,70,1,1,particulas);
     mc.addHole(85,80,1);
     mc.makeVisible();
     mc.start(200);
}
}
