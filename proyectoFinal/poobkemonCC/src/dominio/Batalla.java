package dominio;

import java.util.List;

public class Batalla {

    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Entrenador turnoActual;
    private ControladorTurno controlador;

    public Batalla(Entrenador e1, Entrenador e2) {
        this.entrenador1 = e1;
        this.entrenador2 = e2;
        this.turnoActual = lanzarMoneda() ? entrenador1 : entrenador2;
        this.controlador = new ControladorTurno(this);
    }

    public void iniciarBatalla() {
        controlador.iniciar();
    }

    public void comenzarTurno(String accion, Object obj) throws ExceptionPOOBkemon {
        switch (accion.toLowerCase()) {
            case "atacar" -> {
                if (!(obj instanceof Integer indice)) throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
                Pokemon objetivo = obtenerOponente().getPokemonActivo();
                turnoActual.seleccionarMovimiento(indice, objetivo);

                if (objetivo.getSalud() <= 0) {
                    List<Pokemon> disponibles = obtenerOponente().getPokemones().stream()
                        .filter(p -> p.getSalud() > 0)
                        .toList();

                    if (!disponibles.isEmpty()) {
                        Pokemon nuevo = disponibles.get(0);
                        obtenerOponente().cambiarPokemon(nuevo);
                    }
                }
            }

            case "cambiar" -> {
                if (!(obj instanceof Pokemon nuevo)) throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
                turnoActual.cambiarPokemon(nuevo);
            }

            case "usaritem" -> {
                if (!(obj instanceof Item item)) throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
                Pokemon objetivo = turnoActual.getPokemonActivo();
                turnoActual.usarItem(item, objetivo);
            }

            case "huir" -> {
                throw new ExceptionPOOBkemon(obtenerOponente().getNombre() + " ha ganado la batalla.");
            }

            default -> throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
        }

        verificarFinYContinuar();
    }

    private boolean lanzarMoneda() {
        return Math.random() < 0.5;
    }

    private void cambiarTurno() {
        turnoActual = (turnoActual == entrenador1) ? entrenador2 : entrenador1;
    }

    public Entrenador obtenerOponente() {
        return (turnoActual == entrenador1) ? entrenador2 : entrenador1;
    }

    private void verificarFinYContinuar() throws ExceptionPOOBkemon {
        boolean e1SinPokemones = entrenador1.getPokemones().stream().allMatch(p -> p.getSalud() <= 0);
        boolean e2SinPokemones = entrenador2.getPokemones().stream().allMatch(p -> p.getSalud() <= 0);

        if (e1SinPokemones || e2SinPokemones) {
            Entrenador ganador = e1SinPokemones ? entrenador2 : entrenador1;
            controlador.detener();
            throw new ExceptionPOOBkemon(ganador.getNombre() + ExceptionPOOBkemon.GANADOR);
        }

        cambiarTurno();
        controlador.iniciar();
    }

    public Entrenador getTurnoActual() {
        return turnoActual;
    }

    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    public Entrenador getEntrenador2() {
        return entrenador2;
    }
}
