package clases;

/**
 * clase principal
 */
public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        transmilenioPersistencia persistencia = new transmilenioPersistencia(sistema);

        persistencia.importarEstaciones("clases/estaciones.txt");

        
        persistencia.importarRuta("B", "clases/B12.bat");

        sistema.exportarRutas("Portal del Sur - JFK Coop.Financiera", "Portal del Norte", "clases/rutas_exportadas.txt");
        
        sistema.guardarTroncal("B", "clases/troncal_B_info.txt");
    }
}

