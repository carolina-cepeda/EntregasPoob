package domain;

import java.awt.Color;

public class Walker extends Person{
    /*
     * constructor de la persona caminante
     */
    public Walker(City city,int row, int column){

        super(city,row,column);
        this.color = Color.green ;
        this.state = Agent.INDIFFERENT;
    }

    @Override
    /*
     * Actua
     */
    public void decide(){
        if (hasNeighbors()){
            state = Agent.HAPPY;
        }
        else{
            state = Agent.INDIFFERENT;
        }
    }
    @Override
    /*
     * cambia su estado actual
     */
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
    /*
     * retorna la forma del caminante
     */
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
