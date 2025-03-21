package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solitaria extends Person{
    
    public Solitaria(City city, int row, int col){
        super(city, row, col);
        this.color = Color.PINK ;
    }

    @Override
    public void decide(){
        int vecinos = city.neighborsEquals(row, column);
        if (vecinos < 3){
            this.state = Agent.HAPPY;
        }
        else if (vecinos == 3){
            this.state = Agent.INDIFFERENT;
        }
        else{
            this.state = Agent.DISSATISFIED;
        }
    }

    @Override
    public int[] getAEmptyLocation(){
        int[][] direcciones = {
        {-1, 0}, {1, 0}, 
        {0, -1}, {0, 1}, 
        {-1, -1}, {-1, 1}, 
        {1, -1}, {1, 1}
    };

    List<int[]> mejoresPosiciones = new ArrayList<>();
    int maxVecinos = 100;
    
     for (int[] dir : direcciones) {
        int newRow = row + dir[0];
        int newCol = column + dir[1];

        if (city.isEmpty(newRow, newCol)) {
            int vecinos = city.neighborsEquals(newRow, newCol);

            if (vecinos < maxVecinos) {
                maxVecinos = vecinos;
                mejoresPosiciones.clear();
                mejoresPosiciones.add(new int[]{newRow, newCol});
            } else if (vecinos == maxVecinos) {
                mejoresPosiciones.add(new int[]{newRow, newCol});
            }
        }
    }

    if (!mejoresPosiciones.isEmpty()) {
        return mejoresPosiciones.get(new Random().nextInt(mejoresPosiciones.size()));
    }

    return null; 
}
@Override
public int shape(){
    return ROUND;
}

}
