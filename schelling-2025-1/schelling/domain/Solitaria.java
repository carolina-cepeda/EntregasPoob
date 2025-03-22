package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Solitaria extends Person{
    /*
     * Constructor persona Solitaria
     * @param City city, int row, int column
     */
    public Solitaria(City city, int row, int col){
        super(city, row, col);
        this.color = Color.pink ;
    }

    /*
     * Metodo que decide el estado de la persona solitaria
     */
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
    /*
     * Metodo que cambia la posición de la persona si no está feliz
     */
    @Override
    public void change() {
        step(); 
        if (state == Agent.DISSATISFIED || state == Agent.INDIFFERENT) { 
            int[] newPos = getAEmptyLocation();
            if (newPos != null) {
                city.setItem(row, column, null); 
                row = newPos[0];
                column = newPos[1];
                city.setItem(row, column, this);
            }
        }
    }
    /*
     * metodo que retorna un arreglo de posiciones vacias al rededor de la persona a las
     * que se puede mover.
     */
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
}
