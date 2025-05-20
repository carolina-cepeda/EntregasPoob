package dominio;

import java.util.List;

public class Supervivencia implements ModoJuego {

    private Juego juego;
    private String nombre1;
    private String nombre2;

    @Override
    public void configurarJuego(Juego juego) {
        this.juego = juego;
    }

    /**
     * Prepara una batalla en modo Supervivencia.
     * Solo para PvP (responsabilidad externa garantizar esto).
     */
    public void prepararBatalla(String nombre1, String nombre2) throws ExceptionPOOBkemon {
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;

        Entrenador entrenador1 = new Entrenador(nombre1, "Rojo");
        Entrenador entrenador2 = new Entrenador(nombre2, "Azul");

        List<Pokemon> disponibles = juego.getPokemonesBaseCopia();
        entrenador1.seleccionarPokemonesAuto(disponibles);
        entrenador2.seleccionarPokemonesAuto(disponibles);
        
        juego.setEntrenadores(entrenador1, entrenador2);
        juego.comenzarBatalla();
    }

   
}