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
        Person Eva = new Person(this,15, 15);
    }
    
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
   

    public boolean isEmpty(int r, int c){
        return (inLocations(r,c) && locations[r][c]==null);
    }    
        
    private boolean inLocations(int r, int c){
        return ((0<=r) && (r<SIZE) && (0<=c) && (c<SIZE));
    }
    
   
    public void ticTac(){
        for(int i= 0; i < SIZE; i++){

            for (int j=0; j < SIZE; j++){
                if (locations != null){
                    locations[i][j].decide();
                }
            }
        }

        for(int i= 0; i < SIZE; i++){

            for (int j=0; j < SIZE; j++){
                if (locations != null){
                    locations[i][j].change();
                }
            }
        }
    }

}
