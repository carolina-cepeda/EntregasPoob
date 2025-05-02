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

        entrenador1.agregarItem(new Pocion("Potion"));
        entrenador2.agregarItem(new Pocion("SuperPotion"));

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

            juego.ejecutarTurno("atacar", opcion);
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
}
