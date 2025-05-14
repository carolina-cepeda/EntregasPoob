package presentacion;

import dominio.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PoobkemonGUI {
    private JFrame mainFrame;
    private final Juego juego;
    private EstadoJuego estadoActual;
    private JProgressBar playerHealthBar;
    private JProgressBar opponentHealthBar;

    public PoobkemonGUI() {
        juego = new Juego();
        initializeMainFrame();
        showMainMenu();
    }

    private void initializeMainFrame() {
        mainFrame = new JFrame("POOBKEMON");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);
    }

    private ImageIcon loadImage(String path) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                System.err.println("Recurso no encontrado: " + path);
                return null;
            }
            return new ImageIcon(resource);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + path);
            System.err.println("Detalles del error: " + e.getMessage());
            return null;
        }
    }

    private void showMainMenu() {
        // Panel con imagen de fondo
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = loadImage("/fondoInicial.png");
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

        JLabel title = new JLabel("POOBKEMON", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        panel.add(title, gbc);

        JButton normalModeBtn = createStyledButton("Normal");
        normalModeBtn.addActionListener(e -> showSubModeSelection());
        panel.add(normalModeBtn, gbc);

        JButton survivalModeBtn = createStyledButton("Supervivencia");
        survivalModeBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainFrame, "Modo supervivencia no implementado aún");
        });
        panel.add(survivalModeBtn, gbc);

        mainFrame.getContentPane().removeAll();
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void showSubModeSelection() {
        JDialog dialog = new JDialog(mainFrame, "Selección de modo", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Elija un modo de juego", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        dialog.add(title, gbc);

        JButton pvpBtn = createStyledButton("Player vs Player");
        pvpBtn.addActionListener(e -> {
            dialog.dispose();
            showPlayerNamesInput();
        });
        dialog.add(pvpBtn, gbc);

        JButton pvcBtn = createStyledButton("Player vs Máquina");
        pvcBtn.addActionListener(e -> {
            dialog.dispose();
            showPlayerVsMachineSetup();
        });
        dialog.add(pvcBtn, gbc);

        JButton mvmBtn = createStyledButton("Máquina vs Máquina");
        mvmBtn.addActionListener(e -> {
            dialog.dispose();
            showMachineVsMachineSetup();
        });
        dialog.add(mvmBtn, gbc);

        dialog.setVisible(true);
    }

    private void showMachineVsMachineSetup() {
        JDialog dialog = new JDialog(mainFrame, "Configuración Máquina vs Máquina", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Seleccione los tipos de IA para las máquinas", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        dialog.add(title, gbc);

        JComboBox<String> machine1Type = new JComboBox<>(new String[]{"Defensiva", "Ofensiva", "Cambio", "Experta"});
        dialog.add(new JLabel("Máquina 1:"), gbc);
        dialog.add(machine1Type, gbc);

        JComboBox<String> machine2Type = new JComboBox<>(new String[]{"Defensiva", "Ofensiva", "Cambio", "Experta"});
        dialog.add(new JLabel("Máquina 2:"), gbc);
        dialog.add(machine2Type, gbc);

        JButton startBtn = createStyledButton("Iniciar Batalla");
        startBtn.addActionListener(e -> {
            int tipoIA1 = machine1Type.getSelectedIndex() + 1;
            int tipoIA2 = machine2Type.getSelectedIndex() + 1;

            try {
                Normal modoNormal = new Normal();
                juego.seleccionarModoJuego(modoNormal);
                modoNormal.setTipoJuego(3); // MvM
                modoNormal.prepararBatalla("Máquina 1", "Máquina 2", tipoIA1, tipoIA2);
                modoNormal.iniciarBatalla();
            } catch (ExceptionPOOBkemon ex) {
                JOptionPane.showMessageDialog(dialog, "Error al iniciar batalla: " + ex.getMessage());
            }

            dialog.dispose();
        });
        dialog.add(startBtn, gbc);

        dialog.setVisible(true);
    }

    private void showPlayerVsMachineSetup() {
        JDialog dialog = new JDialog(mainFrame, "Configuración Player vs Máquina", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Seleccione el tipo de IA para la máquina", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        dialog.add(title, gbc);

        JComboBox<String> machineType = new JComboBox<>(new String[]{"Defensiva", "Ofensiva", "Cambio", "Experta"});
        dialog.add(new JLabel("Máquina:"), gbc);
        dialog.add(machineType, gbc);

        JTextField playerNameField = new JTextField();
        dialog.add(new JLabel("Nombre del Jugador:"), gbc);
        dialog.add(playerNameField, gbc);

        JButton startBtn = createStyledButton("Iniciar Batalla");
        startBtn.addActionListener(e -> {
            int tipoIA = machineType.getSelectedIndex() + 1;
            String playerName = playerNameField.getText().trim();

            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "El nombre del jugador no puede estar vacío.");
                return;
            }

            try {
                Normal modoNormal = new Normal();
                juego.seleccionarModoJuego(modoNormal);
                modoNormal.setTipoJuego(2); // PvM
                modoNormal.prepararBatalla(playerName, "Máquina", tipoIA, 0);
                modoNormal.iniciarBatalla();
            } catch (ExceptionPOOBkemon ex) {
                JOptionPane.showMessageDialog(dialog, "Error al iniciar batalla: " + ex.getMessage());
            }

            dialog.dispose();
        });
        dialog.add(startBtn, gbc);

        dialog.setVisible(true);
    }

    private void showPlayerNamesInput() {
        JDialog dialog = new JDialog(mainFrame, "Nombres de jugadores", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 300);
        dialog.setLocationRelativeTo(mainFrame);

        // Panel principal con dos columnas
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para Jugador 1
        JPanel player1Panel = new JPanel(new BorderLayout());
        player1Panel.setBorder(BorderFactory.createTitledBorder("JUGADOR 1"));

        JLabel player1Label = new JLabel("Escriba su nombre:");
        JTextField player1Field = new JTextField();

        player1Panel.add(player1Label, BorderLayout.NORTH);
        player1Panel.add(player1Field, BorderLayout.CENTER);

        // Panel para Jugador 2
        JPanel player2Panel = new JPanel(new BorderLayout());
        player2Panel.setBorder(BorderFactory.createTitledBorder("JUGADOR 2"));

        JLabel player2Label = new JLabel("Escriba su nombre:");
        JTextField player2Field = new JTextField();

        player2Panel.add(player2Label, BorderLayout.NORTH);
        player2Panel.add(player2Field, BorderLayout.CENTER);

        mainPanel.add(player1Panel);
        mainPanel.add(player2Panel);

        // Panel inferior con botón
        JPanel buttonPanel = new JPanel();
        JButton acceptBtn = new JButton("Aceptar");
        acceptBtn.addActionListener(e -> {
            String player1Name = player1Field.getText().trim();
            String player2Name = player2Field.getText().trim();

            if (player1Name.isEmpty() || player2Name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Ambos jugadores deben tener un nombre");
                return;
            }
            juego.crearEntrenadores(player1Name, player2Name);
            dialog.dispose();
            showPokemonSelection(player1Name, player2Name);

        });
        buttonPanel.add(acceptBtn);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showPokemonSelection(String player1Name, String player2Name) {
        JDialog dialog = new JDialog(mainFrame, "Selección de Pokémon", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(mainFrame);

        // Panel principal con dos columnas
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Listas para mantener los Pokémon seleccionados
        List<Pokemon> selectedPokemons1 = new ArrayList<>();
        List<Pokemon> selectedPokemons2 = new ArrayList<>();

        // Panel para Jugador 1
        JPanel player1Panel = new JPanel(new BorderLayout());
        player1Panel.setBorder(BorderFactory.createTitledBorder(player1Name));

        // Usar DefaultListModel para la lista de Pokémon
        DefaultListModel<Pokemon> listModel1 = new DefaultListModel<>();
        JList<Pokemon> pokemonList1 = new JList<>(listModel1);

        // Configurar selección múltiple
        pokemonList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        pokemonList1.setLayoutOrientation(JList.VERTICAL);
        pokemonList1.setVisibleRowCount(-1);
        pokemonList1.setCellRenderer(new PokemonListRenderer());

        // Obtener Pokémon disponibles
        List<Pokemon> availablePokemons = juego.getPokemonesBaseCopia();
        for (Pokemon p : availablePokemons) {
            listModel1.addElement(p);
        }

        JScrollPane scrollPane1 = new JScrollPane(pokemonList1);
        player1Panel.add(scrollPane1, BorderLayout.CENTER);

        JLabel countLabel1 = new JLabel("0/3 Pokémon seleccionados");
        player1Panel.add(countLabel1, BorderLayout.SOUTH);

        // Panel para Jugador 2 (similar al jugador 1)
        JPanel player2Panel = new JPanel(new BorderLayout());
        player2Panel.setBorder(BorderFactory.createTitledBorder(player2Name));

        DefaultListModel<Pokemon> listModel2 = new DefaultListModel<>();
        JList<Pokemon> pokemonList2 = new JList<>(listModel2);
        pokemonList2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        pokemonList2.setLayoutOrientation(JList.VERTICAL);
        pokemonList2.setVisibleRowCount(-1);
        pokemonList2.setCellRenderer(new PokemonListRenderer());

        for (Pokemon p : availablePokemons) {
            listModel2.addElement(p);
        }

        JScrollPane scrollPane2 = new JScrollPane(pokemonList2);
        player2Panel.add(scrollPane2, BorderLayout.CENTER);

        JLabel countLabel2 = new JLabel("0/3 Pokémon seleccionados");
        player2Panel.add(countLabel2, BorderLayout.SOUTH);

        mainPanel.add(player1Panel);
        mainPanel.add(player2Panel);

        // Panel inferior con botón y mensaje
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JLabel messageLabel = new JLabel(" ", SwingConstants.CENTER);
        bottomPanel.add(messageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton acceptBtn = new JButton("Aceptar");

        // Listeners para actualizar las selecciones
        pokemonList1.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedPokemons1.clear();
                // Obtener TODOS los Pokémon seleccionados
                selectedPokemons1.addAll(pokemonList1.getSelectedValuesList());
                countLabel1.setText(selectedPokemons1.size() + "/3 Pokémon seleccionados");
            }
        });

        pokemonList2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedPokemons2.clear();
                selectedPokemons2.addAll(pokemonList2.getSelectedValuesList());
                countLabel2.setText(selectedPokemons2.size() + "/3 Pokémon seleccionados");
            }
        });

        acceptBtn.addActionListener(e -> {
            if (selectedPokemons1.size() < 3) {
                messageLabel.setText(player1Name + " no ha seleccionado suficientes Pokémon");
                return;
            }
            if (selectedPokemons2.size() < 3) {
                messageLabel.setText(player2Name + " no ha seleccionado suficientes Pokémon");
                return;
            }

            // Asignar Pokémon a los jugadores
            try {

                for (Pokemon p : selectedPokemons1) {
                    juego.agregarPokemonAEntrenador(1, p);
                }
                for (Pokemon p : selectedPokemons2) {
                    juego.agregarPokemonAEntrenador(2, p);
                }

                dialog.dispose();
                showItemSelection(player1Name, player2Name);
            } catch (ExceptionPOOBkemon ex) {
                JOptionPane.showMessageDialog(dialog, "Error al asignar Pokémon: " + ex.getMessage());
            }
        });

        buttonPanel.add(acceptBtn);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showItemSelection(String player1Name, String player2Name) {
        JDialog dialog = new JDialog(mainFrame, "Selección de Ítems", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(mainFrame);

        // Panel principal con dos columnas
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel para Jugador 1
        JPanel player1Panel = createItemSelectionPanel(player1Name, 1);
        player1Panel.setBorder(BorderFactory.createTitledBorder(player1Name));

        // Panel para Jugador 2
        JPanel player2Panel = createItemSelectionPanel(player2Name, 2);
        player2Panel.setBorder(BorderFactory.createTitledBorder(player2Name));

        mainPanel.add(player1Panel);
        mainPanel.add(player2Panel);

        // Botón para finalizar
        JButton finishBtn = createStyledButton("Terminar");
        finishBtn.addActionListener(e -> {
            dialog.dispose();
            try {
                juego.comenzarBatalla();
                startBattle();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error al comenzar batalla: " + ex.getMessage());
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(finishBtn);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JPanel createItemSelectionPanel(String playerName, int playerNumber) {
        JPanel itemPanel = new JPanel(new BorderLayout());

        // Lista de ítems con renderizador personalizado
        DefaultListModel<Item> listModel = new DefaultListModel<>();
        JList<Item> itemList = new JList<>(listModel);
        itemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        itemList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Item item) {
                    setText(item.toString());
                }
                return renderer;
            }
        });

        // Obtener lista de ítems disponibles
        List<Item> availableItems = juego.getItemsBase();
        for (Item item : availableItems) {
            listModel.addElement(item);
        }

        JScrollPane scrollPane = new JScrollPane(itemList);
        itemPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón para agregar ítems
        JButton addItemButton = createStyledButton("Agregar");
        addItemButton.addActionListener(e -> {
            List<Item> selectedItems = itemList.getSelectedValuesList();
            if (!selectedItems.isEmpty()) {
                try {
                    for (Item item : selectedItems) {
                        juego.agregarItemAEntrenador(playerNumber, item);
                    }
                    JOptionPane.showMessageDialog(itemPanel, "Ítems agregados correctamente.");
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(itemPanel, "Error al agregar ítems: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(itemPanel, "Selecciona al menos un ítem.");
            }
        });

        JPanel addButtonPanel = new JPanel();
        addButtonPanel.add(addItemButton);
        itemPanel.add(addButtonPanel, BorderLayout.SOUTH);

        return itemPanel;
    }

    private void startBattle() {
        estadoActual = juego.obtenerEstadoActual();

        if (estadoActual == null) {
            JOptionPane.showMessageDialog(mainFrame, "Error al obtener estado del juego");
            showMainMenu();
            return;
        }

        mainFrame.getContentPane().removeAll();

        // Panel principal con imagen de fondo de batalla
        JPanel battlePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = loadImage("/fondoBatalla.png");
                if (icon != null) {
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(240, 240, 240));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        // Panel para Pokémon del oponente (esquina superior derecha)
        JPanel opponentPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        opponentPanel.setOpaque(false);
        JLabel opponentLabel = new JLabel(estadoActual.nombreOponente);
        opponentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        opponentLabel.setForeground(Color.WHITE);
        opponentPanel.add(opponentLabel);

        // Imagen del Pokémon oponente
        JLabel opponentPokemonImage = new JLabel();
        try {
            ImageIcon icon = loadImage("/pokemon/" + estadoActual.pokemonOponente.getNombre().toLowerCase() + ".png");
            if (icon != null) {
                opponentPokemonImage.setIcon(icon);
            } else {
                opponentPokemonImage.setText("Imagen no encontrada");
            }
        } catch (Exception e) {
            opponentPokemonImage.setText("Imagen no encontrada");
        }
        opponentPanel.add(opponentPokemonImage);

        // Barra de salud del oponente
        opponentHealthBar = new JProgressBar(0, 100);
        opponentHealthBar.setValue(50); // Valor por defecto
        opponentHealthBar.setString("50/100");
        opponentHealthBar.setStringPainted(true);
        opponentHealthBar.setForeground(Color.GREEN);
        opponentPanel.add(opponentHealthBar);

        battlePanel.add(opponentPanel, BorderLayout.NORTH);

        // Panel para Pokémon del jugador (esquina inferior izquierda)
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerPanel.setOpaque(false);

        // Imagen del Pokémon del jugador
        JLabel playerPokemonImage = new JLabel();
        try {
            ImageIcon pokemonIcon = loadImage("/pokemon/" + estadoActual.pokemonActivo.getNombre().toLowerCase() + ".png");
            if (pokemonIcon != null) {
                playerPokemonImage.setIcon(pokemonIcon);
            } else {
                playerPokemonImage.setText("Imagen no encontrada");
            }
        } catch (Exception e) {
            playerPokemonImage.setText("Imagen no encontrada");
        }
        playerPanel.add(playerPokemonImage);

        JLabel playerLabel = new JLabel(estadoActual.nombreJugador);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerLabel.setForeground(Color.WHITE);
        playerPanel.add(playerLabel);

        // Barra de salud del jugador
        playerHealthBar = new JProgressBar(0, 100);
        playerHealthBar.setValue(50); // Valor por defecto
        playerHealthBar.setString("50/100");
        playerHealthBar.setStringPainted(true);
        playerHealthBar.setForeground(Color.GREEN);
        playerPanel.add(playerHealthBar);

        battlePanel.add(playerPanel, BorderLayout.SOUTH);

        // Panel de acciones
        JPanel actionPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        actionPanel.setOpaque(false);

        JLabel turnLabel = new JLabel("Turno de " + estadoActual.nombreJugador, SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setForeground(Color.WHITE);
        actionPanel.add(turnLabel);

        JButton attackBtn = createStyledButton("1. Atacar");
        attackBtn.addActionListener(e -> showAttackOptions());
        actionPanel.add(attackBtn);

        JButton itemBtn = createStyledButton("2. Usar Item");
        itemBtn.addActionListener(e -> showItemOptions());
        actionPanel.add(itemBtn);

        JButton changePokemonBtn = createStyledButton("3. Cambiar Pokémon");
        changePokemonBtn.addActionListener(e -> showChangePokemonOptions());
        actionPanel.add(changePokemonBtn);

        JButton fleeBtn = createStyledButton("4. Huir");
        fleeBtn.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(mainFrame,
                "¿Estás seguro de que quieres huir?",
                "Confirmar huida",
                JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(mainFrame,
                    estadoActual.nombreOponente + " ha ganado el juego, felicidades!!");
                showMainMenu();
            }
        });
        actionPanel.add(fleeBtn);

        battlePanel.add(actionPanel, BorderLayout.CENTER);

        mainFrame.add(battlePanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void actualizarBarraDeSalud(JProgressBar barra, int vidaActual, int vidaMaxima) {
        barra.setValue((int) ((vidaActual / (double) vidaMaxima) * 100));
        barra.setString(vidaActual + "/" + vidaMaxima);
        barra.setForeground(vidaActual > vidaMaxima * 0.5 ? Color.GREEN : vidaActual > vidaMaxima * 0.2 ? Color.ORANGE : Color.RED);
    }

    private void showAttackOptions() {
        try {
            List<Movimiento> movimientos = estadoActual.pokemonActivo.getMovimientos();
            String[] opciones = movimientos.stream().map(Movimiento::getNombre).toArray(String[]::new);
            String seleccion = (String) JOptionPane.showInputDialog(
                mainFrame,
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
                    actualizarBarraDeSalud(playerHealthBar, estadoActual.pokemonActivo.getSalud(), estadoActual.pokemonActivo.getSaludInicial());
                    actualizarBarraDeSalud(opponentHealthBar, estadoActual.pokemonOponente.getSalud(), estadoActual.pokemonOponente.getSaludInicial());

                    if (estadoActual.pokemonOponente.getSalud() <= 0) {
                        JOptionPane.showMessageDialog(mainFrame, "¡Has derrotado al Pokémon oponente!");
                    } else if (estadoActual.pokemonActivo.getSalud() <= 0) {
                        JOptionPane.showMessageDialog(mainFrame, "Tu Pokémon ha sido derrotado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Movimiento no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (ExceptionPOOBkemon e) {
            JOptionPane.showMessageDialog(mainFrame, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showItemOptions() {
        JDialog dialog = new JDialog(mainFrame, "Seleccionar ítem", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(mainFrame);

        JLabel title = new JLabel("Elija uno de los siguientes ítems", SwingConstants.CENTER);
        dialog.add(title, BorderLayout.NORTH);

        DefaultListModel<Item> listModel = new DefaultListModel<>();
        for (Item item : estadoActual.items) {
            listModel.addElement(item);
        }

        JList<Item> itemList = new JList<>(listModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(itemList);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton useButton = new JButton("Usar");
        useButton.addActionListener(e -> {
            Item selected = itemList.getSelectedValue();
            if (selected != null) {
                try {
                    juego.realizarAccion("usarItem", selected);
                    dialog.dispose();
                    updateBattleScreen();
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(dialog, "Error al usar ítem: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecciona un ítem primero");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(useButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showChangePokemonOptions() {
        JDialog dialog = new JDialog(mainFrame, "Cambiar Pokémon", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);

        // Lista de Pokémon disponibles
        DefaultListModel<Pokemon> listModel = new DefaultListModel<>();
        JList<Pokemon> pokemonList = new JList<>(listModel);
        pokemonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pokemonList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Pokemon pokemon) {
                    setText(pokemon.getNombre() + " - Salud: " + pokemon.getSalud());
                }
                return renderer;
            }
        });

        // Cargar Pokémon disponibles
        List<Pokemon> availablePokemons = estadoActual.equipo;
        for (Pokemon pokemon : availablePokemons) {
            if (pokemon.getSalud() > 0 && !pokemon.equals(estadoActual.pokemonActivo)) {
                listModel.addElement(pokemon);
            }
        }

        JScrollPane scrollPane = new JScrollPane(pokemonList);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Botón para confirmar el cambio
        JButton changeButton = createStyledButton("Cambiar");
        changeButton.addActionListener(e -> {
            Pokemon selectedPokemon = pokemonList.getSelectedValue();
            if (selectedPokemon != null) {
                try {
                    juego.realizarAccion("cambiar", selectedPokemon);
                    estadoActual = juego.obtenerEstadoActual();
                    dialog.dispose();
                    updateBattleScreen();
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(dialog, "Error al cambiar Pokémon: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecciona un Pokémon para cambiar.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(changeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void updateBattleScreen() {
        estadoActual = juego.obtenerEstadoActual();

        if (estadoActual == null || !juego.hayBatallaActiva()) {
            String ganador = "Jugador"; // Esto debería obtenerse del juego
            JOptionPane.showMessageDialog(mainFrame,
                "El jugador " + ganador + " ha ganado el juego, felicidades!!");
            showMainMenu();
            return;
        }

        startBattle();
    }

    private class PokemonListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Pokemon pokemon) {
                setText(pokemon.getNombre());
                setFont(new Font("Arial", Font.BOLD, 14));
            }
            return renderer;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PoobkemonGUI());
    }
}