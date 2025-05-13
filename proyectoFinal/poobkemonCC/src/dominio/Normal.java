package dominio;

import java.util.*;
import java.util.Map.Entry;

public class Normal implements ModoJuego {

    private Juego juego;
    private List<Movimiento> movimientosBase;
    private List<Item> itemsBase;

    /**
     * metodo para configurar le juego
     * podria hacerse la mitad de este en juego , como pokemones base
     */
    @Override
public void configurarJuego(Juego juego) {
    this.juego = juego;
    this.movimientosBase = juego.getMovimientosBase();
    this.itemsBase = juego.getItemsBase();

    System.out.println("Configurando modo de juego...");
    Scanner scanner = new Scanner(System.in);

    System.out.println("Selecciona el modo de juego:");
    System.out.println("1. Player vs Player");
    System.out.println("2. Player vs Machine");
    System.out.println("3. Machine vs Machine");
    System.out.print("Opción: ");
    int modo = scanner.nextInt();
    scanner.nextLine();

    Entrenador entrenador1;
    Entrenador entrenador2;

    List<Pokemon> base = juego.getPokemonesBaseCopia();

    switch (modo) {
        case 1 -> { // PvP
            System.out.print("Ingrese nombre del Entrenador 1: ");
            String nombre1 = scanner.nextLine().trim();
            if (nombre1.isEmpty()) nombre1 = "Entrenador 1";

            System.out.print("Ingrese nombre del Entrenador 2: ");
            String nombre2 = scanner.nextLine().trim();
            if (nombre2.isEmpty()) nombre2 = "Entrenador 2";

            entrenador1 = new Entrenador(nombre1, "Rojo");
            entrenador2 = new Entrenador(nombre2, "Azul");

            seleccionarPokemones(scanner, entrenador1, base, nombre1);
            seleccionarPokemones(scanner, entrenador2, base, nombre2);
            seleccionarItems(scanner, entrenador1);
            seleccionarItems(scanner, entrenador2);
        }

        case 2 -> { // PvM
            System.out.print("Ingrese nombre del Entrenador humano: ");
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) nombre = "Jugador";

            entrenador1 = new Entrenador(nombre, "Rojo");

            System.out.println("Selecciona tipo de entrenador máquina:");
            System.out.println("1. Defensive");
            System.out.println("2. Attacking");
            System.out.println("3. Changing");
            System.out.println("4. Expert");
            int tipoMaquina = scanner.nextInt();
            scanner.nextLine();

            entrenador2 = crearEntrenadorMaquina(tipoMaquina, "CPU", "Azul");

            seleccionarPokemones(scanner, entrenador1, base, nombre);
            entrenador2.seleccionarPokemonesAuto(base);
            seleccionarItems(scanner, entrenador1);
            entrenador2.seleccionarItemsAuto(itemsBase);
        }

        case 3 -> { // MvM
            System.out.println("Selecciona tipo del Entrenador Máquina 1:");
            System.out.println("1. Defensive");
            System.out.println("2. Attacking");
            System.out.println("3. Changing");
            System.out.println("4. Expert");
            int tipo1 = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Selecciona tipo del Entrenador Máquina 2:");
            int tipo2 = scanner.nextInt();
            scanner.nextLine();

            entrenador1 = crearEntrenadorMaquina(tipo1, "CPU1", "Rojo");
            entrenador2 = crearEntrenadorMaquina(tipo2, "CPU2", "Azul");

            entrenador1.seleccionarPokemonesAuto(base);
            entrenador2.seleccionarPokemonesAuto(base);
            entrenador1.seleccionarItemsAuto(itemsBase);
            entrenador2.seleccionarItemsAuto(itemsBase);
        }

        default -> {
            System.out.println("Modo no válido. Se selecciona PvP por defecto.");
            entrenador1 = new Entrenador("Entrenador 1", "Rojo");
            entrenador2 = new Entrenador("Entrenador 2", "Azul");
            seleccionarPokemones(scanner, entrenador1, base, "Entrenador 1");
            seleccionarPokemones(scanner, entrenador2, base, "Entrenador 2");
            seleccionarItems(scanner, entrenador1);
            seleccionarItems(scanner, entrenador2);
        }
    }

    juego.empezarBatalla(entrenador1, entrenador2);

    while (juego.hayBatallaActiva()) {
        Entrenador actual = juego.getTurnoActual();
        System.out.println("\nTurno de " + actual.getNombre());

        if (actual instanceof EntrenadorMaquina maquina) {
            Pokemon enemigo = juego.obtenerOponente(maquina).getPokemonActivo();
            Entry<String, Object> accion = maquina.decidirAccion(enemigo);
            juego.comenzarTurno(accion.getKey(), accion.getValue());
        }
        else {
            realizarTurnoHumano(scanner, actual);
        }
    }

    scanner.close();
}

