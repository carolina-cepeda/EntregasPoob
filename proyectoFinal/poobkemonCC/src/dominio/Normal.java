package dominio;

/**
 * modo de juego normal definido para el juego jugador vs jugador, jugador vs maquina y maquina vs maquina
 */
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
    }

    /**
     * especificar que tipo de juego es playervs player, player vs maquina o maquina vs maquina
     * @param tipo
     */
    public void setTipoJuego(int tipo) {
        this.tipoJuego = tipo;
    }

    /**
     * metodo para preparar la batalla, see hace uso de juego.
     * @param nombre1
     * @param nombre2
     * @param tipoIA1
     * @param tipoIA2
     * @throws ExceptionPOOBkemon
     */
    public void prepararBatalla(String nombre1, String nombre2, int tipoIA1, int tipoIA2) throws ExceptionPOOBkemon {
    this.nombre1 = nombre1;
    this.nombre2 = nombre2;
    this.tipoIA1 = tipoIA1;
    this.tipoIA2 = tipoIA2;

    switch (tipoJuego) {
        case 1 -> { // PvP
            juego.crearEntrenadores(nombre1, nombre2);
        }

        case 2 -> { // PvM
            Entrenador humano = new Entrenador(nombre1, "Rojo");
            EntrenadorMaquina cpu = crearEntrenadorMaquina(tipoIA1, nombre2, "Azul");

            juego.setEntrenadores(humano, cpu);
          
        }

        case 3 -> {
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



    /**
     * metodo para iniciar la batalla 
     * @throws ExceptionPOOBkemon
     */
    public void iniciarBatalla() throws ExceptionPOOBkemon {
        switch (tipoJuego) {
        case 2 -> { // PvM
            EntrenadorMaquina cpu = (EntrenadorMaquina) juego.getEntrenador2();

            cpu.seleccionarPokemonesAuto(juego.getPokemonesBaseCopia());
            cpu.seleccionarItemsAuto(juego.getItemsBase());

            juego.comenzarBatalla();
        }

        case 3 -> { // MvM
        
            EntrenadorMaquina cpu1 = crearEntrenadorMaquina(tipoIA1, nombre1, "Rojo");
            EntrenadorMaquina cpu2 = crearEntrenadorMaquina(tipoIA2, nombre2, "Azul");

            cpu1.seleccionarPokemonesAuto(juego.getPokemonesBaseCopia());
            cpu2.seleccionarPokemonesAuto(juego.getPokemonesBaseCopia());
            cpu1.seleccionarItemsAuto(juego.getItemsBase());
            cpu2.seleccionarItemsAuto(juego.getItemsBase());

            juego.setEntrenadores(cpu1, cpu2);
            juego.comenzarBatalla();

            // Luego manejar el juego automático
            while (juego.hayBatallaActiva()) {
                Entrenador actual = juego.getEntrenadorActual();
                if (actual instanceof EntrenadorMaquina cpu) {
                    cpu.realizarAccionAutomatica(juego);
                } else {
                    throw new ExceptionPOOBkemon("El entrenador actual no es una máquina.");
                }
            }
            }

        default -> throw new ExceptionPOOBkemon("Modo de juego inválido.");
    }
}

    /**
     * metodo para crear el entrenador que es un a maquina
     * @param tipo
     * @param nombre
     * @param color
     * @return
     */
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
