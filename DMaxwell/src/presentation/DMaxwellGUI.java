package presentation;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class DMaxwellGUI extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenuItem itemNuevo, itemAbrir, itemSalvar, itemSalir;
    private JPanel configuracionPanel;
    private JPanel simulacionPanel;
    private JPanel colorPanel;
    private JPanel estPanel;

    // componentes de configuracion
    private JTextField h,w,r,b,o;
    private JLabel hLabel,wLabel,rLabel,bLabel,oLabel,colorLabel;
    private JButton aplicarButton;
    private JComboBox<String> colorComboBox;

    // componentes estadisticas
    private JLabel correctParticlesLabel;
    private JLabel lostParticlesLabel;


    //datos predeterminados de la simulación
    private Color currentParticleColor = Color.RED;
    private int hTablero ;
    private int wTablero;
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

        //paneles
        prepareConfigPanel();
        prepareSimulationPanel();
        prepareColorPanel();
        prepareStatsPanel();
        organizaPaneles();

        //menu
        prepareElementsMenu();

        //acciones
        prepareActions();
    
        
    }
    /**
     * metodo para preparar el panel de simulacion
     */
    private void prepareSimulationPanel(){

        simulacionPanel = new JPanel();
        //simulacionPanel.setLayout(new GridLayout(this.hTablero, this.wTablero));

        for (int i = 0; i < this.hTablero; i++) { 
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(1, 1)); 
            simulacionPanel.add(button);
        }
    }
    /**
     * Metodo para preparar el panel de configuracion
     */
    private void prepareConfigPanel() {
        configuracionPanel = new JPanel(new GridLayout(6, 2, 5, 5)); // ia
    
        hLabel = new JLabel("h:");
        wLabel = new JLabel("w:");
        rLabel = new JLabel("r:");
        bLabel = new JLabel("b:");
        oLabel = new JLabel("o:");
    
        h = new JTextField(5);
        w = new JTextField(5);
        r = new JTextField(5);
        b = new JTextField(5);
        o = new JTextField(5);
    
        aplicarButton = new JButton("Aplicar");
    
        configuracionPanel.add(hLabel);
        configuracionPanel.add(h);
        configuracionPanel.add(wLabel);
        configuracionPanel.add(w);
        configuracionPanel.add(rLabel);
        configuracionPanel.add(r);
        configuracionPanel.add(bLabel);
        configuracionPanel.add(b);
        configuracionPanel.add(oLabel);
        configuracionPanel.add(o);
        configuracionPanel.add(new JLabel()); // espacio vacío
        configuracionPanel.add(aplicarButton);
        configuracionPanel.setBorder(BorderFactory.createTitledBorder("Configuración del contenedor"));
    }
    
    /**
     * Metodo para preparar el panel de color
     */
    private void prepareColorPanel(){
        colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        colorLabel = new JLabel("Color de la partícula: ");
        String[] colores = {"Rojo", "Verde", "Azul"};
        colorComboBox = new JComboBox<>(colores);
    
        colorPanel.add(colorLabel);
        colorPanel.add(colorComboBox);
        colorPanel.setBorder(BorderFactory.createTitledBorder("Color de la Partícula"));

    }
    


    /**
     * Metodo para preparar el panel de estadisticas
     */
    private void prepareStatsPanel(){
        estPanel = new JPanel();
        correctParticlesLabel = new JLabel("Partículas correctas:");
        lostParticlesLabel = new JLabel("Partículas perdidas:");

        estPanel.add(correctParticlesLabel);
        estPanel.add(lostParticlesLabel);
        estPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas"));

    }

    /**
     * * Método para organizar los paneles en la ventana.
     * Utiliza un layout de tipo GridLayout para organizar los paneles.
     */
    private void organizaPaneles(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    
        mainPanel.add(simulacionPanel);
        mainPanel.add(configuracionPanel);
        mainPanel.add(colorPanel);
        mainPanel.add(estPanel);
    
        add(mainPanel, BorderLayout.CENTER);
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

        // aplicacion de la configuracion refresh??
        aplicarButton.addActionListener(e -> {
            try {
                hTablero = Integer.parseInt(h.getText());
                wTablero = Integer.parseInt(w.getText());
        
                simulacionPanel.removeAll(); // limpia botones anteriores
                simulacionPanel.setLayout(new GridLayout(hTablero, wTablero));
        
                for (int i = 0; i < hTablero * wTablero; i++) {
                    JButton button = new JButton();
                    button.setPreferredSize(new Dimension(30, 30));
                    simulacionPanel.add(button);
                }
        
                simulacionPanel.revalidate();
                simulacionPanel.repaint();
        
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce valores válidos para h y w.");
            }
        });  
        
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
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getName();
                JOptionPane.showMessageDialog(this,
                    "Funcionalidad de abrir en construcción.\nArchivo seleccionado: " + fileName,
                    "Abrir archivo",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
    
        itemSalvar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getName();
                JOptionPane.showMessageDialog(this,
                    "Funcionalidad de guardar en construcción.\nArchivo a guardar: " + fileName,
                    "Salvar archivo",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
    
        itemSalir.addActionListener(e -> exit());
    }
        public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.prepareElements();
        gui.setVisible(true);
    }
}