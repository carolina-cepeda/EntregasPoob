package dominio;

import java.util.*;
import presentacion.EstadoJuego;

public class Juego {

    private ModoJuego modoJuego;
    private Batalla batalla;

    private Entrenador entrenador1;
    private Entrenador entrenador2;

    private List<Movimiento> movimientosBase;
    private List<Pokemon> pokemonesBase;
    private List<Item> itemsBase;

    public Juego() {
        inicializarDatosBase();
    }

    private void inicializarDatosBase() {
        movimientosBase = new ArrayList<>();
        movimientosBase.add(new Movimiento("Bubble", 40, 100, 30, 0, "agua", null));
        movimientosBase.add(new Movimiento("Dragon Rage", 40, 100, 10, 0, "dragón", null)); 
        movimientosBase.add(new Movimiento("Flamethrower", 90, 100, 15, 0, "fuego", null));
        movimientosBase.add(new Movimiento("Placaje", 40, 100, 35, 0, "normal", null));
        movimientosBase.add(new Movimiento("Stun Spore", 0, 75, 30, 0, "planta", null));
        movimientosBase.add(new Movimiento("Peck", 35, 100, 35, 0, "volador", null));

        pokemonesBase = new ArrayList<>();
        pokemonesBase.add(new Pokemon("Charizard", 360, 20, "fuego", "volador", 293, 280, 348, 295, 0, 0, 0, movimientosBase.toArray(new Movimiento[0])));
        pokemonesBase.add(new Pokemon("Blastoise", 362, 20, "agua", null, 291, 328, 280, 295, 339, 0, 0, movimientosBase.toArray(new Movimiento[0])));
        pokemonesBase.add(new Pokemon("Venusaur", 364, 20, "planta", "veneno", 289, 291, 284, 328, 328, 0, 0, movimientosBase.toArray(new Movimiento[0])));
        pokemonesBase.add(new Pokemon("Gengar", 324, 20, "fantasma", "veneno", 251, 240, 350, 394, 273, 0, 0, movimientosBase.toArray(new Movimiento[0])));
        pokemonesBase.add(new Pokemon("Dragonite", 386, 20, "dragon", "volador", 403, 317, 284, 328, 328, 0, 0, movimientosBase.toArray(new Movimiento[0])));

        itemsBase = new ArrayList<>();
        itemsBase.add(new Pocion("Potion"));
        itemsBase.add(new Pocion("SuperPotion"));
        itemsBase.add(new Pocion("HyperPotion"));
        itemsBase.add(new Pocion("Revive"));
    }

    public void seleccionarModoJuego(ModoJuego modo) {
    this.modoJuego = modo;
    modo.configurarJuego(this);
}


    // Métodos públicos para la interfaz
    public void crearEntrenadores(String nombre1, String nombre2) {
        entrenador1 = new Entrenador(nombre1, "Rojo");
        entrenador2 = new Entrenador(nombre2, "Azul");
    }

    public void agregarPokemonAEntrenador(int numeroJugador, Pokemon pokemon) throws ExceptionPOOBkemon {
        if (numeroJugador == 1) {
            entrenador1.agregarPokemon(pokemon);
        } else if (numeroJugador == 2) {
            entrenador2.agregarPokemon(pokemon);
        } else {
            throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
        }
    }

    public void agregarItemAEntrenador(int numeroJugador, Item item) throws ExceptionPOOBkemon {
        if (numeroJugador == 1) {
            entrenador1.agregarItem(item);
        } else if (numeroJugador == 2) {
            entrenador2.agregarItem(item);
        } else {
            throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
        }
    }

    public void comenzarBatalla() {
        this.batalla = new Batalla(entrenador1, entrenador2);
        batalla.iniciarBatalla();
    }

    public void realizarAccion(String accion, Object dato) throws ExceptionPOOBkemon {
        if (batalla != null) {
            batalla.comenzarTurno(accion, dato);
        }
    }

    public EstadoJuego obtenerEstadoActual() {
        if (batalla == null) return null;
        Entrenador actual = batalla.getTurnoActual();
        Entrenador oponente = batalla.obtenerOponente();

        return new EstadoJuego(
            actual.getNombre(),
            actual.getPokemonActivo(),
            actual.getPokemones(),
            actual.getItems(),
            oponente.getNombre(),
            oponente.getPokemonActivo()
        );
    }

    public boolean esTurnoJugador() {
        return batalla.getTurnoActual() instanceof EntrenadorHumano;
    }

    public void esperarAccionJugador() {
        // Este método puede ser implementado para esperar la acción desde la GUI
        System.out.println("Esperando acción del jugador humano...");
    }

    public Entrenador getEntrenadorActual() {
        return batalla.getTurnoActual();
    }

    public void comenzarTurno() throws ExceptionPOOBkemon {
        batalla.comenzarTurno("pasar", null);
    }

    // Getters base
    public List<Movimiento> getMovimientosBase() {
        return movimientosBase;
    }

    public List<Pokemon> getPokemonesBaseCopia() {
        return new ArrayList<>(pokemonesBase);
    }

    public List<Item> getItemsBase() {
        return itemsBase;
    }

    public boolean hayBatallaActiva() {
        return batalla != null;
    }

    public void setEntrenadores(Entrenador e1, Entrenador e2) {
    this.entrenador1 = e1;
    this.entrenador2 = e2;
}

}
