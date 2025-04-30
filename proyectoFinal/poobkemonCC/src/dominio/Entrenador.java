import java.util.ArrayList;
import java.util.List;

public class Entrenador {
    private String nombre;
    private String color;
    private Pokemon pokemonActual;
    private List<Pokemon> pokemones;
    private List<Item> items;

    public Entrenador(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        this.pokemones = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    public void agregarPokemon(Pokemon p) {
        if (pokemones.size() < 6) {
            pokemones.add(p);
            if (pokemonActual == null) {
                pokemonActual = p;
            }
        } else {
            System.out.println("El entrenador ya tiene 6 pokémon.");
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
            pokemonActual.Atacar(indiceMovimiento, objetivo);
        }
    }

    public void usarItem(Item item, Pokemon objetivo) {
        if (items.contains(item)) {
            item.usar(objetivo);
            items.remove(item); // lo elimina tras usarlo, como evito tener que volver  a inventar el item despues?
        }
    }

    public List<Pokemon> getPokemones() {
        return pokemones;
    }

    public List<Item> getItems() {
        return items;
    }
}
