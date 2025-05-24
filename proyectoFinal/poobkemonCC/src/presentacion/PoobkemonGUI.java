package presentacion;

import dominio.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class PoobkemonGUI extends JFrame{
    private Juego juego;
    private EstadoJuego estadoActual;
    private JProgressBar barraSaludJugador;
    private JProgressBar barraSaludOponente;

     // menu
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenuItem itemPausar, itemAbrir, itemGuardar, itemSalir;
    private JLabel etiquetaTurno;
/**
 * constructor de la presentación
 */
    public PoobkemonGUI() {
        juego = new Juego();
        inicializarthis();
        prepareElementsMenu();
        mostrarMenuPrincipal();
    }

    /**
     * inicializacion de marco
     */
    private void inicializarthis() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
    }

    /**
     * meotdo para cargar las imagenes de forma más sencilla 
     * @param ruta
     * @return
     */
    private ImageIcon cargarImagen(String ruta) {
        try {
            URL recurso = getClass().getResource(ruta);
            if (recurso == null) {
                System.err.println("Recurso no encontrado: " + ruta);
                return null;
            }
            return new ImageIcon(recurso);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + ruta);
            POOBkemonLog.error(e);
            return null;
        }
    }

    /**
     * metodo apra mostrar el menu inicial 
     */
    private void mostrarMenuPrincipal() {
        // Panel con imagen de fondo
        JPanel panel = new JPanel(new GridBagLayout()) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL recurso = getClass().getResource("presentation/resources/fondoInicial.png"); 
                if (recurso != null) {
                    ImageIcon icon = new ImageIcon(recurso);
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Si no hay imagen, mostrar fondo sólido
                    g.setColor(new Color(11,103,48));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
            panel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 0, 10, 0);

            JLabel titulo = new JLabel("POOBKEMON", SwingConstants.CENTER);
            titulo.setFont(new Font("Arial", Font.BOLD, 36));
            titulo.setForeground(new Color(50, 205, 50));
            panel.add(titulo, gbc);

            JButton botonModoNormal = crearBotonEstilizado("Normal");
            botonModoNormal.addActionListener(e -> mostrarSeleccionSubModo());
            panel.add(botonModoNormal, gbc);
            JButton botonModoSupervivencia = crearBotonEstilizado("Supervivencia");
            botonModoSupervivencia.addActionListener(e -> {
                try {
                    Supervivencia modoSupervivencia = new Supervivencia();
                    juego.seleccionarModoJuego(modoSupervivencia);
            
                    modoSupervivencia.prepararBatalla("jugador1","jugador2");
                    
                    iniciarBatalla();
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(PoobkemonGUI.this, 
                        "Error al iniciar modo supervivencia: " + ex.getMessage());
                }
            });
            panel.add(botonModoSupervivencia, gbc);

            prepareElementsMenu();

            this.getContentPane().removeAll();
            this.add(panel, BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
            this.setVisible(true);
}


    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(200, 50));
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private void mostrarSeleccionSubModo() {
        JDialog dialogo = new JDialog(this, "Selección de modo", true);
        dialogo.setLayout(new GridBagLayout());
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titulo = new JLabel("Elija un modo de juego", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        dialogo.add(titulo, gbc);

        JButton botonPvP = crearBotonEstilizado("Player vs Player");
        botonPvP.addActionListener(e -> {
            dialogo.dispose();
            mostrarEntradaNombresJugadores();
        });
        dialogo.add(botonPvP, gbc);

        JButton botonPvM = crearBotonEstilizado("Player vs Máquina");
        botonPvM.addActionListener(e -> {
            dialogo.dispose();
            mostrarConfiguracionJugadorVsMaquina();
        });
        dialogo.add(botonPvM, gbc);

        JButton botonMvM = crearBotonEstilizado("Máquina vs Máquina");
        botonMvM.addActionListener(e -> {
            dialogo.dispose();
            mostrarConfiguracionMaquinaVsMaquina();
        });
        dialogo.add(botonMvM, gbc);

        dialogo.setVisible(true);
    }

    private void mostrarConfiguracionMaquinaVsMaquina() {
        JDialog dialogo = new JDialog(this, "Configuración Máquina vs Máquina", true);
        dialogo.setLayout(new GridBagLayout());
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titulo = new JLabel("Seleccione los tipos de IA para las máquinas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        dialogo.add(titulo, gbc);

        JComboBox<String> tipoMaquina1 = new JComboBox<>(new String[]{"Defensiva", "Ofensiva", "Cambio", "Experta"});
        dialogo.add(new JLabel("Máquina 1:"), gbc);
        dialogo.add(tipoMaquina1, gbc);

        JComboBox<String> tipoMaquina2 = new JComboBox<>(new String[]{"Defensiva", "Ofensiva", "Cambio", "Experta"});
        dialogo.add(new JLabel("Máquina 2:"), gbc);
        dialogo.add(tipoMaquina2, gbc);

        JButton botonIniciar = crearBotonEstilizado("Iniciar Batalla");
        botonIniciar.addActionListener(e -> {
        int tipoIA1 = tipoMaquina1.getSelectedIndex() + 1;
        int tipoIA2 = tipoMaquina2.getSelectedIndex() + 1;

        try {
            Normal modoNormal = new Normal();
            juego.seleccionarModoJuego(modoNormal);
            modoNormal.setTipoJuego(3); 
            modoNormal.prepararBatalla("Máquina 1", "Máquina 2", tipoIA1, tipoIA2);

            dialogo.dispose();
            mostrarBatallaAutomatica(modoNormal);
        } catch (ExceptionPOOBkemon ex) {
            JOptionPane.showMessageDialog(dialogo, "Error al iniciar batalla: " + ex.getMessage());
        }
    });

        
        dialogo.add(botonIniciar, gbc);

        dialogo.setVisible(true);
    }


    private void mostrarBatallaAutomatica(Normal modo) {
        estadoActual = juego.obtenerEstadoActual();
        if (estadoActual == null) {
            JOptionPane.showMessageDialog(this, "Error al obtener estado del juego");
            mostrarMenuPrincipal();
            return;
        }

        JPanel panelBatalla = crearPanelBatalla();
        this.getContentPane().removeAll();
        this.add(panelBatalla);
        this.revalidate();
        this.repaint();

        Timer timer = new Timer(1500, null);
        timer.addActionListener(e -> {
            if (!juego.hayBatallaActiva()) {
                timer.stop();
                String ganador = juego.getNombreGanador(); 
            
                JOptionPane.showMessageDialog(this, "¡Batalla finalizada!\nGanador: " + ganador);
                mostrarMenuPrincipal();
                return;
            }

            try {
                Entrenador actual = juego.getEntrenadorActual();
                if (actual instanceof EntrenadorMaquina cpu) {
                    int saludJugadorAntes = estadoActual.pokemonActivo.getSalud();
                    int saludOponenteAntes = estadoActual.pokemonOponente.getSalud();
                    
                    cpu.realizarAccionAutomatica(juego);
                    juego.comenzarTurno();
                    

                    estadoActual = juego.obtenerEstadoActual();
                    

                    actualizarBarraDeSalud(barraSaludJugador, estadoActual.pokemonActivo.getSalud(), 
                        estadoActual.pokemonActivo.getSaludInicial());
                    actualizarBarraDeSalud(barraSaludOponente, estadoActual.pokemonOponente.getSalud(), 
                        estadoActual.pokemonOponente.getSaludInicial());
                    
                    if (saludJugadorAntes <= 0 || saludOponenteAntes <= 0) {
                        this.getContentPane().removeAll();
                        this.add(crearPanelBatalla());
                    }
                    this.revalidate();
                    this.repaint();
                }
            } catch (ExceptionPOOBkemon ex) {
                timer.stop();
                JOptionPane.showMessageDialog(this, " " + ex.getMessage());
                mostrarMenuPrincipal();
            }
        });

        timer.start();
}



    private void mostrarConfiguracionJugadorVsMaquina() {
        JDialog dialogo = new JDialog(this, "Configuración Player vs Máquina", true);
        dialogo.setLayout(new GridBagLayout());
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titulo = new JLabel("Seleccione el tipo de IA para la máquina", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        dialogo.add(titulo, gbc);

        JComboBox<String> tipoMaquina = new JComboBox<>(new String[]{"Defensiva", "Ofensiva", "Cambio", "Experta"});
        dialogo.add(new JLabel("Máquina:"), gbc);
        dialogo.add(tipoMaquina, gbc);

        JTextField campoNombreJugador = new JTextField();
        dialogo.add(new JLabel("Nombre del Jugador:"), gbc);
        dialogo.add(campoNombreJugador, gbc);

        JButton botonIniciar = crearBotonEstilizado("Iniciar Batalla");
        
        botonIniciar.addActionListener(e -> {
            int tipoIA = tipoMaquina.getSelectedIndex() + 1;
            String nombreJugador = campoNombreJugador.getText().trim();

            if (nombreJugador.isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "El nombre del jugador no puede estar vacío.");
                return;
            }

                Normal modoNormal = new Normal();
                juego.seleccionarModoJuego(modoNormal);
                modoNormal.setTipoJuego(2); // PvM
                dialogo.dispose();
                mostrarSeleccionPokemonPvM(nombreJugador, tipoIA);
        });
        dialogo.add(botonIniciar, gbc);

        dialogo.setVisible(true);
    }

    private void mostrarSeleccionPokemonPvM(String nombreJugador, int tipoIA) {
        JDialog dialogo = new JDialog(this, "Selecciona tus Pokémon", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(this);

        DefaultListModel<Pokemon> modeloLista = new DefaultListModel<>();
        JList<Pokemon> listaPokemon = new JList<>(modeloLista);
        listaPokemon.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaPokemon.setCellRenderer(new RenderizadorListaPokemon());

        for (Pokemon p : juego.getPokemonesBaseCopia()) {
            modeloLista.addElement(p);
        }

        JScrollPane scrollPane = new JScrollPane(listaPokemon);
        JButton botonAceptar = new JButton("Aceptar");

        botonAceptar.addActionListener(e -> {
            List<Pokemon> seleccionados = listaPokemon.getSelectedValuesList();
            if (seleccionados.size() != 6) {
                JOptionPane.showMessageDialog(dialogo, "Debes seleccionar exactamente 6 Pokémon.");
                return;
            }
            dialogo.dispose();
            mostrarSeleccionItemsPvM(nombreJugador, tipoIA, seleccionados);
        });

        dialogo.add(scrollPane, BorderLayout.CENTER);
        dialogo.add(botonAceptar, BorderLayout.SOUTH);
        dialogo.setVisible(true);
}


private void mostrarSeleccionItemsPvM(String nombreJugador, int tipoIA, List<Pokemon> pokemonesSeleccionados) {
    JDialog dialogo = new JDialog(this, "Selecciona tus ítems", true);
    dialogo.setLayout(new BorderLayout());
    dialogo.setSize(400, 300);
    dialogo.setLocationRelativeTo(this);

    JPanel panelItems = new JPanel(new GridLayout(5, 2));
    JLabel labelMensaje = new JLabel("Elige hasta 2 de cada tipo de poción y 1 Revive");

    Map<String, Integer> contador = new HashMap<>();
    contador.put("Potion", 0);
    contador.put("SuperPotion", 0);
    contador.put("HyperPotion", 0);
    contador.put("Revive", 0);

    JComboBox<String> comboItems = new JComboBox<>(new String[]{"Potion", "SuperPotion", "HyperPotion", "Revive"});
    DefaultListModel<String> modeloLista = new DefaultListModel<>();
    JList<String> listaSeleccion = new JList<>(modeloLista);
    JScrollPane scroll = new JScrollPane(listaSeleccion);

    JButton botonAgregar = new JButton("Agregar");
    botonAgregar.addActionListener(e -> {
        String tipo = (String) comboItems.getSelectedItem();
        int max = tipo.equals("Revive") ? 1 : 2;
        if (contador.get(tipo) < max) {
            contador.put(tipo, contador.get(tipo) + 1);
            modeloLista.addElement(tipo);
        } else {
            JOptionPane.showMessageDialog(dialogo, "Ya seleccionaste el máximo de " + tipo);
        }
    });

    JButton botonFinalizar = new JButton("Finalizar");
    botonFinalizar.addActionListener(e -> {
        try {
           Normal modoNormal = new Normal();
            modoNormal.setTipoJuego(2); // PvM
            juego.seleccionarModoJuego(modoNormal);

            modoNormal.prepararBatalla(nombreJugador, "Máquina", tipoIA, 0);
            for (Pokemon p : pokemonesSeleccionados) {
                juego.agregarPokemonAEntrenador(1, p);
            }
            for (int i = 0; i < modeloLista.size(); i++) {
                juego.agregarItemAEntrenador(1, new Pocion(modeloLista.get(i)));
            }
            modoNormal.prepararBatalla(nombreJugador, "Máquina", tipoIA, 0);

            dialogo.dispose();
            iniciarBatallaPvM(modoNormal);

        } catch (ExceptionPOOBkemon ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    });

    panelItems.add(comboItems);
    panelItems.add(botonAgregar);

    dialogo.add(labelMensaje, BorderLayout.NORTH);
    dialogo.add(panelItems, BorderLayout.CENTER);
    dialogo.add(scroll, BorderLayout.EAST);
    dialogo.add(botonFinalizar, BorderLayout.SOUTH);
    dialogo.setVisible(true);
}


   private void iniciarBatallaPvM(Normal modo) {
    JPanel panel = new JPanel(new GridBagLayout());
    JLabel esperando = new JLabel("Preparando batalla...");
    esperando.setFont(new Font("Arial", Font.BOLD, 24));
    panel.add(esperando);

    this.getContentPane().removeAll();
    this.add(panel, BorderLayout.CENTER);
    this.revalidate();
    this.repaint();

    new Thread(() -> {
        try {
            modo.iniciarBatalla(); 

            SwingUtilities.invokeLater(() -> {
                try {
                    estadoActual = juego.obtenerEstadoActual();
                    if (estadoActual == null) {
                        JOptionPane.showMessageDialog(this, "Error al obtener estado del juego tras iniciar.");
                        mostrarMenuPrincipal();
                        return;
                    }

                    JPanel panelBatalla = crearPanelBatalla();
                    this.getContentPane().removeAll();
                    this.add(panelBatalla);
                    this.revalidate();
                    this.repaint();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la interfaz: " + e.getMessage());
                }
            });
        } catch (ExceptionPOOBkemon e) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Error durante la batalla: " + e.getMessage());
            });
        }
    }).start();
}

    private void mostrarEntradaNombresJugadores() {
        JDialog dialogo = new JDialog(this, "Nombres de jugadores", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(600, 300);
        dialogo.setLocationRelativeTo(this);

        // Panel principal con dos columnas
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para Jugador 1
        JPanel panelJugador1 = new JPanel(new BorderLayout());
        panelJugador1.setBorder(BorderFactory.createTitledBorder("JUGADOR 1"));

        JLabel etiquetaJugador1 = new JLabel("Escriba su nombre:");
        JTextField campoJugador1 = new JTextField();

        panelJugador1.add(etiquetaJugador1, BorderLayout.NORTH);
        panelJugador1.add(campoJugador1, BorderLayout.CENTER);

        // Panel para Jugador 2
        JPanel panelJugador2 = new JPanel(new BorderLayout());
        panelJugador2.setBorder(BorderFactory.createTitledBorder("JUGADOR 2"));

        JLabel etiquetaJugador2 = new JLabel("Escriba su nombre:");
        JTextField campoJugador2 = new JTextField();

        panelJugador2.add(etiquetaJugador2, BorderLayout.NORTH);
        panelJugador2.add(campoJugador2, BorderLayout.CENTER);

        panelPrincipal.add(panelJugador1);
        panelPrincipal.add(panelJugador2);

        // Panel inferior con botón
        JPanel panelBoton = new JPanel();
        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(e -> {
            String nombreJugador1 = campoJugador1.getText().trim();
            String nombreJugador2 = campoJugador2.getText().trim();

            if (nombreJugador1.isEmpty() || nombreJugador2.isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "Ambos jugadores deben tener un nombre");
                return;
            }
            juego.crearEntrenadores(nombreJugador1, nombreJugador2);
            dialogo.dispose();
            mostrarSeleccionPokemon(nombreJugador1, nombreJugador2);

        });
        panelBoton.add(botonAceptar);

        dialogo.add(panelPrincipal, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private void mostrarSeleccionPokemon(String nombreJugador1, String nombreJugador2) {
        JDialog dialogo = new JDialog(this, "Selección de Pokémon", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(800, 600);
        dialogo.setLocationRelativeTo(this);

        // Panel principal con dos columnas
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Listas para mantener los Pokémon seleccionados
        List<Pokemon> pokemonesSeleccionados1 = new ArrayList<>();
        List<Pokemon> pokemonesSeleccionados2 = new ArrayList<>();

        // Panel para Jugador 1
        JPanel panelJugador1 = new JPanel(new BorderLayout());
        panelJugador1.setBorder(BorderFactory.createTitledBorder(nombreJugador1));

        // Usar DefaultListModel para la lista de Pokémon
        DefaultListModel<Pokemon> modeloLista1 = new DefaultListModel<>();
        JList<Pokemon> listaPokemon1 = new JList<>(modeloLista1);

        // Configurar selección múltiple
        listaPokemon1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaPokemon1.setLayoutOrientation(JList.VERTICAL);
        listaPokemon1.setVisibleRowCount(-1);
        listaPokemon1.setCellRenderer(new RenderizadorListaPokemon());

        // Obtener Pokémon disponibles
        List<Pokemon> pokemonesDisponibles = juego.getPokemonesBaseCopia();
        for (Pokemon p : pokemonesDisponibles) {
            modeloLista1.addElement(p);
        }

        JScrollPane panelDesplazamiento1 = new JScrollPane(listaPokemon1);
        panelJugador1.add(panelDesplazamiento1, BorderLayout.CENTER);

        JLabel etiquetaConteo1 = new JLabel("0 Pokémon seleccionados");
        panelJugador1.add(etiquetaConteo1, BorderLayout.SOUTH);

        // Panel para Jugador 2 (similar al jugador 1)
        JPanel panelJugador2 = new JPanel(new BorderLayout());
        panelJugador2.setBorder(BorderFactory.createTitledBorder(nombreJugador2));

        DefaultListModel<Pokemon> modeloLista2 = new DefaultListModel<>();
        JList<Pokemon> listaPokemon2 = new JList<>(modeloLista2);
        listaPokemon2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaPokemon2.setLayoutOrientation(JList.VERTICAL);
        listaPokemon2.setVisibleRowCount(-1);
        listaPokemon2.setCellRenderer(new RenderizadorListaPokemon());

        for (Pokemon p : pokemonesDisponibles) {
            modeloLista2.addElement(p);
        }

        JScrollPane panelDesplazamiento2 = new JScrollPane(listaPokemon2);
        panelJugador2.add(panelDesplazamiento2, BorderLayout.CENTER);

        JLabel etiquetaConteo2 = new JLabel("0 Pokémon seleccionados");
        panelJugador2.add(etiquetaConteo2, BorderLayout.SOUTH);

        panelPrincipal.add(panelJugador1);
        panelPrincipal.add(panelJugador2);

        // Panel inferior con botón y mensaje
        JPanel panelInferior = new JPanel(new BorderLayout());

        JLabel etiquetaMensaje = new JLabel(" ", SwingConstants.CENTER);
        panelInferior.add(etiquetaMensaje, BorderLayout.NORTH);

        JPanel panelBoton = new JPanel();
        JButton botonAceptar = new JButton("Aceptar");

        // Listeners para actualizar las selecciones
        listaPokemon1.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pokemonesSeleccionados1.clear();
                // Obtener TODOS los Pokémon seleccionados
                pokemonesSeleccionados1.addAll(listaPokemon1.getSelectedValuesList());
                etiquetaConteo1.setText(pokemonesSeleccionados1.size() + "Pokémon seleccionados");
            }
        });

        listaPokemon2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pokemonesSeleccionados2.clear();
                pokemonesSeleccionados2.addAll(listaPokemon2.getSelectedValuesList());
                etiquetaConteo2.setText(pokemonesSeleccionados2.size() + "Pokémon seleccionados");
            }
        });

        botonAceptar.addActionListener(e -> {

            // Asignar Pokémon a los jugadores
            try {

                for (Pokemon p : pokemonesSeleccionados1) {
                    juego.agregarPokemonAEntrenador(1, p);
                }
                for (Pokemon p : pokemonesSeleccionados2) {
                    juego.agregarPokemonAEntrenador(2, p);
                }

                dialogo.dispose();
                mostrarSeleccionItems(nombreJugador1, nombreJugador2);
            } catch (ExceptionPOOBkemon ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al asignar Pokémon: " + ex.getMessage());
            }
        });

        panelBoton.add(botonAceptar);
        panelInferior.add(panelBoton, BorderLayout.SOUTH);

        dialogo.add(panelPrincipal, BorderLayout.CENTER);
        dialogo.add(panelInferior, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private void mostrarSeleccionItems(String nombreJugador1, String nombreJugador2) {
        JDialog dialogo = new JDialog(this, "Selección de Ítems", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(800, 600);
        dialogo.setLocationRelativeTo(this);

        // Panel principal con dos columnas
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para Jugador 1
        JPanel panelJugador1 = crearPanelSeleccionItems(nombreJugador1, 1);
        panelJugador1.setBorder(BorderFactory.createTitledBorder(nombreJugador1));

        // Panel para Jugador 2
        JPanel panelJugador2 = crearPanelSeleccionItems(nombreJugador2, 2);
        panelJugador2.setBorder(BorderFactory.createTitledBorder(nombreJugador2));

        panelPrincipal.add(panelJugador1);
        panelPrincipal.add(panelJugador2);

        // Botón para finalizar
        JButton botonTerminar = crearBotonEstilizado("Terminar");
        botonTerminar.addActionListener(e -> {
            dialogo.dispose();
            try {
                juego.comenzarBatalla();
                iniciarBatalla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al comenzar batalla: " + ex.getMessage());
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonTerminar);

        dialogo.add(panelPrincipal, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private JPanel crearPanelSeleccionItems(String nombreJugador, int numeroJugador) {
        JPanel panelItems = new JPanel(new BorderLayout());

        // Lista de ítems con renderizador personalizado
        DefaultListModel<Item> modeloLista = new DefaultListModel<>();
        JList<Item> listaItems = new JList<>(modeloLista);
        listaItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaItems.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderizador = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Item item) {
                    setText(item.toString());
                }
                return renderizador;
            }
        });

        // Obtener lista de ítems disponibles
        List<Item> itemsDisponibles = juego.getItemsBase();
        for (Item item : itemsDisponibles) {
            modeloLista.addElement(item);
        }

        JScrollPane panelDesplazamiento = new JScrollPane(listaItems);
        panelItems.add(panelDesplazamiento, BorderLayout.CENTER);

        // Botón para agregar ítems
        JButton botonAgregarItem = crearBotonEstilizado("Agregar");
        botonAgregarItem.addActionListener(e -> {
            List<Item> itemsSeleccionados = listaItems.getSelectedValuesList();
            if (!itemsSeleccionados.isEmpty()) {
                try {
                    for (Item item : itemsSeleccionados) {
                        juego.agregarItemAEntrenador(numeroJugador, item);
                    }
                    JOptionPane.showMessageDialog(panelItems, "Ítems agregados correctamente.");
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(panelItems, "Error al agregar ítems: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(panelItems, "Selecciona al menos un ítem.");
            }
        });

        JPanel panelBotonAgregar = new JPanel();
        panelBotonAgregar.add(botonAgregarItem);
        panelItems.add(panelBotonAgregar, BorderLayout.SOUTH);

        return panelItems;
    }

    private void iniciarBatalla() {
        estadoActual = juego.obtenerEstadoActual();
        if (estadoActual == null) {
            JOptionPane.showMessageDialog(this, "Error al obtener estado del juego");
            mostrarMenuPrincipal();
            return;
        }

        JPanel panelBatalla = crearPanelBatalla();
        this.getContentPane().removeAll();
        this.add(panelBatalla);
        this.revalidate();
        this.repaint();
    }


    private JPanel crearPanelBatalla() {
    JPanel panelBatalla = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon icon = cargarImagen("resources/fondoBatalla.png");
            if (icon != null) {
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    };

    // Panel del oponente
    JPanel panelOponente = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    panelOponente.setOpaque(false);
    panelOponente.setBorder(BorderFactory.createEmptyBorder(120, 0, 0, 0)); // top= - px


    JLabel nombrePokemonOponente = new JLabel(estadoActual.pokemonOponente.getNombre());
    nombrePokemonOponente.setFont(new Font("Arial", Font.BOLD, 14));
    nombrePokemonOponente.setForeground(Color.BLACK);
    panelOponente.add(nombrePokemonOponente);

    JLabel imagenPokemonOponente = new JLabel();
    ImageIcon iconOponente = cargarImagen("resources/" + estadoActual.pokemonOponente.getNombre().toLowerCase() + ".png");
    if (iconOponente != null) {
        imagenPokemonOponente.setIcon(iconOponente);
    } else {
        imagenPokemonOponente.setText("Imagen no encontrada");
    }
    panelOponente.add(imagenPokemonOponente);

    barraSaludOponente = new JProgressBar(0, 100);
    barraSaludOponente.setValue(estadoActual.pokemonOponente.getSalud());
    barraSaludOponente.setString(estadoActual.pokemonOponente.getSalud() + "/" + estadoActual.pokemonOponente.getSaludInicial());
    barraSaludOponente.setStringPainted(true);
    barraSaludOponente.setForeground(Color.GREEN);
    panelOponente.add(barraSaludOponente);

    panelBatalla.add(panelOponente, BorderLayout.NORTH);

    // Panel del jugador
    JPanel panelJugador = new JPanel(new BorderLayout());
    panelJugador.setOpaque(false);

    JPanel panelInfoJugador = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panelInfoJugador.setOpaque(false);

    JLabel nombrePokemonJugador = new JLabel(estadoActual.pokemonActivo.getNombre());
    nombrePokemonJugador.setFont(new Font("Arial", Font.BOLD, 14));
    nombrePokemonJugador.setForeground(Color.BLACK);
    panelInfoJugador.add(nombrePokemonJugador);

    JLabel imagenPokemonJugador = new JLabel();
    ImageIcon iconJugador = cargarImagen("resources/" + estadoActual.pokemonActivo.getNombre().toLowerCase() + ".png");
    if (iconJugador != null) {
        imagenPokemonJugador.setIcon(iconJugador);
    } else {
        imagenPokemonJugador.setText("Imagen no encontrada");
    }
    panelInfoJugador.add(imagenPokemonJugador);

    JLabel etiquetaJugador = new JLabel(estadoActual.nombreJugador);
    etiquetaJugador.setFont(new Font("Arial", Font.BOLD, 16));
    etiquetaJugador.setForeground(Color.CYAN);
    panelInfoJugador.add(etiquetaJugador);

    barraSaludJugador = new JProgressBar(0, 100);
    barraSaludJugador.setValue(estadoActual.pokemonActivo.getSalud());
    barraSaludJugador.setString(estadoActual.pokemonActivo.getSalud() + "/" + estadoActual.pokemonActivo.getSaludInicial());
    barraSaludJugador.setStringPainted(true);
    barraSaludJugador.setForeground(Color.GREEN);
    panelInfoJugador.add(barraSaludJugador);

    panelJugador.add(panelInfoJugador, BorderLayout.NORTH);

    // Panel de acciones (solo para PvP o PvM)
    if (!estadoActual.nombreJugador.toLowerCase().contains("máquina")) {

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelAcciones.setOpaque(false);

        //etiqueta turno
        etiquetaTurno = new JLabel("Turno de: " + estadoActual.nombreJugador);
        etiquetaTurno.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetaTurno.setForeground(Color.YELLOW);
        etiquetaTurno.setAlignmentX(Component.CENTER_ALIGNMENT); 
        panelAcciones.add(etiquetaTurno);
        panelAcciones.add(Box.createVerticalStrut(10)); 


        JButton botonAtacar = crearBotonEstilizado("Atacar");
        botonAtacar.setPreferredSize(new Dimension(100, 30));
        botonAtacar.addActionListener(e -> mostrarOpcionesAtaque());
        panelAcciones.add(botonAtacar);

        JButton botonItem = crearBotonEstilizado("Item");
        botonItem.setPreferredSize(new Dimension(100, 30));
        botonItem.addActionListener(e -> mostrarOpcionesItem());
        panelAcciones.add(botonItem);

        JButton botonCambiarPokemon = crearBotonEstilizado("Cambiar");
        botonCambiarPokemon.setPreferredSize(new Dimension(100, 30));
        botonCambiarPokemon.addActionListener(e -> mostrarOpcionesCambioPokemon());
        panelAcciones.add(botonCambiarPokemon);

        JButton botonHuir = crearBotonEstilizado("Huir");
        botonHuir.setPreferredSize(new Dimension(100, 30));
        botonHuir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que quieres huir?",
                    "Confirmar huida",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this,
                        estadoActual.nombreOponente + " ha ganado el juego, felicidades!!");
                mostrarMenuPrincipal();
            }
        });
        panelAcciones.add(botonHuir);

        panelJugador.add(panelAcciones, BorderLayout.SOUTH);
    }

    panelBatalla.add(panelJugador, BorderLayout.SOUTH);
    return panelBatalla;
}

    private void actualizarBarraDeSalud(JProgressBar barra, int vidaActual, int vidaMaxima) {
        barra.setValue((int) ((vidaActual / (double) vidaMaxima) * 100));
        barra.setString(vidaActual + "/" + vidaMaxima);
        barra.setForeground(vidaActual > vidaMaxima * 0.5 ? Color.GREEN : vidaActual > vidaMaxima * 0.2 ? Color.ORANGE : Color.RED);
    }

    private void mostrarOpcionesAtaque() {
    try {
        List<Movimiento> movimientos = estadoActual.pokemonActivo.getMovimientos();
        String[] opciones = movimientos.stream().map(Movimiento::getNombre).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(
            this,
            "Selecciona un movimiento:",
            "Opciones de ataque",
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (seleccion != null) {
            int indiceMovimiento = -1;
            for (int i = 0; i < movimientos.size(); i++) {
                if (movimientos.get(i).getNombre().equals(seleccion)) {
                    indiceMovimiento = i;
                    break;
                }
            }

            if (indiceMovimiento != -1) {
 
                int saludJugadorAntes = estadoActual.pokemonActivo.getSalud();
                int saludOponenteAntes = estadoActual.pokemonOponente.getSalud();

                juego.realizarAccion("atacar", indiceMovimiento);

                estadoActual = juego.obtenerEstadoActual();

                // Actualizar datos
                actualizarBarraDeSalud(barraSaludJugador, estadoActual.pokemonActivo.getSalud(), estadoActual.pokemonActivo.getSaludInicial());
                actualizarBarraDeSalud(barraSaludOponente, estadoActual.pokemonOponente.getSalud(), estadoActual.pokemonOponente.getSaludInicial());
                    if (etiquetaTurno != null) {
                                            etiquetaTurno.setText("Turno de: " + estadoActual.nombreJugador);
                                        }

                // Mostrar mensajes si algún Pokémon fue derrotado
                if (estadoActual.pokemonOponente.getSalud() <= 0) {
                    JOptionPane.showMessageDialog(this, "¡Has derrotado al Pokémon oponente!");
                } else if (estadoActual.pokemonActivo.getSalud() <= 0) {
                    JOptionPane.showMessageDialog(this, "Tu Pokémon ha sido derrotado.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Movimiento no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (ExceptionPOOBkemon e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void mostrarOpcionesItem() {
        JDialog dialogo = new JDialog(this, "Seleccionar ítem", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(300, 200);
        dialogo.setLocationRelativeTo(this);

        JLabel titulo = new JLabel("Elija uno de los siguientes ítems", SwingConstants.CENTER);
        dialogo.add(titulo, BorderLayout.NORTH);

        DefaultListModel<Item> modeloLista = new DefaultListModel<>();
        for (Item item : estadoActual.items) {
            modeloLista.addElement(item);
        }

        JList<Item> listaItems = new JList<>(modeloLista);
        listaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane panelDesplazamiento = new JScrollPane(listaItems);
        dialogo.add(panelDesplazamiento, BorderLayout.CENTER);

        JButton botonUsar = new JButton("Usar");
        botonUsar.addActionListener(e -> {
            Item seleccionado = listaItems.getSelectedValue();
            if (seleccionado != null) {
               try {
                    juego.realizarAccion("usarItem", seleccionado);

                    estadoActual = juego.obtenerEstadoActual(); 
                    if (etiquetaTurno != null) {
                        etiquetaTurno.setText("Turno de: " + estadoActual.nombreJugador);
                    }

                    dialogo.dispose();
                    actualizarPantallaBatalla();
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(dialogo, "Error al usar ítem: " + ex.getMessage());
                }

               
            } else {
                JOptionPane.showMessageDialog(dialogo, "Selecciona un ítem primero");
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonUsar);
        dialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setVisible(true);
        
    }

    private void mostrarOpcionesCambioPokemon() {
        JDialog dialogo = new JDialog(this, "Cambiar Pokémon", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);

        // Lista de Pokémon disponibles
        DefaultListModel<Pokemon> modeloLista = new DefaultListModel<>();
        JList<Pokemon> listaPokemon = new JList<>(modeloLista);
        listaPokemon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPokemon.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderizador = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Pokemon pokemon) {
                    setText(pokemon.getNombre() + " - Salud: " + pokemon.getSalud());
                }
                return renderizador;
            }
        });

        // Cargar Pokémon disponibles
        List<Pokemon> pokemonesDisponibles = estadoActual.equipo;
        for (Pokemon pokemon : pokemonesDisponibles) {
            if (pokemon.getSalud() > 0 && !pokemon.equals(estadoActual.pokemonActivo)) {
                modeloLista.addElement(pokemon);
            }
        }

        JScrollPane panelDesplazamiento = new JScrollPane(listaPokemon);
        dialogo.add(panelDesplazamiento, BorderLayout.CENTER);

        // Botón para confirmar el cambio
        JButton botonCambiar = crearBotonEstilizado("Cambiar");
        botonCambiar.addActionListener(e -> {
            Pokemon pokemonSeleccionado = listaPokemon.getSelectedValue();
            if (pokemonSeleccionado != null) {
                try {
                    juego.realizarAccion("cambiar", pokemonSeleccionado);
                    estadoActual = juego.obtenerEstadoActual();
                    dialogo.dispose();
                    actualizarPantallaBatalla();
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(dialogo, "Error al cambiar Pokémon: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(dialogo, "Selecciona un Pokémon para cambiar.");
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonCambiar);
        dialogo.add(panelBoton, BorderLayout.SOUTH);

        dialogo.setVisible(true);
    }

    private void actualizarPantallaBatalla() {
        estadoActual = juego.obtenerEstadoActual();
        
        if (estadoActual == null || !juego.hayBatallaActiva()) {
            String ganador = juego.getNombreGanador(); 
            JOptionPane.showMessageDialog(this,
                "El jugador " + ganador + " ha ganado el juego, felicidades!!");
            mostrarMenuPrincipal();
            return;
        }

        iniciarBatalla(); 
    }

    /**
     * uso de IA para manejar la lista de pokemones
     */
    private class RenderizadorListaPokemon extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Component renderizador = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Pokemon pokemon) {
                setText(pokemon.getNombre());
                setFont(new Font("Arial", Font.BOLD, 14));
            }
            return renderizador;
        }
    }

    /**
     * * Método para preparar los elementos del menu
     * 
     */
    private void prepareElementsMenu() {
        menuBar = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
    
        itemPausar = new JMenuItem("Pausar");
        itemAbrir = new JMenuItem("Abrir");
        itemGuardar = new JMenuItem("Guardar");
        itemSalir = new JMenuItem("Salir");
   

        menuArchivo.add(itemPausar);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);

        menuArchivo.add(new JSeparator()); 
        menuArchivo.add(itemSalir);
    
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
        prepareActionsMenu();

    }

     /**
     * * Método para preparar las acciones del menú.
     * se utiliza un listener para cada elemento del menú.
     */
    private void prepareActionsMenu() {
        itemPausar.addActionListener(e -> {
                if (juego != null) {
                    juego.pausar();
                    JOptionPane.showMessageDialog(this, "Juego pausado");
                }
});

        itemAbrir.addActionListener(e -> optionOpen());

        itemGuardar.addActionListener(e -> optionSave());

        itemSalir.addActionListener(e -> optionExit());
    

    }

    /**
     * metodo para abnrir un archivo
     */
    private void optionOpen() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
            Juego juegoCargado = PokemonPersistencia.cargarEstadoJuego(selectedFile.getAbsolutePath());

            if (juegoCargado != null) {
                juego = juegoCargado;
                estadoActual = juego.obtenerEstadoActual();
                if(estadoActual != null){
                    JPanel panelBatalla = crearPanelBatalla();
                    getContentPane().removeAll();
                    add(panelBatalla,BorderLayout.CENTER);
            
                revalidate();
                repaint();
                JOptionPane.showMessageDialog(this, "Archivo abierto exitosamente.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    /**
     * metodo para guardar un archivo 
     */
   private void optionSave() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                juego.guardarPartida(selectedFile.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    /**
     * metodo para salir
     */
    private void optionExit() {
        System.exit(0);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PoobkemonGUI());
    }
}