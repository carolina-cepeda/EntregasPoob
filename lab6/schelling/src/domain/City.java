package domain;

import java.io.*;


/*No olviden adicionar la documentacion*/
public class City implements Serializable{
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
    /*
    * Retorna el tamaño de la ciudad
    */
    public int  getSize(){
        return SIZE;
    }

    /*
     * retorna el item que este en la fila r y columna c
     */
    public Item getItem(int r,int c){
        return locations[r][c];
    }
    /*
     * Asigna un intem e a la fila r y columna c
     */

    public void setItem(int r, int c, Item e){
        locations[r][c]=e;
    }
    /*
     * funcion para añadir items
     */

    public void someItems(){   
      //Person Adam = new Person(this,10, 10);
        //setItem(10, 10, Adam);  
    
    //    Person Eva = new Person(this,15, 15);
      //  setItem(15, 15, Eva); 
        
        //Walker messner = new Walker(this,10,15);
        //setItem(10,15, messner);  
    
   //     Walker kukuczka = new Walker (this,10,7);
     //   setItem(10,7,kukuczka);
        
       // TrafficLight alarm = new TrafficLight(this,0,0);
        //setItem(0,0,alarm);
        
        //TrafficLight alert = new TrafficLight(this,0,24);
        //setItem(0,24,alarm);

        Solitaria Cepeda = new Solitaria(this, 24, 15);
        setItem(24, 15, Cepeda);

        Solitaria Victoria = new Solitaria(this,24,14);
        setItem(24,14,Victoria);
        
        Solitaria persona1 = new Solitaria(this, 23, 14);
        setItem(23,14,persona1);

        Solitaria persona2 = new Solitaria(this, 23, 15);
        setItem(23, 15,persona2);

        Hole agujero = new Hole(this, 12, 13);
        setItem(12, 13,agujero);

        Hole objeto2 = new Hole(this, 15, 10);
        setItem(15,10,objeto2) ;

        //Walker walker = new Walker(this, 10, 20);
        //setItem(10, 20, walker);

        Hole obstaculo = new Hole(this, 9, 20);
        setItem(9, 20, obstaculo);
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
    /*
    * Revisa si la posición está vacía
    */
    public boolean isEmpty(int r, int c){
        return (inLocations(r,c) && locations[r][c]==null);
    }    
    /*
    * revisa que la posición esté en los rangos de la ciudad
    */
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

    /**
     * metodo inicial open 00
     * @param archivo
     * @return
     * @throws CityException
     */
    public static  City open00 (File archivo) throws CityException{

        throw new CityException(CityException.ERROR_ABRIR + archivo.getName());
    }
    
    /**
     * metodo inicial save00
     * @param archivo
     * @throws CityException
     */

    public void save00 (File archivo) throws CityException{

        throw new CityException(CityException.ERROR_GUARDAR + archivo.getName());

    }

    /**
     * implementacion de save01 con serializacion 
     * @param archivo
     * @return City guardada
     * @throws CityException
     */
    public void saveo1(File archivo) throws CityException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new CityException(CityException.ERROR_GUARDAR + archivo.getName());
        }
    }

    /**
     * metodo para abrir sin mejor manejo de excepciones
     * @param archivo
     * @return
     * @throws CityException
     */

    public static City open01(File archivo) throws CityException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (City) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new CityException(CityException.ERROR_ABRIR + archivo.getName());
        }
    }

    /**
     * metodo guardar con mejor manejo de excepciones
     * @param archivo
     * @throws CityException
     */
    public void save(File archivo) throws CityException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            throw new CityException(CityException.ERROR_GUARDAR + archivo.getAbsolutePath());
        } catch (IOException e) {
            throw new CityException("Error al guardar la ciudad en " + archivo.getAbsolutePath());
        }
    }

    /**
     * metodo para abrir archivo con mejor manejo de excepciones
     * @param archivo
     * @return
     * @throws CityException
     */
    public static City open(File archivo) throws CityException {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
        return (City) ois.readObject();
    } catch (FileNotFoundException e) {
        throw new CityException(CityException.ERROR_ABRIR + archivo.getAbsolutePath());
    } catch (ClassNotFoundException e) {
        throw new CityException("Clase no encontrada al abrir el archivo: " + e.getMessage());
    } catch (IOException e) {
        throw new CityException("Error al abrir la ciudad desde " + archivo.getAbsolutePath());
    }
}





    /**
     * version inicial 
     * @param archivo
     * @return
     * @throws CityException
     */
   
    public static City importar00 (File archivo) throws CityException{

        throw new CityException(CityException.ERROR_IMPORTAR + archivo.getName());

    }

    /**
     * version inicial
     * @param archivo
     * @throws CityException
     */
    
    public void exportar00 (File archivo) throws CityException{

        throw new CityException(CityException.ERROR_EXPORTAR + archivo.getName());

    }

    /**
     * exportar 01
     * @param archivo
     * @throws CityException
     */
    public void exportar01(File archivo) throws CityException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                Item item = getItem(i, j);
                if (item != null) {
                    writer.println(item.getClass().getSimpleName() + " " + i + " " + j);
                }
            }
        }
    } catch (IOException e) {
        throw new CityException(CityException.ERROR_EXPORTAR + archivo.getName());
    }
}

    /**
     * metodo para importar un a ciudad
     * @param archivo
     * @return
     * @throws CityException
     */
    public static City importar01(File archivo) throws CityException {
    City nuevaCiudad = new City(); 
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;
            String[] partes = linea.split("\\s+");
            if (partes.length != 3) continue;

            String tipo = partes[0];
            int fila = Integer.parseInt(partes[1]);
            int columna = Integer.parseInt(partes[2]);

            switch (tipo) {
                case "Person" -> nuevaCiudad.setItem(fila, columna, new Person(nuevaCiudad, fila, columna));
                case "Walker" -> nuevaCiudad.setItem(fila, columna, new Walker(nuevaCiudad, fila, columna));
                case "Hole" -> nuevaCiudad.setItem(fila, columna, new Hole(nuevaCiudad, fila, columna));
                case "Solitaria" -> nuevaCiudad.setItem(fila, columna, new Solitaria(nuevaCiudad, fila, columna));
                case "TrafficLight" -> nuevaCiudad.setItem(fila, columna, new TrafficLight(nuevaCiudad, fila, columna));
                default -> {
                }
            }
        }
        return nuevaCiudad;
    } catch (IOException | NumberFormatException e) {
        throw new CityException(CityException.ERROR_IMPORTAR + archivo.getName());
    }
}

