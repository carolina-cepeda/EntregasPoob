package dominio;

public class Normal implements ModoJuego {

    private Juego juego;
    private int tipoJuego; // 1 = PvP, 2 = PvM, 3 = MvM

    @Override
    public void configurarJuego(Juego juego) {
        this.juego = juego;
        // Solo se inicializa, el resto será guiado por la GUI o flujo externo
    }

    public void setTipoJuego(int tipo) {
        this.tipoJuego = tipo;
    }

    public void prepararBatalla(String nombre1, String nombre2, int tipoIA1, int tipoIA2) throws ExceptionPOOBkemon {
        switch (tipoJuego) {
            case 1 -> { // PvP
                juego.crearEntrenadores(nombre1, nombre2);
            }

            case 2 -> { // PvM
                Entrenador humano = new Entrenador(nombre1, "Rojo");
                EntrenadorMaquina cpu = crearEntrenadorMaquina(tipoIA1, nombre2, "Azul");

                juego.setEntrenadores(humano, cpu);
            }

            case 3 -> { // MvM
                EntrenadorMaquina cpu1 = crearEntrenadorMaquina(tipoIA1, nombre1, "Rojo");
                EntrenadorMaquina cpu2 = crearEntrenadorMaquina(tipoIA2, nombre2, "Azul");

                juego.setEntrenadores(cpu1, cpu2);
                cpu1.seleccionarPokemonesAuto(juego.getPokemonesBaseCopia());
                cpu2.seleccionarPokemonesAuto(juego.getPokemonesBaseCopia());
                cpu1.seleccionarItemsAuto(juego.getItemsBase());
                cpu2.seleccionarItemsAuto(juego.getItemsBase());

                juego.comenzarBatalla();
            }

            default -> throw new ExceptionPOOBkemon("Modo de juego inválido.");
        }
    }

    public void iniciarBatalla() throws ExceptionPOOBkemon {
        switch (tipoJuego) {
            case 2 -> { // PvM
                while (juego.hayBatallaActiva()) {
                    if (juego.esTurnoJugador()) {
                        // Esperar acción del jugador humano (guiado por la GUI)
                        juego.esperarAccionJugador();
                    } else {
                        Entrenador actual = juego.getEntrenadorActual();
                        if (actual instanceof EntrenadorMaquina cpu) {
                            cpu.realizarAccionAutomatica(juego);
                        } else {
                            throw new ExceptionPOOBkemon("El entrenador actual no es una máquina.");
                        }
                    }
                    juego.comenzarTurno();
                }
            }
            case 3 -> { // MvM
                while (juego.hayBatallaActiva()) {
                    Entrenador actual = juego.getEntrenadorActual();
                    if (actual instanceof EntrenadorMaquina cpu) {
                        cpu.realizarAccionAutomatica(juego);
                    } else {
                        throw new ExceptionPOOBkemon("El entrenador actual no es una máquina.");
                    }
                    juego.comenzarTurno();
                }
            }
            default -> throw new ExceptionPOOBkemon("Modo de juego inválido.");
        }
    }

    private EntrenadorMaquina crearEntrenadorMaquina(int tipo, String nombre, String color) {
        return switch (tipo) {
            case 1 -> new defensiveTrainer(nombre, color);
            case 2 -> new attackingTrainer(nombre, color);
            case 3 -> new changingTrainer(nombre, color);
            case 4 -> new expertTrainer(nombre, color);
            default -> new defensiveTrainer(nombre, color);
        };
    }
}
