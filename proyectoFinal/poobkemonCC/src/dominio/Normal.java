package dominio;

import java.util.*;

public class Normal implements ModoJuego {

    private Juego juego;
    private List<Movimiento> movimientosBase;
    private List<Item> itemsBase;

    @Override
    public void configurarJuego(Juego juego) {
        this.juego = juego;
        this.movimientosBase = juego.getMovimientosBase();
        this.itemsBase = juego.getItemsBase();

        System.out.println("Configurando modo Normal...");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese nombre del Entrenador 1: ");
        String nombre1 = scanner.nextLine().trim();
        if (nombre1.isEmpty()) nombre1 = "Entrenador 1";

        System.out.print("Ingrese nombre del Entrenador 2: ");
        String nombre2 = scanner.nextLine().trim();
        if (nombre2.isEmpty()) nombre2 = "Entrenador 2";

        Entrenador entrenador1 = new Entrenador(nombre1, "Rojo");
        Entrenador entrenador2 = new Entrenador(nombre2, "Azul");

        List<Pokemon> base = juego.getPokemonesBaseCopia();
        seleccionarPokemones(scanner, entrenador1, base, nombre1);
        seleccionarPokemones(scanner, entrenador2, base, nombre2);

        seleccionarItems(scanner, entrenador1);
        seleccionarItems(scanner, entrenador2);

        juego.empezarBatalla(entrenador1, entrenador2);

        while (juego.hayBatallaActiva()) {
            Entrenador actual = juego.getTurnoActual();
            Pokemon activo = actual.getPokemonActivo();
        
            System.out.println("\nTurno de " + actual.getNombre() + " | Pokémon activo: " + activo.nombre);
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Atacar");
            System.out.println("2. Cambiar Pokémon");
            System.out.println("3. Usar ítem");
            System.out.println("4. Huir");
            System.out.print("Selecciona una opción: ");
            int accion = scanner.nextInt();
            scanner.nextLine();  // limpiar buffer
        
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
                    System.out.println("Pokémon disponibles:");
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
                        break;
                    }
                    System.out.println("Ítems disponibles:");
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
        

        scanner.close();
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

                System.out.println("Selecciona 4 movimientos para " + base.nombre + ":");
                for (int j = 0; j < movimientosBase.size(); j++) {
                    System.out.println(j + ": " + movimientosBase.get(j).getNombre());
                }

                Movimiento[] movsElegidos = new Movimiento[4];
                int movsSeleccionados = 0;
                while (movsSeleccionados < 4) {
                    System.out.print("Movimiento #" + (movsSeleccionados + 1) + ": ");
                    int movIndex = scanner.nextInt();
                    if (movIndex >= 0 && movIndex < movimientosBase.size()) {
                        movsElegidos[movsSeleccionados++] = movimientosBase.get(movIndex);
                    } else {
                        System.out.println("Índice inválido.");
                    }
                }

                Pokemon personalizado = new Pokemon(base, movsElegidos);
                entrenador.agregarPokemon(personalizado);
                disponibles.remove(index);
                count++;
            } else {
                System.out.println("Índice inválido.");
            }
        }
    }

    private void seleccionarItems(Scanner scanner, Entrenador entrenador) {
        int pocionesElegidas = 0;
        boolean reviveElegido = false;

        System.out.println("\n" + entrenador.getNombre() + ", elige hasta 2 pociones y 1 Revive:");

        while (pocionesElegidas < 2 || !reviveElegido) {
            System.out.println("Opciones de ítems:");
            if (pocionesElegidas < 2) {
                System.out.println("1: Potion");
                System.out.println("2: SuperPotion");
                System.out.println("3: HyperPotion");
            }
            if (!reviveElegido) {
                System.out.println("4: Revive");
            }
            System.out.println("0: Finalizar selección");

            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1, 2, 3 -> {
                    if (pocionesElegidas < 2) {
                        String nombre = switch (opcion) {
                            case 1 -> "Potion";
                            case 2 -> "SuperPotion";
                            default -> "HyperPotion";
                        };
                        entrenador.agregarItem(new Pocion(nombre));
                        pocionesElegidas++;
                    } else {
                        System.out.println("Ya has elegido el máximo de pociones.");
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
