package dominio;

import java.util.*;
import presentacion.EstadoJuego;

/**
 * clase principal del juego poobkemon
 */
public class Juego {

    private ModoJuego modoJuego;
    private Batalla batalla;

    private Entrenador entrenador1;
    private Entrenador entrenador2;

    private List<Movimiento> movimientosBase;
    private List<Pokemon> pokemonesBase;
    private List<Item> itemsBase;

    /**
     * constructor del juego 
     */
    public Juego() {
        inicializarDatosBase();
    }

    /**
     * metodo para inicializar datos base 
     */
    private void inicializarDatosBase() {
       
        pokemonesBase = PokemonPersistencia.importarPokemones("src/dominio/pokemonesBase.bat");
        itemsBase = new ArrayList<>();
        itemsBase.add(new Pocion("Potion"));
        itemsBase.add(new Pocion("SuperPotion"));
        itemsBase.add(new Pocion("HyperPotion"));
        itemsBase.add(new Pocion("Revive"));
    }

    /**
     * metodo para seleccionar el tipo de juego, puede ser normal o supervivencia
     * @param modo
     */
    public void seleccionarModoJuego(ModoJuego modo) {
        this.modoJuego = modo;
        modo.configurarJuego(this);
    }

    /**
     * metodo para la creacion de los entrenadores
     * @param nombre1 : cadena del nombre del entrenador 1
     * @param nombre2 :cadena del nombre del entrenador 2
     *  */ 
    public void crearEntrenadores(String nombre1, String nombre2) {
        entrenador1 = new Entrenador(nombre1, "Rojo");
        entrenador2 = new Entrenador(nombre2, "Azul");
    }

    /**
     * metodo para agregar un pokemon a uno de los entrenadores
     * @param numeroJugador
     * @param pokemon
     * @throws ExceptionPOOBkemon : accion invalida si no hay entrenador al que asignar
     */
    public void agregarPokemonAEntrenador(int numeroJugador, Pokemon pokemon) throws ExceptionPOOBkemon {
        switch (numeroJugador) {
            case 1 -> entrenador1.agregarPokemon(pokemon);
            case 2 -> entrenador2.agregarPokemon(pokemon);
            default -> throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
        }
    }

    /**
     * metodo para agregar items a un entrenador dado
     * @param numeroJugador
     * @param pokemon
     * @throws ExceptionPOOBkemon : accion invalida si no hay entrenador al que asignar
     *s
     */
    public void agregarItemAEntrenador(int numeroJugador, Item item) throws ExceptionPOOBkemon {
        switch (numeroJugador) {
            case 1 -> entrenador1.agregarItem(item);
            case 2 -> entrenador2.agregarItem(item);
            default -> throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
        }
    }

    /**
     * metodo para empezar la batalla
     */
    public void comenzarBatalla() {
        this.batalla = new Batalla(entrenador1, entrenador2, this);
        batalla.iniciarBatalla();
    }

    /**
     * metodo para que se realice una accion (atacar, huir, cambiar pokemon)
     * @param accion
     * @param dato
     * @throws ExceptionPOOBkemon
     */
    public void realizarAccion(String accion, Object dato) throws ExceptionPOOBkemon {
        if (batalla != null) {
            batalla.comenzarTurno(accion, dato);
        }
    }

    /**
     * metodo para obtener un estado del juego que devuelva toda la información del juego resumida
     * @return
     */
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

    /**
     * metodo para saber si es el turno de uno de los jugadores humanos
     * @return
     */
    public boolean esTurnoJugador() {
        return batalla.getTurnoActual() instanceof EntrenadorHumano;
    }

    /**
     * metodo para obtener el entrenador actual
     * @return
     */
    public Entrenador getEntrenadorActual() {
        return batalla.getTurnoActual();
    }

    /**
     * metodo para comenzar el siguiente turno si el jugador no juega despues de 20 segundos
     * @throws ExceptionPOOBkemon
     */
    public void comenzarTurno() throws ExceptionPOOBkemon {
        batalla.comenzarTurno("pasar", null);
    }

    /**
     * metodo para obtener los movimientos base
     * @return
     */
    public List<Movimiento> getMovimientosBase() {
        return movimientosBase;
    }

    /**
     * metodo para obtener los pokemones base
     * @return
     */
    public List<Pokemon> getPokemonesBaseCopia() {
        return new ArrayList<>(pokemonesBase);
    }

    /**
     * metodo para obtener los items base
     * @return
     */
    public List<Item> getItemsBase() {
        return itemsBase;
    }

    /**
     * metodo para saber si hay una batalla activa
     * @return
     */
    public boolean hayBatallaActiva() {
        return batalla != null;
    }

    /*
     * metodo para establecer entrenadores
     */
    public void setEntrenadores(Entrenador e1, Entrenador e2) {
        this.entrenador1 = e1;
        this.entrenador2 = e2;
    }

    /**
     * metodo para obtener el nombre del ganador (entrenador actual)
     * @return
     */
    public String getNombreGanador(){
        return getEntrenadorActual().getNombre();
    }

}
