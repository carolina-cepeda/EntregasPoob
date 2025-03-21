package domain;
import java.awt.Color;

public class TrafficLight extends Agent implements Item {
    private int step;  // 0 = Red, 1 = Yellow, 2 = Green, 3 = Yellow
    protected City city;
    protected int row ,column;
    private int luz;
    
    /*
     * constructor
     */
    public TrafficLight(City city, int row, int column) {
       
        this.step = 0; 
        this.row = row;
        this.column = column;
    }

    @Override
    /*
     * metodo para el aumento de los "pasos" y saber
     * en que estado de luz está
     */
    public void decide() {
        step += 1;
        luz = (step) % 4;
    }

    @Override
    /*
     * metodo para saber el color del semaforo
     */
    public Color getColor() {
        return switch (this.luz) {
            case 0 -> Color.red;
            case 1 -> Color.yellow;
            case 2 -> Color.green;
            case 3 -> Color.yellow;
            default -> Color.red;
        };
    }

    @Override
    /*
     * metodo para retornar la forma del semaforo
     */
    public int shape() {
        return ROUND; 
    }
}
