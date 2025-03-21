package domain;

/*No olviden adicionar la documentacion*/
public class City{
    static private int SIZE=25;
    private Item[][] locations;
    
    public City() {
        locations=new Item[SIZE][SIZE];
        for (int r=0;r<SIZE;r++){
            for (int c=0;c<SIZE;c++){
                locations[r][c]=null;
            }
        }
        someItems();
    }

    public int  getSize(){
        return SIZE;
    }

    public Item getItem(int r,int c){
        return locations[r][c];
    }

    public void setItem(int r, int c, Item e){
        locations[r][c]=e;
    }

    public void someItems(){   
        Person Adam = new Person(this,10, 10);
        setItem(10, 10, Adam);  
    
        Person Eva = new Person(this,15, 15);
        setItem(15, 15, Eva); 
        
        Walker messner = new Walker(this,10,15);
        setItem(10,15, messner);  
    
        Walker kukuczka = new Walker (this,10,7);
        setItem(10,7,kukuczka);

        Solitaria Cepeda = new Solitaria(this, 6, 6);
        setItem(6, 6, Cepeda);

        Solitaria Victoria = new Solitaria(this,6,10);
        setItem(6, 10, Victoria);
    }
    
    
  /*
   * metodo para revisar cuantos vecinos hay
   */
    public int neighborsEquals(int r, int c){
            int num=0;
            if (inLocations(r,c) && locations[r][c]!=null){
                for(int dr=-1; dr<2;dr++){
                    for (int dc=-1; dc<2;dc++){
                        if ((dr!=0 || dc!=0) && inLocations(r+dr,c+dc) && 
                        (locations[r+dr][c+dc]!=null) &&  (locations[r][c].getClass()==locations[r+dr][c+dc].getClass())) num++;
                    }
                }
            }
            return num;

    }

    /**
     * Cuenta cuántos vecinos en la posición (r, c) tienen el mismo estado de ánimo que la persona dada.
     */
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
    

    public boolean isEmpty(int r, int c){
        return (inLocations(r,c) && locations[r][c]==null);
    }    
        
    private boolean inLocations(int r, int c){
        return ((0<=r) && (r<SIZE) && (0<=c) && (c<SIZE));
    }
    
   /*
    * Cambia de estado la simulación
    */
    public void ticTac(){
        for(int i= 0; i < SIZE; i++){

            for (int j=0; j < SIZE; j++){
                if (locations[i][j] != null){
                    locations[i][j].decide();
                    locations[i][j].getColor();
                }
            }
        }

        for(int i= 0; i < SIZE; i++){

            for (int j=0; j < SIZE; j++){
                if (locations[i][j] != null){
                    locations[i][j].change();
                }
            }
        }
    }

}
