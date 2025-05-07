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
        System.out.println("¡Empieza la batalla entre " + entrenador1.getNombre() + " y " + entrenador2.getNombre() + "!");
        controlador.iniciar();
    }

    /**
     * Permite realizar la acción del turno.
     */
    public void comenzarTurno(String accion, Object obj) {
        switch (accion.toLowerCase()) {
            case "atacar" -> {
                int indice = (int) obj;
                Pokemon objetivo = obtenerOponente().getPokemonActivo();
                turnoActual.seleccionarMovimiento(indice, objetivo);

                if (objetivo.getSalud() <= 0) {
                    System.out.println(objetivo.nombre + " ha sido derrotado.");

                    List<Pokemon> disponibles = obtenerOponente().getPokemones().stream()
                        .filter(p -> p.getSalud() > 0)
                        .toList();

                    if (!disponibles.isEmpty()) {
                        Pokemon nuevo = disponibles.get(0);
                        obtenerOponente().cambiarPokemon(nuevo);
                        System.out.println(obtenerOponente().getNombre() + " cambió a " + nuevo.nombre);
                    }
                }
            }


            case "cambiar" -> {
                Pokemon nuevo = (Pokemon) obj;
                turnoActual.cambiarPokemon(nuevo);
            }

            case "usaritem" -> {
                Item item = (Item) obj;
                Pokemon objetivo = turnoActual.getPokemonActivo();
                turnoActual.usarItem(item, objetivo);
            }

            case "huir" -> {
                System.out.println(turnoActual.getNombre() + " ha huido de la batalla.");
                terminarBatalla(obtenerOponente());
                return; 
            }

            default -> System.out.println("Acción no válida.");
        }

        if (verificarFin()) return;
        cambiarTurno();
        controlador.iniciar();
    }

    private boolean lanzarMoneda() {
        return Math.random() < 0.5; 
    }

    private void cambiarTurno() {
        turnoActual = (turnoActual == entrenador1) ? entrenador2 : entrenador1; // deberia manejarlo controladorTurno??
        System.out.println("Turno de " + turnoActual.getNombre());
    }

    private Entrenador obtenerOponente() {
        return (turnoActual == entrenador1) ? entrenador2 : entrenador1;
    }

    private boolean verificarFin() {
        boolean e1SinPokemones = entrenador1.getPokemones().stream().allMatch(p -> p.getSalud() <= 0);
        boolean e2SinPokemones = entrenador2.getPokemones().stream().allMatch(p -> p.getSalud() <= 0);

        if (e1SinPokemones || e2SinPokemones) {
            Entrenador ganador = e1SinPokemones ? entrenador2 : entrenador1;
            terminarBatalla(ganador);
            return true;
        }
        return false;
    }

    private void terminarBatalla(Entrenador ganador) {
        controlador.detener();
        System.out.println("¡" + ganador.getNombre() + " ha ganado la batalla!");
        System.exit(0);
        
    }

    public Entrenador getTurnoActual() {
        return turnoActual;
    }
}



