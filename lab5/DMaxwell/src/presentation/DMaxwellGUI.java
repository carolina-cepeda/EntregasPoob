package presentation;
import domain.Agujero;
import domain.DMaxwell;
import domain.Demonio;
import domain.Elemento;
import domain.Particula;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
public class DMaxwellGUI extends JFrame {

    //dominio
    private DMaxwell dMaxwell;

    //paneles
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenuItem itemNuevo, itemAbrir, itemSalvar, itemSalir;
    private JPanel configuracionPanel;
    private JPanel simulacionPanel;
    private JPanel colorPanel;
    private JPanel estPanel;

    //tablero
    private JButton botonSeleccionado = null;
 
    // componentes de configuracion
    private JTextField h,w,r,b,o;
    private JLabel hLabel,wLabel,rLabel,bLabel,oLabel,colorLabel;
    private JButton aplicarButton;
    private JButton elegirColorButton;

    // componentes estadisticas
    private JLabel correctParticlesLabel;
    private JLabel lostParticlesLabel;
    private JTextArea statsTextArea;


    //datos predeterminados de la simulación
    private Color currentParticleColor = Color.RED;
    private int hTablero ;
    private int wTablero;


    private DMaxwellGUI(){
        prepareElements();
        prepareActions();

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

        //paneles
        prepareConfigPanel();
        prepareElementsBoard();
        prepareColorPanel();
        prepareStatsPanel();
        organizaPaneles();

        //menu
        prepareElementsMenu();

        
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
     * Metodo para refrescar el panel de simulacion
     */

    private void refresh() {
    aplicarButton.addActionListener(e -> {
        if (leerConfiguracion()) {
            inicializarModelo();
            dibujarTablero();
            colocarElementosEnTablero();
        }
        });
    }
    /**
     * metodo para leer la informacion dada en el panel de configuracion
     * @return
     */
    private boolean leerConfiguracion() {
        try {
            hTablero = Integer.parseInt(h.getText());
            wTablero = Integer.parseInt(w.getText());
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce valores válidos para h y w.");
            return false;
        }
    }

    private void inicializarModelo() {
        int rValor = Integer.parseInt(r.getText());
        int bValor = Integer.parseInt(b.getText());
        int oValor = Integer.parseInt(o.getText());
        dMaxwell = new DMaxwell(hTablero, wTablero, rValor, bValor, oValor);
    }

    private void dibujarTablero() {
        simulacionPanel.removeAll();
        simulacionPanel.setLayout(new GridLayout(hTablero, wTablero + 1));
        botonSeleccionado = null;
    
        int separadorColumna = wTablero / 2;
        int totalColumnas = wTablero + 1;
        int totalFilas = hTablero;
    
        for (int fila = 0; fila < totalFilas; fila++) {
            for (int col = 0; col < totalColumnas; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(1, 1));
    
                if (col == separadorColumna) {
                    if (fila == totalFilas / 2) {
                        button.setBackground(Color.GRAY); // demonio
                        button.addActionListener(e -> seleccionarCelda(button)); 
                    } else {
                        button.setBackground(Color.BLACK); // muro
                        button.setEnabled(false);
                    }
                } else {
                    button.addActionListener(f -> seleccionarCelda(button));
                }
    
                simulacionPanel.add(button);
            }
        }
    
        simulacionPanel.revalidate();
        simulacionPanel.repaint();
    }
    

    private void colocarElementosEnTablero() {
        if (dMaxwell == null) return;

        Component[] components = simulacionPanel.getComponents();
        int cols = wTablero + 1;

        for (Elemento e : dMaxwell.getElementos()) {
            int x = e.getPx();
            int y = e.getPy();
            int index = y * cols + x;

            if (index >= 0 && index < components.length) {
                JButton btn = (JButton) components[index];

                if (e instanceof Particula) {
                    Color colorPersonalizado = ((Particula) e).getColorPersonalizado();
                    if (colorPersonalizado != null) {
                        btn.setBackground(colorPersonalizado);
                    } else {
                        btn.setBackground(((Particula) e).isRed() ? Color.RED : Color.BLUE);
                    }
                } else if (e instanceof Agujero) {
                    btn.setBackground(Color.BLACK);
                } else if (e instanceof Demonio) {
                    btn.setBackground(Color.GRAY);
                }
            }
        }
    }

