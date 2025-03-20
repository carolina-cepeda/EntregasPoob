package domain;

import java.awt.Color;

public class Walker extends Person{
    
    public Walker(City city,int row, int column){

        super(city,row,column);
        this.color = Color.green ;
        this.state = Agent.INDIFFERENT;
    }

    @Override
    public void decide(){
        if (hasNeighbors()){
            state = Agent.HAPPY;
        }
        else{
            state = Agent.INDIFFERENT;
        }
    }
    @Override
    public void change(){
        step();
        if (state == Agent.INDIFFERENT || state == Agent.DISSATISFIED){
            if(moveNorth()){
                state = Agent.INDIFFERENT;
            }
            else{
                state = Agent.DISSATISFIED ;
            }
        }
    }

    @Override
    public int shape(){
        return SQUARE;
    }
    


    /*
     * Método para intentar mover al caminante hacia el norte
     */
    private boolean moveNorth(){
        int newRow = row -1 ;
        if (this.city.isEmpty(newRow,column)){
            city.setItem(row,column, null);
            row = newRow ;
            city.setItem(row,column, this);
            return true;
        }
        return false;
    }

    /*
     * Verificar si tiene vecinos
     */
    private boolean hasNeighbors(){
        return city.neighborsSameMood(row, column, this) >0 ;
    }
}
