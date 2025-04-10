package presentation;
import java.awt.*;
import javax.swing.*;
public class DMaxwellGUI extends JFrame {
    public DMaxwellGUI() {
        super("Maxwell Discreto");
    }

    private void prepareElements(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);

        setLocation(screenSize.width / 2 - width/2, screenSize.height / 2 - height/2);
    }
    public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.prepareElements();
        gui.setVisible(true);
    }
}