    private void seleccionarCelda(JButton boton) {
    if (botonSeleccionado == null) {
        botonSeleccionado = boton;
        botonSeleccionado.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
    } else {
        int indexOrigen = Arrays.asList(simulacionPanel.getComponents()).indexOf(botonSeleccionado);
        int indexDestino = Arrays.asList(simulacionPanel.getComponents()).indexOf(boton);
        int x1 = indexOrigen % (wTablero + 1);
        int y1 = indexOrigen / (wTablero + 1);
        int x2 = indexDestino % (wTablero + 1);
        int y2 = indexDestino / (wTablero + 1);

        // Solo si son adyacentes
        if ((Math.abs(x1 - x2) + Math.abs(y1 - y2)) == 1) {
            dMaxwell.moverParticula(x1, y1, x2 - x1, y2 - y1);
            dibujarTablero(); 
            colocarElementosEnTablero();
        }
        actualizarEstadisticas();
        botonSeleccionado.setBorder(null);
        botonSeleccionado = null;
    }
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
        elegirColorButton = new JButton("Elegir color...");
        colorPanel.add(colorLabel);
        colorPanel.add(elegirColorButton);
    }
    


    /**
     * Metodo para preparar el panel de estadisticas
     */
    private void prepareStatsPanel() {
        estPanel = new JPanel();
        estPanel.setLayout(new BoxLayout(estPanel, BoxLayout.Y_AXIS));
        correctParticlesLabel = new JLabel("Partículas correctas: 0%");
        lostParticlesLabel = new JLabel("Partículas perdidas: 0");
        
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
        prepareActionsColor();
        
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

    /**
     * * Método para preparar las acciones del color.
     * */
    private void prepareActionsColor() {
        elegirColorButton.addActionListener(e -> {
            if (botonSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Primero selecciona una celda del tablero.");
                return;
            }
            int indice= Arrays.asList(simulacionPanel.getComponents()).indexOf(botonSeleccionado);
            int x = indice % (wTablero + 1); // uso de ia para encontrar las coordenadas x,y
            int y = indice / (wTablero + 1);
    
            Color colorElegido = JColorChooser.showDialog(
                this, "Selecciona un color para la partícula",  currentParticleColor
            );
    
            if (colorElegido != null) {
                if (dMaxwell != null) {
                    boolean exito = dMaxwell.cambiarColorParticulaEn(x, y, colorElegido);
                    if (exito) {
                        currentParticleColor = colorElegido;
                        botonSeleccionado.setBackground(currentParticleColor);
                    } else {
                        JOptionPane.showMessageDialog(
                            this, 
                            "No hay una partícula en esta posición.", "Error", 
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
    }
    private void actualizarEstadisticas() {
        if (dMaxwell != null) {
            double porcentajeCorrectas = dMaxwell.calcularParticulasCorrectas();
            int perdidas = dMaxwell.getAfectadas();
            
            correctParticlesLabel.setText(String.format("Partículas correctas: %.1f%%", porcentajeCorrectas));
            lostParticlesLabel.setText(String.format("Partículas perdidas: %d", perdidas));
            
            statsTextArea.setText(String.format(
                "Estadísticas:\n- Correctas: %.1f%%\n- Perdidas: %d\n- Rojas: %d\n- Azules: %d",
                porcentajeCorrectas,
                perdidas,
                dMaxwell.getCantidadRojas(),
                dMaxwell.getCantidadAzules()
            ));
        }
    }
    
        public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.setVisible(true);
    }
}