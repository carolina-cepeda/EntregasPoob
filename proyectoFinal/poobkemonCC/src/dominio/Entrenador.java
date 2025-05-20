package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * clase entrenador , puede ser un jugador o una
 */
public class Entrenador implements Serializable{

    private String nombre;
    private int PP;
    private String color;
    private Pokemon pokemonActual;
    private List<Pokemon> pokemones;
    private List<Item> items;

    /**
     * constructor para el entrenador
     * @param nombre
     * @param color
     */
    public Entrenador(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        this.pokemones = new ArrayList<>();
        this.items = new ArrayList<>();
        this.PP = 1000;
    }

    /**
     * metodo para obtener el nombre del entrenador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * metodo para obtener el color del entrenador
     */
    public String getColor() {
        return color;
    }
    /**
     * metodo para obtener los PP
     * @return
     */
    public int getPP() {
        return PP;
    }

/**
 * metodo para agregar un pokemon a la lista de pokemones
 * @param p
 */
    public void agregarPokemon(Pokemon p) {
        if (pokemones.size() < 6) {
            pokemones.add(p);
            if (pokemonActual == null) {
                pokemonActual = p;
            }
        } else {
            System.out.println("El entrenador ya tiene 6 pokémones.");
        }
    }

    /**
     * metodo para agregar un item a la lista de items
     * @param item
     */
    public void agregarItem(Item item) {
        items.add(item);
    }

    /**
     * metodo para obtener el pokemon que este seleccionado actualmente
     * @return
     */
    public Pokemon getPokemonActivo() {
        return pokemonActual;
    }

    /**
     * metodo para cambiar de pokemon
     * @param nuevo
     */
    public void cambiarPokemon(Pokemon nuevo) {
        if (pokemones.contains(nuevo) && nuevo.getSalud() > 0) {
            this.pokemonActual = nuevo;
        } else {
            System.out.println("No se puede cambiar a ese Pokémon.");
        }
    }

    /**
     * metodo para elegir el movimiento con el que se va a atacar al pokemon objetivo
     * 
     */
    public void seleccionarMovimiento(int indiceMovimiento, Pokemon objetivo) {
    if (pokemonActual != null && objetivo != null) {
        Movimiento mov = pokemonActual.getMovimientos().get(indiceMovimiento);
        if (mov.getPP() > 0) {
            pokemonActual.Atacar(indiceMovimiento, objetivo);
        } else {
            System.out.println(getNombre() + " no tiene PP suficientes para el movimiento: " + mov.getNombre());
        }
    } else {
        System.out.println(getNombre() + " no puede atacar porque no tiene un Pokémon o el objetivo es nulo.");
    }
}// usar poobkemon exception

    /**
     * metodo para usar un item en uno de los pokemones del entrenador
     * @param item
     * @param objetivo
     */
    public void usarItem(Item item, Pokemon objetivo) {
        if (items.contains(item)) {
            item.usar(objetivo);
            items.remove(item);  // elimino a los items despues de usarlos?
        } else {
            System.out.println("El item no está en la lista de items del entrenador.");
        }
    }

    /*+
     * metodo para obtener la lista de pokemones
     */
    public List<Pokemon> getPokemones() {
        return pokemones;
    }

    /**
     * metodo para obtener la lista de los items
     * @return
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * metodo para seleccionar los pokemones de forma automatica
     * @param disponibles
     */
    public void seleccionarPokemonesAuto(List<Pokemon> disponibles) {
    Collections.shuffle(disponibles); // Mezcla los Pokémon disponibles para q sea mas aleatorio
    int seleccionados = 0;
    for (Pokemon p : disponibles) {
        if (seleccionados >= 3) break; 
        this.agregarPokemon(p);
            seleccionados++;
        }
    }

    /**
     * metodo para seleccionar los items de forma automatica
     * @param itemsBase
     */
    public void seleccionarItemsAuto(List<Item> itemsBase) {

        int potions = 0, superPotions = 0;
        boolean reviveUsado = false;

        for (Item item : itemsBase) {
            if (item.getNombre().equals("Potion") && potions < 2) {
                this.agregarItem(new Pocion("Potion"));
                potions++;
            } else if (item.getNombre().equals("SuperPotion") && superPotions < 1) {
                this.agregarItem(new Pocion("SuperPotion"));
                superPotions++;
            } else if (item.getNombre().equals("Revive") && !reviveUsado) {
                this.agregarItem(new Pocion("Revive"));
                reviveUsado = true;
            }
            if (potions == 2 && superPotions == 1 && reviveUsado) break;
        }
    }
}