/*
 * metodo exportar con mejor manejo de excepciones
 */
public void exportar(File archivo) throws CityException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                Item item = getItem(i, j);
                if (item != null) {
                    writer.println(item.getClass().getSimpleName() + " " + i + " " + j);
                }
            }
        }
    } catch (IOException e) {
        throw new CityException(CityException.ERROR_EXPORTAR + archivo.getAbsolutePath());
    }
}

/**
 * metodo importar archivos con mejor manejo de excepciones
 */
public static City importar(File archivo) throws CityException {
    City nuevaCiudad = new City(); 
    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        int numLinea = 0;
        while ((linea = reader.readLine()) != null) {
            numLinea++;
            linea = linea.trim();
            if (linea.isEmpty()) continue;

            String[] partes = linea.split("\\s+");
            if (partes.length != 3) {
                throw new CityException(CityException.FORMATO_INVALIDO + numLinea + ": '" + linea + "'");
            }

            int fila, columna;
            try {
                fila = Integer.parseInt(partes[1]);
                columna = Integer.parseInt(partes[2]);
            } catch (NumberFormatException e) {
                throw new CityException(CityException.COORDENADAS_INVALIDAS + numLinea + ": '" + linea + "'");
            }

            String tipo = partes[0];
            Item nuevoItem = switch (tipo) {
                case "Person" -> new Person(nuevaCiudad, fila, columna);
                case "Walker" -> new Walker(nuevaCiudad, fila, columna);
                case "Hole" -> new Hole(nuevaCiudad, fila, columna);
                case "Solitaria" -> new Solitaria(nuevaCiudad, fila, columna);
                case "TrafficLight" -> new TrafficLight(nuevaCiudad, fila, columna);
                default -> throw new CityException(CityException.TIPO_DESCONOCIDO + numLinea + ": '" + tipo + "'");
            };

            nuevaCiudad.setItem(fila, columna, nuevoItem);
        }
        return nuevaCiudad;
    } catch (IOException e) {
        throw new CityException(CityException.ERROR_IMPORTAR + archivo.getAbsolutePath());
    }
}

    
    
}
