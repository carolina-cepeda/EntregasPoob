public class Main {
    public static void main(){
        int[][] particulas={
            {59,85,8,3},
            {30,100,7,5},
            {82,120,4,5}
    };
     MaxwellContainer mc = new MaxwellContainer(200,200,85,1,1,particulas);
     mc.addHole(25,80,1);
     mc.makeVisible();
     mc.start(200);
}
}
