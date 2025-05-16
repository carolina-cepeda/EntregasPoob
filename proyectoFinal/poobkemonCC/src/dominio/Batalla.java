package dominio;

import java.util.List;

public class Batalla {

    private final Entrenador entrenador1;
    private final Entrenador entrenador2;
    private final Juego juego;
    private Entrenador turnoActual;
    private final ControladorTurno controlador;

    /**
     * metodo constructor de la batalla
     * @param e1
     * @param e2
     * @param juegoParam
     */
    public Batalla(Entrenador e1, Entrenador e2, Juego juegoParam) {
        this.entrenador1 = e1;
        this.entrenador2 = e2;
        this.juego = juegoParam;
        this.turnoActual = lanzarMoneda() ? entrenador1 : entrenador2;
        this.controlador = new ControladorTurno(this);
    }

    /**
     * metodo para iniciar la batalla usando un controlador de turnos
     */
    public void iniciarBatalla() {
        controlador.iniciar();
    }

    /**
     * metodo para manejar una acción hecha por un entrenador
     * @param accion
     * @param obj : objeto/pokemon objetivo de la accion
     * @throws ExceptionPOOBkemon
     */
    public void comenzarTurno(String accion, Object obj) throws ExceptionPOOBkemon {
        System.out.println("Acción recibida: " + accion);
        System.out.println("Objeto asociado: " + obj);
        
        switch (accion.toLowerCase()) {
            case "atacar" -> {
                if (!(obj instanceof Integer indice)) {
                    System.out.println("Error: El objeto no es un índice válido.");
                    throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
                }
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
                if (!(obj instanceof Pokemon nuevo)) {
                    System.out.println("Error: El objeto no es un Pokémon válido.");
                    throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
                }
                turnoActual.cambiarPokemon(nuevo);
            }
            case "usaritem" -> {
                if (!(obj instanceof Item item)) {
                    System.out.println("Error: El objeto no es un ítem válido.");
                    throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
                }
                Pokemon objetivo = turnoActual.getPokemonActivo();
                turnoActual.usarItem(item, objetivo);
            }
            case "huir" -> {
                throw new ExceptionPOOBkemon(obtenerOponente().getNombre() + " ha ganado la batalla.");
            }
            case "pasar" -> {
                System.out.println("El turno se pasa sin realizar ninguna acción.");
                // No se realiza ninguna acción, simplemente se avanza el turno
            }
            default -> {
                System.out.println("Error: Acción no reconocida.");
                throw new ExceptionPOOBkemon(ExceptionPOOBkemon.accionInvalida);
            }
        }

        verificarFinYContinuar();
    }

    /**
     * metodo para hacer la accion automatica de un entrenador si este es una maquina 
     * @throws ExceptionPOOBkemon
     */
    public void comenzarTurno() throws ExceptionPOOBkemon {
        if (turnoActual instanceof EntrenadorMaquina maquina) {
            // Realizar acción automática para el entrenador máquina
            maquina.realizarAccionAutomatica(juego);
        } else {
            throw new ExceptionPOOBkemon("El turno actual no es de un entrenador máquina.");
        }

        verificarFinYContinuar();
    }

    /**
     * ,metodo para hacer aleatorio el comienzo del juego 
     * @return
     */
    private boolean lanzarMoneda() {
        return Math.random() < 0.5;
    }

    /**
     * metodo para cambiar el turno
     */
    private void cambiarTurno() {
        turnoActual = (turnoActual == entrenador1) ? entrenador2 : entrenador1;
    }

    /**
     * metodo para obtener la información del oponente del entrenador del turno actual
     * @return
     */
    public Entrenador obtenerOponente() {
        return (turnoActual == entrenador1) ? entrenador2 : entrenador1;
    }

    /**
     * metodo para verificar si la batalla ha terminado,antes de cambiar el turno para revisar si ya se termino la batalla
     * @throws ExceptionPOOBkemon con el nombre del ganador si se termina la batalla
     */
    private void verificarFinYContinuar() throws ExceptionPOOBkemon {
        System.out.println("Verificando si la batalla ha terminado...");
        boolean e1SinPokemones = entrenador1.getPokemones().stream().allMatch(p -> p.getSalud() <= 0);
        boolean e2SinPokemones = entrenador2.getPokemones().stream().allMatch(p -> p.getSalud() <= 0);

        if (e1SinPokemones || e2SinPokemones) {
            Entrenador ganador = e1SinPokemones ? entrenador2 : entrenador1;
            System.out.println("La batalla ha terminado. Ganador: " + ganador.getNombre());
            controlador.detener();
            throw new ExceptionPOOBkemon(ganador.getNombre() + ExceptionPOOBkemon.GANADOR);
        }

        System.out.println("La batalla continúa. Cambiando turno...");
        cambiarTurno();
        controlador.iniciar();
    }
    /**
     * metodo para obtener el entrenador que esta jugando actualmente
     * @return
     */
    public Entrenador getTurnoActual() {
        return turnoActual;
    }

    /**
     * metodo para obtener el entrenador 1
     * @return
     */
    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    /**
     * metodo para obtener el entrenador 2
     * @return
     */
    public Entrenador getEntrenador2() {
        return entrenador2;
    }
}
