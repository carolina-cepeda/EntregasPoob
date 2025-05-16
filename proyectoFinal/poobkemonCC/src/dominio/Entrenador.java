package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * clase entrenador , puede ser un jugador o una
 */
public class Entrenador {

    private String nombre;
    private int PP;
    private String color;
    private Pokemon pokemonActual;
    private List<Pokemon> pokemones;
    private List<Item> items;

    public Entrenador(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        this.pokemones = new ArrayList<>();
        this.items = new ArrayList<>();
        this.PP = 1000;
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    public int getPP() {
        return PP;
    }


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

    public void agregarItem(Item item) {
        items.add(item);
    }

    public Pokemon getPokemonActivo() {
        return pokemonActual;
    }

    public void cambiarPokemon(Pokemon nuevo) {
        if (pokemones.contains(nuevo) && nuevo.getSalud() > 0) {
            this.pokemonActual = nuevo;
        } else {
            System.out.println("No se puede cambiar a ese Pokémon.");
        }
    }

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
}

    
    public void usarItem(Item item, Pokemon objetivo) {
        if (items.contains(item)) {
            item.usar(objetivo);
            items.remove(item);  // elimino a los items despues de usarlos?
        } else {
            System.out.println("El item no está en la lista de items del entrenador.");
        }
    }

    public List<Pokemon> getPokemones() {
        return pokemones;
    }

    public List<Item> getItems() {
        return items;
    }

    public void seleccionarPokemonesAuto(List<Pokemon> disponibles) {
    Collections.shuffle(disponibles); // Mezcla los Pokémon disponibles para q sea mas aleatorio
    int seleccionados = 0;
    for (Pokemon p : disponibles) {
        if (seleccionados >= 3) break;
        this.agregarPokemon(p);
            seleccionados++;
        }
    }

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
