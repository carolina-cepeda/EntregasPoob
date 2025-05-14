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
        // Panel con imagen de fondo
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource("/resources/fondo_menu.png"));
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
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
            JOptionPane.showMessageDialog(dialog, "Modo Player vs Máquina no implementado aún");
        });
        dialog.add(pvcBtn, gbc);

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
		dialog.setSize(800, 500);
		dialog.setLocationRelativeTo(mainFrame);

		// Panel principal con dos columnas
		JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Panel para Jugador 1
		JPanel player1Panel = new JPanel(new BorderLayout());
		player1Panel.setBorder(BorderFactory.createTitledBorder(player1Name));
		
		DefaultListModel<Pokemon> listModel1 = new DefaultListModel<>();
		JList<Pokemon> pokemonList1 = new JList<>(listModel1);
		pokemonList1.setCellRenderer(new PokemonListRenderer());
		
		List<Pokemon> availablePokemons = juego.getPokemonesBaseCopia();
		for (Pokemon p : availablePokemons) {
			listModel1.addElement(p);
		}
		
		JScrollPane scrollPane1 = new JScrollPane(pokemonList1);
		player1Panel.add(scrollPane1, BorderLayout.CENTER);
		
		JLabel countLabel1 = new JLabel("0/3 Pokémon seleccionados");
		player1Panel.add(countLabel1, BorderLayout.SOUTH);

		// Panel para Jugador 2
		JPanel player2Panel = new JPanel(new BorderLayout());
		player2Panel.setBorder(BorderFactory.createTitledBorder(player2Name));
		
		DefaultListModel<Pokemon> listModel2 = new DefaultListModel<>();
		JList<Pokemon> pokemonList2 = new JList<>(listModel2);
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
		
		JLabel messageLabel = new JLabel("Selecciona al menos 3 Pokémon por jugador", SwingConstants.CENTER);
		bottomPanel.add(messageLabel, BorderLayout.NORTH);
		
		JPanel buttonPanel = new JPanel();
		JButton acceptBtn = new JButton("Aceptar");
		acceptBtn.addActionListener(e -> {
			int selectedCount1 = 0; // Aquí deberías obtener la cuenta real de Pokémon seleccionados
			int selectedCount2 = 0; // Aquí deberías obtener la cuenta real de Pokémon seleccionados
			
			if (selectedCount1 < 3) {
				messageLabel.setText(player1Name + " no ha seleccionado suficientes Pokémon");
				return;
			}
			if (selectedCount2 < 3) {
				messageLabel.setText(player2Name + " no ha seleccionado suficientes Pokémon");
				return;
			}
			
			dialog.dispose();
			showItemSelection(player1Name, player2Name);
		});
		buttonPanel.add(acceptBtn);
		bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

		dialog.add(mainPanel, BorderLayout.CENTER);
		dialog.add(bottomPanel, BorderLayout.SOUTH);
		dialog.setVisible(true);
}

    private JPanel createPokemonSelectionPanel(String playerName, int playerNumber) {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel con imagen de fondo
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource("/resources/fondo_seleccion.png"));
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    g.setColor(new Color(240, 240, 240));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        JLabel title = new JLabel(playerName + " - Elige tus Pokémon", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        backgroundPanel.add(title, BorderLayout.NORTH);
        
        DefaultListModel<Pokemon> listModel = new DefaultListModel<>();
        JList<Pokemon> pokemonList = new JList<>(listModel);
        pokemonList.setCellRenderer(new PokemonListRenderer());
        
        // Obtener lista de Pokémon disponibles
        List<Pokemon> availablePokemons = juego.getPokemonesBaseCopia();
        for (Pokemon p : availablePokemons) {
            listModel.addElement(p);
        }
        
        JScrollPane scrollPane = new JScrollPane(pokemonList);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = createStyledButton("Agregar");
        addButton.addActionListener(e -> {
            Pokemon selected = pokemonList.getSelectedValue();
            if (selected != null) {
                try {
                    juego.agregarPokemonAEntrenador(playerNumber, selected);
                    JOptionPane.showMessageDialog(panel, "Pokémon agregado: " + selected.getNombre());
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(panel, "Error al agregar Pokémon: " + ex.getMessage());
                }
            }
        });
        buttonPanel.add(addButton);
        
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(backgroundPanel);
        
        return panel;
    }

    private void showItemSelection(String player1Name, String player2Name) {
        JDialog dialog = new JDialog(mainFrame, "Selección de Ítems", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(mainFrame);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Panel para jugador 1
        JPanel player1Panel = createItemSelectionPanel(player1Name, 1);
        tabbedPane.addTab(player1Name, player1Panel);
        
        // Panel para jugador 2
        JPanel player2Panel = createItemSelectionPanel(player2Name, 2);
        tabbedPane.addTab(player2Name, player2Panel);

        dialog.add(tabbedPane, BorderLayout.CENTER);

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
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JPanel createItemSelectionPanel(String playerName, int playerNumber) {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel con imagen de fondo
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource("/resources/fondo_items.png"));
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    g.setColor(new Color(240, 240, 240));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        JLabel title = new JLabel(playerName + " - Elige tus ítems", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        backgroundPanel.add(title, BorderLayout.NORTH);
        
        DefaultListModel<Item> listModel = new DefaultListModel<>();
        JList<Item> itemList = new JList<>(listModel);
        
        // Obtener lista de ítems disponibles
        List<Item> availableItems = juego.getItemsBase();
        for (Item item : availableItems) {
            listModel.addElement(item);
        }
        
        JScrollPane scrollPane = new JScrollPane(itemList);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = createStyledButton("Agregar");
        addButton.addActionListener(e -> {
            Item selected = itemList.getSelectedValue();
            if (selected != null) {
                try {
                    juego.agregarItemAEntrenador(playerNumber, selected);
                    JOptionPane.showMessageDialog(panel, "Ítem agregado: " + selected.getNombre());
                } catch (ExceptionPOOBkemon ex) {
                    JOptionPane.showMessageDialog(panel, "Error al agregar ítem: " + ex.getMessage());
                }
            }
        });
        buttonPanel.add(addButton);
        
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(backgroundPanel);
        
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
        
        // Panel principal con imagen de fondo de batalla
        JPanel battlePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource("/resources/fondo_batalla.png"));
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
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
            ImageIcon icon = new ImageIcon(getClass().getResource(
                "/resources/pokemon/" + estadoActual.pokemonOponente.getNombre().toLowerCase() + ".png"));
            opponentPokemonImage.setIcon(icon);
        } catch (Exception e) {
            opponentPokemonImage.setText("Imagen no encontrada");
        }
        opponentPanel.add(opponentPokemonImage);
        
        // Barra de salud del oponente
        JProgressBar opponentHealthBar = new JProgressBar(0, 100);
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
            ImageIcon icon = new ImageIcon(getClass().getResource(
                "/resources/pokemon/" + estadoActual.pokemonActivo.getNombre().toLowerCase() + ".png"));
            playerPokemonImage.setIcon(icon);
        } catch (Exception e) {
            playerPokemonImage.setText("Imagen no encontrada");
        }
        playerPanel.add(playerPokemonImage);
        
        JLabel playerLabel = new JLabel(estadoActual.nombreJugador);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerLabel.setForeground(Color.WHITE);
        playerPanel.add(playerLabel);
        
        // Barra de salud del jugador
        JProgressBar playerHealthBar = new JProgressBar(0, 100);
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
        
        JButton fleeBtn = createStyledButton("3. Huir");
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

   private class PokemonListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                    boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
           
			if (value instanceof Pokemon pokemon) {
                setText(pokemon.getNombre());
                setFont(new Font("Arial", Font.BOLD, 14));
            }
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PoobkemonGUI());
    }
}