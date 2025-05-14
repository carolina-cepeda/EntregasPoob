package presentacion;

import dominio.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PoobkemonGUI {
    private JFrame mainFrame;
    private Juego juego;
    private EstadoJuego estadoActual;

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

    private void showMainMenu() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));

        JLabel title = new JLabel("POOBKEMON", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        panel.add(title);

        JButton normalModeBtn = new JButton("Normal");
        normalModeBtn.addActionListener(e -> showSubModeSelection());
        panel.add(normalModeBtn);

        JButton survivalModeBtn = new JButton("Supervivencia");
        survivalModeBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainFrame, "Modo supervivencia no implementado aún");
        });
        panel.add(survivalModeBtn);

        mainFrame.getContentPane().removeAll();
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
    }

    private void showSubModeSelection() {
        JDialog dialog = new JDialog(mainFrame, "Selección de modo", true);
        dialog.setLayout(new GridLayout(3, 1, 10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);

        JLabel title = new JLabel("Elija un modo de juego", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        dialog.add(title);

        JButton pvpBtn = new JButton("Player vs Player");
        pvpBtn.addActionListener(e -> {
            dialog.dispose();
            showPlayerNamesInput();
        });
        dialog.add(pvpBtn);

        JButton pvcBtn = new JButton("Player vs Máquina");
        pvcBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "Modo Player vs Máquina no implementado aún");
        });
        dialog.add(pvcBtn);

        dialog.setVisible(true);
    }

    private void showPlayerNamesInput() {
        JDialog dialog = new JDialog(mainFrame, "Nombres de jugadores", true);
        dialog.setLayout(new GridLayout(4, 1, 10, 10));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);

        JLabel player1Label = new JLabel("JUGADOR 1 - Escriba su nombre:");
        JTextField player1Field = new JTextField();
        JLabel player2Label = new JLabel("JUGADOR 2 - Escriba su nombre:");
        JTextField player2Field = new JTextField();

        dialog.add(player1Label);
        dialog.add(player1Field);
        dialog.add(player2Label);
        dialog.add(player2Field);

        JButton startBtn = new JButton("COMENZAR!");
        startBtn.addActionListener(e -> {
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startBtn);
        dialog.add(buttonPanel);

        dialog.setVisible(true);
    }

    private void showPokemonSelection(String player1Name, String player2Name) {
        JDialog dialog = new JDialog(mainFrame, "Selección de Pokémon", true);
        dialog.setLayout(new GridLayout(3, 1));
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(mainFrame);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Panel para jugador 1
        JPanel player1Panel = createPokemonSelectionPanel(player1Name, 1);
        tabbedPane.addTab(player1Name, player1Panel);
        
        // Panel para jugador 2
        JPanel player2Panel = createPokemonSelectionPanel(player2Name, 2);
        tabbedPane.addTab(player2Name, player2Panel);

        dialog.add(tabbedPane);

        JButton continueBtn = new JButton("Continuar");
        continueBtn.addActionListener(e -> {
            try {
                // Verificar cantidad de Pokémon seleccionados
                int pokemonesJugador1 = juego.getPokemonesBaseCopia().size(); // Esto necesita corrección
                int pokemonesJugador2 = juego.getPokemonesBaseCopia().size(); // Esto necesita corrección
                
                if (pokemonesJugador1 < 3 || pokemonesJugador2 < 3) {
                    JOptionPane.showMessageDialog(dialog, "Cada jugador debe seleccionar al menos 3 Pokémon");
                    return;
                }
                dialog.dispose();
                showItemSelection(player1Name, player2Name);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(continueBtn);
        dialog.add(buttonPanel);

        dialog.setVisible(true);
    }

    private JPanel createPokemonSelectionPanel(String playerName, int playerNumber) {
        JPanel panel = new JPanel(new BorderLayout());
        
        DefaultListModel<Pokemon> listModel = new DefaultListModel<>();
        JList<Pokemon> pokemonList = new JList<>(listModel);
        pokemonList.setCellRenderer(new PokemonListRenderer());
        
        // Obtener lista de Pokémon disponibles
        List<Pokemon> availablePokemons = juego.getPokemonesBaseCopia();
        for (Pokemon p : availablePokemons) {
            listModel.addElement(p);
        }
        
        JScrollPane scrollPane = new JScrollPane(pokemonList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(e -> {
            Pokemon selected = pokemonList.getSelectedValue();
            if (selected != null) {
                try {
                    // Usamos el Pokémon directamente ya que no tenemos acceso a todos los getters
                    juego.agregarPokemonAEntrenador(playerNumber, selected);
                    JOptionPane.showMessageDialog(panel, "Pokémon agregado: " + selected.getNombre());
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(panel, "Error al agregar Pokémon: " + ex.getMessage());
                }
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void showItemSelection(String player1Name, String player2Name) {
        JDialog dialog = new JDialog(mainFrame, "Selección de Ítems", true);
        dialog.setLayout(new GridLayout(3, 1));
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(mainFrame);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Panel para jugador 1
        JPanel player1Panel = createItemSelectionPanel(player1Name, 1);
        tabbedPane.addTab(player1Name, player1Panel);
        
        // Panel para jugador 2
        JPanel player2Panel = createItemSelectionPanel(player2Name, 2);
        tabbedPane.addTab(player2Name, player2Panel);

        dialog.add(tabbedPane);

        JButton finishBtn = new JButton("Terminar");
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
        dialog.add(buttonPanel);

        dialog.setVisible(true);
    }

    private JPanel createItemSelectionPanel(String playerName, int playerNumber) {
        JPanel panel = new JPanel(new BorderLayout());
        
        DefaultListModel<Item> listModel = new DefaultListModel<>();
        JList<Item> itemList = new JList<>(listModel);
        
        // Obtener lista de ítems disponibles
        List<Item> availableItems = juego.getItemsBase();
        for (Item item : availableItems) {
            listModel.addElement(item);
        }
        
        JScrollPane scrollPane = new JScrollPane(itemList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(e -> {
            Item selected = itemList.getSelectedValue();
            if (selected != null) {
                try {
                    // Solo agregamos el ítem directamente ya que no podemos instanciar Item
                    juego.agregarItemAEntrenador(playerNumber, selected);
                    JOptionPane.showMessageDialog(panel, "Ítem agregado: " + selected.getNombre());
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(panel, "Error al agregar ítem: " + ex.getMessage());
                }
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void startBattle() {
        estadoActual = juego.obtenerEstadoActual();
        
        if (estadoActual == null) {
            JOptionPane.showMessageDialog(mainFrame, "Error al obtener estado del juego");
            showMainMenu();
            return;
        }
        
        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(new BorderLayout());
        
        // Panel superior con información de los Pokémon
        JPanel pokemonInfoPanel = new JPanel(new GridLayout(1, 2));
        
        // Pokémon del oponente
        JPanel opponentPokemonPanel = createPokemonBattlePanel(
            estadoActual.nombreOponente, 
            estadoActual.pokemonOponente, 
            false
        );
        
        // Pokémon del jugador
        JPanel playerPokemonPanel = createPokemonBattlePanel(
            estadoActual.nombreJugador, 
            estadoActual.pokemonActivo, 
            true
        );
        
        pokemonInfoPanel.add(opponentPokemonPanel);
        pokemonInfoPanel.add(playerPokemonPanel);
        
        mainFrame.add(pokemonInfoPanel, BorderLayout.CENTER);
        
        // Panel de acciones
        JPanel actionPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        
        JLabel turnLabel = new JLabel("Turno de " + estadoActual.nombreJugador, SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        actionPanel.add(turnLabel);
        
        JButton attackBtn = new JButton("1. Atacar");
        attackBtn.addActionListener(e -> showAttackOptions());
        actionPanel.add(attackBtn);
        
        JButton itemBtn = new JButton("2. Usar Item");
        itemBtn.addActionListener(e -> showItemOptions());
        actionPanel.add(itemBtn);
        
        JButton fleeBtn = new JButton("3. Huir");
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
        
        mainFrame.add(actionPanel, BorderLayout.SOUTH);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private JPanel createPokemonBattlePanel(String trainerName, Pokemon pokemon, boolean isPlayer) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(trainerName));
        
        // Imagen del Pokémon
        JLabel imageLabel = new JLabel();
        try {
            // Asumiendo que las imágenes están en resources/pokemon/[nombre].png
            ImageIcon icon = new ImageIcon(getClass().getResource(
                "/pokemon/" + pokemon.getNombre().toLowerCase() + ".png"));
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            imageLabel.setText("Imagen no encontrada");
        }
        panel.add(imageLabel, BorderLayout.CENTER);
        
        // Barra de salud (usando valores por defecto ya que no tenemos getSaludMaxima)
        JProgressBar healthBar = new JProgressBar(0, 100);
        healthBar.setValue(50); // Valor por defecto
        healthBar.setString("50/100"); // Valor por defecto
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.GREEN);
        
        panel.add(healthBar, BorderLayout.SOUTH);
        
        return panel;
    }

    private void showAttackOptions() {
        JDialog dialog = new JDialog(mainFrame, "Seleccionar ataque", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(mainFrame);
        
        JLabel title = new JLabel("Elija uno de los siguientes ataques", SwingConstants.CENTER);
        dialog.add(title, BorderLayout.NORTH);
        
        JPanel buttonsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        
        // Obtener ataques del Pokémon actual (usando valores por defecto)
        for (int i = 0; i < 2; i++) {
            JButton attackBtn = new JButton((i+1) + ". Ataque " + (i+1));
            int finalI = i;
            attackBtn.addActionListener(e -> {
                try {
                    juego.realizarAccion("atacar", finalI);
                    dialog.dispose();
                    updateBattleScreen();
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(dialog, "Error al atacar: " + ex.getMessage());
                }
            });
            buttonsPanel.add(attackBtn);
        }
        
        dialog.add(buttonsPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
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

    private void updateBattleScreen() {
        estadoActual = juego.obtenerEstadoActual();
        
        if (estadoActual == null || !juego.hayBatallaActiva()) {
            // La batalla ha terminado
            String ganador = "Jugador"; // Esto debería obtenerse del juego
            JOptionPane.showMessageDialog(mainFrame, 
                "El jugador " + ganador + " ha ganado el juego, felicidades!!");
            showMainMenu();
            return;
        }
        
        // Actualizar la pantalla de batalla
        startBattle();
    }

    // Renderer personalizado para mostrar Pokémon en la lista
    private class PokemonListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                    boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Pokemon) {
                Pokemon pokemon = (Pokemon) value;
                setText(pokemon.getNombre());
            }
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PoobkemonGUI());
    }
}