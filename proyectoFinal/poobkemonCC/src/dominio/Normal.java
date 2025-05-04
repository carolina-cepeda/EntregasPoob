package dominio;

import java.util.*;

public class Normal implements ModoJuego {

    @Override
    public void configurarJuego(Juego juego) {
        System.out.println("Configurando modo Normal...");

        Scanner scanner = new Scanner(System.in);

        // Pedir nombre de los entrenadores
        System.out.print("Ingrese nombre del Entrenador 1: ");
        String nombre1 = scanner.nextLine().trim();
        if (nombre1.isEmpty()) nombre1 = "Entrenador 1";

        System.out.print("Ingrese nombre del Entrenador 2: ");
        String nombre2 = scanner.nextLine().trim();
        if (nombre2.isEmpty()) nombre2 = "Entrenador 2";

        Entrenador entrenador1 = new Entrenador(nombre1, "Rojo");
        Entrenador entrenador2 = new Entrenador(nombre2, "Azul");

        List<Pokemon> base = crearPokemonesBase();

        seleccionarPokemones(scanner, entrenador1, base, nombre1);
        seleccionarPokemones(scanner, entrenador2, base, nombre2);

        seleccionarItems(scanner, entrenador1);
        seleccionarItems(scanner, entrenador2);


        juego.empezarBatalla(entrenador1, entrenador2);

        while (juego.hayBatallaActiva()) {
            Entrenador actual = juego.getTurnoActual();
            Pokemon activo = actual.getPokemonActivo();

            System.out.println("\nTurno de " + actual.getNombre() + " | Pokémon activo: " + activo.nombre);
            ArrayList<Movimiento> movimientos = activo.getMovimientos();

            for (int i = 0; i < movimientos.size(); i++) {
                System.out.println(i + ": " + movimientos.get(i).getNombre() + " (PP: " + movimientos.get(i).getPP() + ")");
            }

            System.out.print("Elige un ataque (0-" + (movimientos.size() - 1) + "): ");
            int opcion = scanner.nextInt();

            juego.comenzarTurno("atacar", opcion);
        }

        scanner.close();
    }

    private List<Pokemon> crearPokemonesBase() {
        Movimiento[] comunes = {
            new MovimientoNormal("Placaje", 40, 100, 35, 0, "Normal", null),
            new MovimientoNormal("Gruñido", 0, 100, 40, 0, "Normal", "Bajar ataque"),
            new MovimientoNormal("Ascuas", 40, 100, 25, 0, "Fuego", "Quemar"),
            new MovimientoNormal("Lanzallamas", 90, 100, 15, 0, "Fuego", "Quemar")
        };
        
        ArrayList<Pokemon> lista = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            lista.add(new Pokemon("Pokemon" + i, 300 + i * 5, 30, "Normal", null, 150 + i, 100, 120, 110, 160, 100, 100, comunes));
        }
        return lista;
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
                entrenador.agregarPokemon(disponibles.get(index));
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
                System.out.println("1: Potion (recupera 20 PS)");
                System.out.println("2: SuperPotion (recupera 50 PS)");
                System.out.println("3: HyperPotion (recupera 200 PS)");
            }
            if (!reviveElegido) {
                System.out.println("4: Revive (revive un Pokémon debilitado)");
            }
            System.out.println("0: Finalizar selección");
    
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 
    
            switch (opcion) {
                case 1 -> {
                    if (pocionesElegidas < 2) {
                        entrenador.agregarItem(new Pocion("Potion"));
                        pocionesElegidas++;
                    } else {
                        System.out.println("Ya has elegido el máximo de pociones.");
                    }
                }
                case 2 -> {
                    if (pocionesElegidas < 2) {
                        entrenador.agregarItem(new Pocion("SuperPotion"));
                        pocionesElegidas++;
                    } else {
                        System.out.println("Ya has elegido el máximo de pociones.");
                    }
                }
                case 3 -> {
                    if (pocionesElegidas < 2) {
                        entrenador.agregarItem(new Pocion("HyperPotion"));
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
