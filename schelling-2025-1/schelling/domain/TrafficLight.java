package domain;
import java.awt.Color;

public class TrafficLight extends Agent implements Item {
    private int luz;  // 0 = Red, 1 = Yellow, 2 = Green, 3 = Yellow
    protected City city;
    protected int row ,column;
    public TrafficLight(City city, int row, int column) {
       
        this.luz = 0; 
    }

    @Override
    public void decide() {
        l = (luz + 1) % 4;
    }

    @Override
    public Color getColor() {
        return switch (this.luz) {
            case 0 -> Color.RED;
            case 1 -> Color.YELLOW;
            case 2 -> Color.GREEN;
            case 3 -> Color.YELLOW;
            default -> Color.BLACK;
        };
    }

    @Override
    public int shape() {
        return ROUND; 
    }
}
