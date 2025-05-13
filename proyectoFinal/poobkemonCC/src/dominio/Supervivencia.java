package dominio;

import java.util.Random;

public class Supervivencia implements ModoJuego {
    @Override
    public void configurarJuego(Juego juego) {
        System.out.println("Configurando modo Supervivencia...");

        Entrenador entrenador1 = new Entrenador("Red", "Negro");
        Entrenador entrenador2 = new Entrenador("Blue", "Blanco");

        for (int i = 0; i < 6; i++) {
            entrenador1.agregarPokemon(generarPokemonAleatorio());
            entrenador2.agregarPokemon(generarPokemonAleatorio());
        }

        // No se agregan ítems

        juego.setEntrenadores(entrenador1, entrenador2);
        juego.comenzarBatalla();

    }

    private Pokemon generarPokemonAleatorio() {
        Random rand = new Random();
        String[] nombres = { "Charizard", "Blastoise", "Venusaur", "Gengar", "Raichu" };
        String nombre = nombres[rand.nextInt(nombres.length)];

        Movimiento[] movimientos = {
            new Movimiento("Movimiento1", 80, 100, 15, 0, "Tipo", null),
            new Movimiento("Movimiento2", 90, 95, 10, 0, "Tipo", null),
            new Movimiento("Movimiento3", 0, 100, 30, 0, "Estado", "Aumentar stat"),
            new Movimiento("Movimiento4", 100, 80, 5, 0, "Fuerte", null)
        };

        Pokemon poke = new Pokemon(nombre, 400, 100, "Tipo1", "Tipo2", 300, 300, 300, 300, 300, 100, 100,movimientos);

        return poke;
    }
}