private EntrenadorMaquina crearEntrenadorMaquina(int tipo, String nombre, String color) {
    return switch (tipo) {
        case 1 -> new defensiveTrainer(nombre, color);
        case 2 -> new attackingTrainer(nombre, color);
        case 3 -> new changingTrainer(nombre, color);
        case 4 -> new expertTrainer(nombre, color);
        default -> new defensiveTrainer(nombre, color);
    };
}

private void realizarTurnoHumano(Scanner scanner, Entrenador actual) {
    Pokemon activo = actual.getPokemonActivo();

    System.out.println("\n" + actual.getNombre() + " | puntos restantes: " + actual.getPP());
    System.out.println("¿Qué deseas hacer?");
    System.out.println("1. Atacar");
    System.out.println("2. Cambiar Pokémon");
    System.out.println("3. Usar ítem");
    System.out.println("4. Huir");
    System.out.print("Selecciona una opción: ");
    int accion = scanner.nextInt();
    scanner.nextLine();

    switch (accion) {
        case 1 -> {
            ArrayList<Movimiento> movimientos = activo.getMovimientos();
            for (int i = 0; i < movimientos.size(); i++) {
                System.out.println(i + ": " + movimientos.get(i).getNombre() + " (PP: " + movimientos.get(i).getPP() + ")");
            }
            System.out.print("Elige un ataque (0-" + (movimientos.size() - 1) + "): ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            juego.comenzarTurno("atacar", opcion);
        }
        case 2 -> {
            List<Pokemon> pokemones = actual.getPokemones();
            for (int i = 0; i < pokemones.size(); i++) {
                System.out.println(i + ": " + pokemones.get(i).nombre + " (Salud: " + pokemones.get(i).getSalud() + ")");
            }
            System.out.print("Elige el Pokémon al que quieres cambiar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            juego.comenzarTurno("cambiar", pokemones.get(opcion));
        }
        case 3 -> {
            List<Item> items = actual.getItems();
            if (items.isEmpty()) {
                System.out.println("No tienes ítems.");
                return;
            }
            for (int i = 0; i < items.size(); i++) {
                System.out.println(i + ": " + items.get(i).getNombre());
            }
            System.out.print("Elige el ítem a usar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            juego.comenzarTurno("usaritem", items.get(opcion));
        }
        case 4 -> juego.comenzarTurno("huir", null);
        default -> System.out.println("Opción no válida.");
    }
}

    private void seleccionarPokemones(Scanner scanner, Entrenador entrenador, List<Pokemon> disponibles, String nombre) {
        System.out.println("\n" + nombre + ", elige 3 Pokémon:");

        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println(i + ": " + disponibles.get(i).nombre);
        }

        int count = 0;
        while (count < 3) {
            System.out.print("Pokémon #" + (count + 1) + ": ");
            int index = scanner.nextInt();
            if (index >= 0 && index < disponibles.size()) {
                Pokemon base = disponibles.get(index);
                entrenador.agregarPokemon(base);
                count++;
            } else {
                System.out.println("Índice inválido.");
            }
        }
    }
    private void seleccionarItems(Scanner scanner, Entrenador entrenador) {
        int potions = 0;
        int superPotions = 0;
        int hyperPotions = 0;
        boolean reviveElegido = false;
    
        System.out.println("\n" + entrenador.getNombre() + ", elige hasta 2 de cada tipo de poción y 1 Revive:");
    
        while ((potions < 2 || superPotions < 2 || hyperPotions < 2) || !reviveElegido) {
            System.out.println("Opciones de ítems:");
            if (potions < 2) System.out.println("1: Potion (" + potions + "/2)");
            if (superPotions < 2) System.out.println("2: SuperPotion (" + superPotions + "/2)");
            if (hyperPotions < 2) System.out.println("3: HyperPotion (" + hyperPotions + "/2)");
            if (!reviveElegido) System.out.println("4: Revive");
            System.out.println("0: Finalizar selección");
    
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
    
            switch (opcion) {
                case 1 -> {
                    if (potions < 2) {
                        entrenador.agregarItem(new Pocion("Potion"));
                        potions++;
                    } else {
                        System.out.println("Ya has elegido 2 Potions.");
                    }
                }
                case 2 -> {
                    if (superPotions < 2) {
                        entrenador.agregarItem(new Pocion("SuperPotion"));
                        superPotions++;
                    } else {
                        System.out.println("Ya has elegido 2 SuperPotions.");
                    }
                }
                case 3 -> {
                    if (hyperPotions < 2) {
                        entrenador.agregarItem(new Pocion("HyperPotion"));
                        hyperPotions++;
                    } else {
                        System.out.println("Ya has elegido 2 HyperPotions.");
                    }
                }
                case 4 -> {
                    if (!reviveElegido) {
                        entrenador.agregarItem(new Pocion("Revive"));
                        reviveElegido = true;
                    } else {
                        System.out.println("Ya has elegido un Revive.");
                    }
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }
    
}
