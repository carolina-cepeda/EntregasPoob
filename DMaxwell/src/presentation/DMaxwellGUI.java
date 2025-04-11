package presentation;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class DMaxwellGUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenuItem itemNuevo, itemAbrir, itemSalvar, itemSalir;

    public DMaxwellGUI() {
        super("Maxwell Discreto");
    }
    /**
     * * Método para preparar los elementos de la ventana.
     * Establece el tamaño y la ubicación de la ventana en la pantalla.
     */
    private void prepareElements(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocation(screenSize.width / 2 - width/2, screenSize.height / 2 - height/2);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        prepareElementsMenu();
        prepareActions();
        prepareActionsMenu();
    }


    /**
     * * Método para preparar las acciones del menú.
     * 
     */
    private void prepareElementsMenu() {
        menuBar = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
    
        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir");
        itemSalvar = new JMenuItem("Salvar");
        itemSalir = new JMenuItem("Salir");
    
        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemSalvar);
        menuArchivo.add(new JSeparator()); 
        menuArchivo.add(itemSalir);
    
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }
    

    /**
     * * Método para preparar las acciones de la ventana.
     * Agrega un listener para el evento de cierre de la ventana.
     */
     public void prepareActions() {
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                exit(); 
            }
        });

        prepareActionsMenu();
    }

    /**
     * * Método para mostrar un mensaje de advertencia al usuario.
     * Se utiliza para advertir al usuario sobre la salida de la aplicación.
     */
    public void exit() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
    /**
     * * Método para preparar las acciones del menú.
     * se utiliza un listener para cada elemento del menú.
     */
    private void prepareActionsMenu() {
        itemNuevo.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Nuevo archivo creado.");
        });
    
        itemAbrir.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrir archivo (aquí iría un JFileChooser).");
        });
    
        itemSalvar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Archivo guardado.");
        });
    
        itemSalir.addActionListener(e -> exit());
    }
    
    public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.prepareElements();
        gui.setVisible(true);
    }
}