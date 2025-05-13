package dominio;

import java.util.Map.Entry;

public abstract class EntrenadorMaquina extends Entrenador {
    public EntrenadorMaquina(String nombre, String color) {
        super(nombre, color);
    }

    public abstract Entry<String, Object> decidirAccion(Pokemon pokemonEnemigo);


}

