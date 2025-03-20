package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**Information about a person<br>
<b>(city,row,column,color)</b><br>
<br>
 */
public class Person extends Agent implements Item{
    private City city;
    private int row,column;    
    protected Color color;
    
    /**Create a new person (<b>row,column</b>) in the city <b>ac</b>..
     * @param city 
     * @param row 
     * @param column 
     */
    public Person(City city,int row, int column){
        this.city=city;
        this.row=row;
        this.column=column;
        this.city.setItem(row,column,(Item)this);    
        color=Color.blue;
    }
    

    /**Returns the row
    @return 
     */
    public final int getRow(){
        return row;
    }

    /**Returns the column
    @return 
     */
    public final int getColumn(){
        return column;
    }

    
    /**Returns the color
    @return 
     */
    public final Color getColor(){
        return color;
    }


    /**Act
     */
    public void decide(){
         state = (getSteps() % 3 == 0 ? Agent.HAPPY: (getSteps() % 3 == 1 ? Agent.INDIFFERENT: Agent.DISSATISFIED));
    }

    /**Change its actual state
     */
    @Override
    public void change() {
        step(); // Aumentar el contador de pasos

        if (state == Agent.DISSATISFIED) { 
            int[] newPos = getAEmptyLocation();
            if (newPos != null) {
                city.setItem(row, column, null); 
                row = newPos[0];
                column = newPos[1];
                city.setItem(row, column, this);
            }
        }
    }

    public int[] getAEmptyLocation() {
    int[][] direcciones = {
        {-1, 0}, {1, 0}, 
        {0, -1}, {0, 1}, 
        {-1, -1}, {-1, 1}, 
        {1, -1}, {1, 1}
    };

    List<int[]> mejoresPosiciones = new ArrayList<>();
    int maxVecinosSimilares = -1;

    for (int[] dir : direcciones) {
        int newRow = row + dir[0];
        int newCol = column + dir[1];

        if (city.isEmpty(newRow, newCol)) {
            int vecinosSimilares = city.neighborsSameMood(newRow, newCol, this);

            if (vecinosSimilares > maxVecinosSimilares) {
                maxVecinosSimilares = vecinosSimilares;
                mejoresPosiciones.clear();
                mejoresPosiciones.add(new int[]{newRow, newCol});
            } else if (vecinosSimilares == maxVecinosSimilares) {
                mejoresPosiciones.add(new int[]{newRow, newCol});
            }
        }
    }

    if (!mejoresPosiciones.isEmpty()) {
        return mejoresPosiciones.get(new Random().nextInt(mejoresPosiciones.size()));
    }

    return null; 
}
public int neighborsSameMood(int r, int c, Person p) {
    int num = 0;

    for (int dr = -1; dr <= 1; dr++) {
        for (int dc = -1; dc <= 1; dc++) {
            int newRow = r + dr;
            int newCol = c + dc;

            if ((dr != 0 || dc != 0) && inLocations(newRow, newCol) && locations[newRow][newCol] instanceof Person) {
                Person vecino = (Person) locations[newRow][newCol];
                if (vecino.getState() == p.getState()) {  
                    num++;
                }
            }
        }
    }

    return num;
}
public char getState(){
    return this.state ;
}


        
}
