package dominio;

import java.util.*;

public class Juego {

    private ModoJuego modoJuego;
    private Batalla batalla;
    private List<Movimiento> movimientosBase;
    private List<Pokemon> pokemonesBase;
    private List<Item> itemsBase;

    public Juego() {
        inicializarDatosBase();
    }

    private void inicializarDatosBase() {
        movimientosBase = new ArrayList<>();
        movimientosBase.add(new Movimiento("Placaje", 40, 100, 35, 0, "Normal", null));
        movimientosBase.add(new Movimiento("Gruñido", 0, 100, 40, 0, "Normal", "Bajar ataque"));
        movimientosBase.add(new Movimiento("Ascuas", 40, 100, 25, 0, "Fuego", "Quemar"));
        movimientosBase.add(new Movimiento("Lanzallamas", 90, 100, 15, 0, "Fuego", "Quemar"));

        pokemonesBase = new ArrayList<>();
        pokemonesBase.add(new Pokemon("Charizard", 360,20,"fuego","volador",293,280,348,295,0,0,0));

        itemsBase = new ArrayList<>();
        itemsBase.add(new Pocion("Potion"));
        itemsBase.add(new Pocion("SuperPotion"));
        itemsBase.add(new Pocion("HyperPotion"));
        itemsBase.add(new Pocion("Revive"));
    }

    public void seleccionarModoJuego(ModoJuego modo) {
        this.modoJuego = modo;
        modoJuego.configurarJuego(this); 
    }

    public void empezarBatalla(Entrenador entrenador1, Entrenador entrenador2) {
        this.batalla = new Batalla(entrenador1, entrenador2);
        batalla.iniciarBatalla();
    }

    public void comenzarTurno(String accion, Object dato) {
        if (batalla != null) {
            batalla.comenzarTurno(accion, dato);
        }
    }

    public Entrenador getTurnoActual() {
        return batalla != null ? batalla.getTurnoActual() : null;
    }

    public boolean hayBatallaActiva() {
        return batalla != null;
    }

    
    public List<Movimiento> getMovimientosBase() {
        return movimientosBase;
    }

    public List<Pokemon> getPokemonesBaseCopia() {
        return new ArrayList<>(pokemonesBase); 
    }

    public List<Item> getItemsBase() {
        return itemsBase;
    }
}
