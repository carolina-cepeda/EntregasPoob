package dominio;

public class Normal implements ModoJuego {

    private Juego juego;
    private int tipoJuego; // 1 = PvP, 2 = PvM, 3 = MvM
    private String nombre1;
    private String nombre2;
    private int tipoIA1;
    private int tipoIA2;


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
                juego.comenzarBatalla();
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
        Entrenador humano = new Entrenador(nombre1, "Rojo");
        EntrenadorMaquina cpu = crearEntrenadorMaquina(tipoIA1, nombre2, "Azul");

        cpu.seleccionarPokemonesAuto(juego.getPokemonesBaseCopia());
        cpu.seleccionarItemsAuto(juego.getItemsBase());

        // solo mientras pruebo QUITAR LUEGO
        humano.seleccionarPokemonesAuto(juego.getPokemonesBaseCopia());
        humano.seleccionarItemsAuto(juego.getItemsBase());

        juego.setEntrenadores(humano, cpu);
        juego.comenzarBatalla();
    }


        case 3 -> {
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
