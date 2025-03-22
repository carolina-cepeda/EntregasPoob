package domain;
import java.awt.Color;

public class TrafficLight implements Item {
    private int estado;
    protected City city;
    protected int row ,column;
    private final Color[] colores = {Color.red, Color.yellow,Color.green,Color.yellow};
    protected Color color;
    /*
     * constructor
     */
    public TrafficLight(City city, int row, int column) {
        this.city = city;
        this.estado = 0; 
        this.row = row;
        this.column = column;
        this.color = Color.white;
    }

    @Override
    /*
     * metodo para el aumento de los "pasos" y saber
     * en que estado de luz está
     */
    public void decide() {
        estado =(estado+1);
        if (estado == 4) estado=0;
    }

    @Override
    /*
     * metodo para saber el color del semaforo
     */
    public Color getColor() {
        color = colores[estado];
        return colores[estado];
    }
}
