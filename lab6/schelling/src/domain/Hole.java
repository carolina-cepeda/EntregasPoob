package domain;
public class Hole implements Item{
    protected City city;
    protected int row ,column;
    /*
    * constructor de la clase
    */
    public Hole(City city, int row, int column){
        this.city = city;
        this.row = row;
        this.column = column;
    }
    /*
    * metodo para decidir vacio
    */
    @Override    
    public void decide(){

        }

    /*
    * metodo que retorna la fila en la que esta
    */
    public int getRow(){
        return row;
    }
    /*
     * metodo que retorna la columna en la que esta
     */
    public int getColumn(){
        return column;
    }
}