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
