package presentacion;

import dominio.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PoobkemonGUI {
    private JFrame marcoPrincipal;
    private final Juego juego;
    private EstadoJuego estadoActual;
    private JProgressBar barraSaludJugador;
    private JProgressBar barraSaludOponente;

    public PoobkemonGUI() {
        juego = new Juego();
        inicializarMarcoPrincipal();
        mostrarMenuPrincipal();
    }

    private void inicializarMarcoPrincipal() {
        marcoPrincipal = new JFrame("POOBKEMON");
        marcoPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marcoPrincipal.setSize(800, 600);
        marcoPrincipal.setLayout(new BorderLayout());
        marcoPrincipal.setLocationRelativeTo(null);
    }

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
            System.err.println("Detalles del error: " + e.getMessage());
            return null;
        }
    }

    private void mostrarMenuPrincipal() {
        // Panel con imagen de fondo
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = cargarImagen("/fondoInicial.png");
                if (icon != null) {
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Si no hay imagen, mostrar fondo sólido
                    g.setColor(new Color(240, 240, 240));
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
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, gbc);

        JButton botonModoNormal = crearBotonEstilizado("Normal");
        botonModoNormal.addActionListener(e -> mostrarSeleccionSubModo());
        panel.add(botonModoNormal, gbc);

        JButton botonModoSupervivencia = crearBotonEstilizado("Supervivencia");
        botonModoSupervivencia.addActionListener(e -> {
            JOptionPane.showMessageDialog(marcoPrincipal, "Modo supervivencia no implementado aún");
        });
        panel.add(botonModoSupervivencia, gbc);

        marcoPrincipal.getContentPane().removeAll();
        marcoPrincipal.add(panel, BorderLayout.CENTER);
        marcoPrincipal.revalidate();
        marcoPrincipal.repaint();
        marcoPrincipal.setVisible(true);
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
        JDialog dialogo = new JDialog(marcoPrincipal, "Selección de modo", true);
        dialogo.setLayout(new GridBagLayout());
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(marcoPrincipal);

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
        JDialog dialogo = new JDialog(marcoPrincipal, "Configuración Máquina vs Máquina", true);
        dialogo.setLayout(new GridBagLayout());
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(marcoPrincipal);

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
                modoNormal.setTipoJuego(3); // MvM
                modoNormal.prepararBatalla("Máquina 1", "Máquina 2", tipoIA1, tipoIA2);
                modoNormal.iniciarBatalla();
            } catch (ExceptionPOOBkemon ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al iniciar batalla: " + ex.getMessage());
            }

            dialogo.dispose();
        });
        dialogo.add(botonIniciar, gbc);

        dialogo.setVisible(true);
    }

    private void mostrarConfiguracionJugadorVsMaquina() {
        JDialog dialogo = new JDialog(marcoPrincipal, "Configuración Player vs Máquina", true);
        dialogo.setLayout(new GridBagLayout());
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(marcoPrincipal);

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

            try {
                Normal modoNormal = new Normal();
                juego.seleccionarModoJuego(modoNormal);
                modoNormal.setTipoJuego(2); // PvM
                modoNormal.prepararBatalla(nombreJugador, "Máquina", tipoIA, 0);
                modoNormal.iniciarBatalla();
            } catch (ExceptionPOOBkemon ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al iniciar batalla: " + ex.getMessage());
            }

            dialogo.dispose();
        });
        dialogo.add(botonIniciar, gbc);

        dialogo.setVisible(true);
    }

    private void mostrarEntradaNombresJugadores() {
        JDialog dialogo = new JDialog(marcoPrincipal, "Nombres de jugadores", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(600, 300);
        dialogo.setLocationRelativeTo(marcoPrincipal);

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
        JDialog dialogo = new JDialog(marcoPrincipal, "Selección de Pokémon", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(800, 600);
        dialogo.setLocationRelativeTo(marcoPrincipal);

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

        JLabel etiquetaConteo1 = new JLabel("0/3 Pokémon seleccionados");
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

        JLabel etiquetaConteo2 = new JLabel("0/3 Pokémon seleccionados");
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
                etiquetaConteo1.setText(pokemonesSeleccionados1.size() + "/3 Pokémon seleccionados");
            }
        });

        listaPokemon2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                pokemonesSeleccionados2.clear();
                pokemonesSeleccionados2.addAll(listaPokemon2.getSelectedValuesList());
                etiquetaConteo2.setText(pokemonesSeleccionados2.size() + "/3 Pokémon seleccionados");
            }
        });

        botonAceptar.addActionListener(e -> {
            if (pokemonesSeleccionados1.size() < 3) {
                etiquetaMensaje.setText(nombreJugador1 + " no ha seleccionado suficientes Pokémon");
                return;
            }
            if (pokemonesSeleccionados2.size() < 3) {
                etiquetaMensaje.setText(nombreJugador2 + " no ha seleccionado suficientes Pokémon");
                return;
            }

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
        JDialog dialogo = new JDialog(marcoPrincipal, "Selección de Ítems", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(800, 600);
        dialogo.setLocationRelativeTo(marcoPrincipal);

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
            JOptionPane.showMessageDialog(marcoPrincipal, "Error al obtener estado del juego");
            mostrarMenuPrincipal();
            return;
        }

        marcoPrincipal.getContentPane().removeAll();

        // Panel principal con imagen de fondo de batalla
        JPanel panelBatalla = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = cargarImagen("/fondoBatalla.png");
                if (icon != null) {
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(240, 240, 240));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        // Panel para Pokémon del oponente (esquina superior derecha)
        JPanel panelOponente = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelOponente.setOpaque(false);

        JLabel nombrePokemonOponente = new JLabel(estadoActual.pokemonOponente.getNombre());
        nombrePokemonOponente.setFont(new Font("Arial", Font.BOLD, 14));
        nombrePokemonOponente.setForeground(Color.WHITE);
        panelOponente.add(nombrePokemonOponente);

        JLabel imagenPokemonOponente = new JLabel();
        try {
            ImageIcon icon = cargarImagen("/pokemon/" + estadoActual.pokemonOponente.getNombre().toLowerCase() + ".png");
            if (icon != null) {
                imagenPokemonOponente.setIcon(icon);
            } else {
                imagenPokemonOponente.setText("Imagen no encontrada");
            }
        } catch (Exception e) {
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

        // Panel para Pokémon del jugador (esquina inferior izquierda)
        JPanel panelJugador = new JPanel(new BorderLayout());
        panelJugador.setOpaque(false);

        JPanel panelInfoJugador = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfoJugador.setOpaque(false);

        JLabel nombrePokemonJugador = new JLabel(estadoActual.pokemonActivo.getNombre());
        nombrePokemonJugador.setFont(new Font("Arial", Font.BOLD, 14));
        nombrePokemonJugador.setForeground(Color.WHITE);
        panelInfoJugador.add(nombrePokemonJugador);

        JLabel imagenPokemonJugador = new JLabel();
        try {
            ImageIcon iconoPokemon = cargarImagen("/pokemon/" + estadoActual.pokemonActivo.getNombre().toLowerCase() + ".png");
            if (iconoPokemon != null) {
                imagenPokemonJugador.setIcon(iconoPokemon);
            } else {
                imagenPokemonJugador.setText("Imagen no encontrada");
            }
        } catch (Exception e) {
            imagenPokemonJugador.setText("Imagen no encontrada");
        }
        panelInfoJugador.add(imagenPokemonJugador);

        JLabel etiquetaJugador = new JLabel(estadoActual.nombreJugador);
        etiquetaJugador.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetaJugador.setForeground(Color.WHITE);
        panelInfoJugador.add(etiquetaJugador);

        barraSaludJugador = new JProgressBar(0, 100);
        barraSaludJugador.setValue(estadoActual.pokemonActivo.getSalud());
        barraSaludJugador.setString(estadoActual.pokemonActivo.getSalud() + "/" + estadoActual.pokemonActivo.getSaludInicial());
        barraSaludJugador.setStringPainted(true);
        barraSaludJugador.setForeground(Color.GREEN);
        panelInfoJugador.add(barraSaludJugador);

        panelJugador.add(panelInfoJugador, BorderLayout.NORTH);

        // Panel de acciones con botones más pequeños debajo de la barra de salud
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelAcciones.setOpaque(false);

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
            int opcion = JOptionPane.showConfirmDialog(marcoPrincipal,
                "¿Estás seguro de que quieres huir?",
                "Confirmar huida",
                JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(marcoPrincipal,
                    estadoActual.nombreOponente + " ha ganado el juego, felicidades!!");
                mostrarMenuPrincipal();
            }
        });
        panelAcciones.add(botonHuir);

        panelJugador.add(panelAcciones, BorderLayout.SOUTH);

        panelBatalla.add(panelJugador, BorderLayout.SOUTH);

        marcoPrincipal.add(panelBatalla);
        marcoPrincipal.revalidate();
        marcoPrincipal.repaint();
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
                marcoPrincipal,
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
                    juego.realizarAccion("atacar", indiceMovimiento);
                    estadoActual = juego.obtenerEstadoActual();

                    // Actualizar barras de salud
                    actualizarBarraDeSalud(barraSaludJugador, estadoActual.pokemonActivo.getSalud(), estadoActual.pokemonActivo.getSaludInicial());
                    actualizarBarraDeSalud(barraSaludOponente, estadoActual.pokemonOponente.getSalud(), estadoActual.pokemonOponente.getSaludInicial());

                    if (estadoActual.pokemonOponente.getSalud() <= 0) {
                        JOptionPane.showMessageDialog(marcoPrincipal, "¡Has derrotado al Pokémon oponente!");
                    } else if (estadoActual.pokemonActivo.getSalud() <= 0) {
                        JOptionPane.showMessageDialog(marcoPrincipal, "Tu Pokémon ha sido derrotado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(marcoPrincipal, "Movimiento no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (ExceptionPOOBkemon e) {
            JOptionPane.showMessageDialog(marcoPrincipal, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarOpcionesItem() {
        JDialog dialogo = new JDialog(marcoPrincipal, "Seleccionar ítem", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(300, 200);
        dialogo.setLocationRelativeTo(marcoPrincipal);

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
        JDialog dialogo = new JDialog(marcoPrincipal, "Cambiar Pokémon", true);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(marcoPrincipal);

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
            String ganador = "Jugador"; // Esto debería obtenerse del juego
            JOptionPane.showMessageDialog(marcoPrincipal,
                "El jugador " + ganador + " ha ganado el juego, felicidades!!");
            mostrarMenuPrincipal();
            return;
        }

        iniciarBatalla();
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PoobkemonGUI());
    }
}