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
        prepareElementsBoard();
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
    private void prepareElementsBoard(){

        simulacionPanel = new JPanel();
        simulacionPanel.setLayout(new GridLayout(4, 5));
        
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j <= 4; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(1, 1));
                if (i == 2 && j == 2) {
                    button.setBackground(Color.MAGENTA); // demonio
                } else {
                    button.setBackground(Color.LIGHT_GRAY);
                }
                simulacionPanel.add(button);
            }
        }
        refresh();

    }

    /**
     * Metodo para preparar el panel de simulacion
     */
    private void refresh(){

        // si se crea el tablero
        aplicarButton.addActionListener(e -> {
            try {
                hTablero = Integer.parseInt(h.getText());
                wTablero = Integer.parseInt(w.getText());
        
                int separadorColumna = wTablero / 2;
                int totalColumnas = wTablero + 1;
                int totalFilas = hTablero;
        
                simulacionPanel.removeAll();
                simulacionPanel.setLayout(new GridLayout(totalFilas, totalColumnas));
        
                for (int fila = 0; fila < totalFilas; fila++) {
                    for (int col = 0; col < totalColumnas; col++) {
                        JButton button = new JButton();
                        button.setPreferredSize(new Dimension(1, 1)); 
                        if (col == separadorColumna) {
                            if (fila == totalFilas / 2) {
                                button.setBackground(Color.MAGENTA); // demonio
                            } else {
                                button.setBackground(Color.BLACK);
                                button.setEnabled(false);
                            }
                        }
        
                        simulacionPanel.add(button);
                    }
                }        
                simulacionPanel.revalidate();
                simulacionPanel.repaint();
        
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce valores válidos para h y w.");
            }
        });
    }
    /**
     * Metodo para preparar el panel de configuracion
     */
    private void prepareConfigPanel() {
        configuracionPanel = new JPanel(); 
        configuracionPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Todo en una línea
    
        hLabel = new JLabel("h:");
        wLabel = new JLabel("w:");
        rLabel = new JLabel("r:");
        bLabel = new JLabel("b:");
        oLabel = new JLabel("o:");
    
        h = new JTextField(3);
        w = new JTextField(3);
        r = new JTextField(3);
        b = new JTextField(3);
        o = new JTextField(3);
    
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

    }
    


    /**
     * Metodo para preparar el panel de estadisticas
     */
    private void prepareStatsPanel(){
        estPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
    
        estPanel.setLayout(new BoxLayout(estPanel, BoxLayout.Y_AXIS));
        correctParticlesLabel = new JLabel("Partículas correctas:");
        lostParticlesLabel = new JLabel("Partículas perdidas:");
    
        estPanel.add(correctParticlesLabel);
        estPanel.add(Box.createVerticalStrut(5));
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
     * * Método para preparar los elementos del menu
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