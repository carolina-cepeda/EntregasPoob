package dominio;
/**
 * clase para implementar el modo de juego normal
 */
public class Normal implements ModoJuego {
	
    @Override
    public void configurarJuego(Juego juego) {
        System.out.println("Configurando modo Normal...");


        Entrenador entrenador1 = new Entrenador("Ash", "Rojo");
        Entrenador entrenador2 = new Entrenador("Gary", "Azul");


        entrenador1.agregarPokemon(crearCharizard());
        entrenador2.agregarPokemon(crearBlastoise());


        entrenador1.agregarItem(new Pocion("Potion"));
        entrenador2.agregarItem(new Pocion("SuperPotion"));


        juego.empezarBatalla(entrenador1, entrenador2);
    }

    private Pokemon crearCharizard() {
        Movimiento[] movimientos = {
            new MovimientoNormal("Lanzallamas", 90, 100, 15, 0, "Fuego", "Quemar"),
            new MovimientoNormal("Vuelo", 90, 95, 10, 0, "Volador", "Evitar daño"),
            new MovimientoNormal("Garra Dragón", 80, 100, 15, 0, "Dragón", null),
            new MovimientoNormal("Gruñido", 0, 100, 40, 0, "Normal", "Bajar ataque")
        };
        return new Pokemon("Charizard", 360, 50, "Fuego", "Volador", 293, 280, 348, 295, 328, 100, 100, movimientos); // creo subclases o lo dejo asi?
    }

    private Pokemon crearBlastoise() {
        Movimiento[] movimientos = {
            new MovimientoNormal("Hidrobomba", 110, 80, 5, 0, "Agua", "Bajar precisión"),
            new MovimientoNormal("Cabezazo", 80, 100, 15, 0, "Normal", null),
            new MovimientoNormal("Protección", 0, -1, 10, 4, "Normal", "Evita daño"),
            new MovimientoNormal("Refuerzo", 0, 100, 20, 0, "Normal", "Aumentar defensa")
        };
        return new Pokemon("Blastoise", 362, 50, "Agua", null, 291, 328, 295, 339, 280, 100, 100, movimientos);
    }
}
