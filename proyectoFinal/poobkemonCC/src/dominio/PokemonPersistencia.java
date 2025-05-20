package dominio;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja la persistencia de datos para la aplicación Pokémon.
 * Contiene métodos para cargar Pokémon desde archivos.
 */
public class PokemonPersistencia {

    
    /**
     * Importa pokemones desde un archivo de texto.
     * @param rutaArchivo La ruta del archivo a importar
     * @return Lista de objetos Pokemon cargados desde el archivo
     */
    public static List<Pokemon> importarPokemones(String rutaArchivo) throws ExceptionPOOBkemon {
        List<Pokemon> pokemones = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numPokemones = Integer.parseInt(br.readLine().trim());
            
            for (int i = 0; i < numPokemones; i++) {
  
                String nombre = br.readLine().trim();
                String[] atributos = br.readLine().trim().split(" ");
                
                int salud = Integer.parseInt(atributos[0]);
                int nivel = Integer.parseInt(atributos[1]);
                String tipoPrincipal = atributos[2];
                String tipoSecundario = atributos[3].equals("NINGUNO") ? null : atributos[3];
                int ataque = Integer.parseInt(atributos[4]);
                int defensa = Integer.parseInt(atributos[5]);
                int ataqueEspecial = Integer.parseInt(atributos[6]);
                int defensaEspecial = Integer.parseInt(atributos[7]);
                int velocidad = Integer.parseInt(atributos[8]);
                int precision = Integer.parseInt(atributos[9]);
                int evasion = Integer.parseInt(atributos[10]);
                
                int numMovimientos = Integer.parseInt(br.readLine().trim());
                Movimiento[] movimientos = new Movimiento[numMovimientos];
                
                for (int j = 0; j < numMovimientos; j++) {
                    String[] datosMovimiento = br.readLine().trim().split(" ");
                    String nombreMovimiento = datosMovimiento[0];
                    int potencia = Integer.parseInt(datosMovimiento[1]);
                    int precisionMovimiento = Integer.parseInt(datosMovimiento[2]);
                    int pp = Integer.parseInt(datosMovimiento[3]);
                    int prioridad = Integer.parseInt(datosMovimiento[4]);
                    String tipo = datosMovimiento[5];
                    String efectoSecundario = datosMovimiento.length > 6 ? datosMovimiento[6] : null;
                    
                    movimientos[j] = new Movimiento(nombreMovimiento, potencia, precisionMovimiento, 
                                                   pp, prioridad, tipo, efectoSecundario);
                }
                
                Pokemon pokemon = new Pokemon(nombre, salud, nivel, tipoPrincipal, tipoSecundario,
                                             ataque, defensa, ataqueEspecial, defensaEspecial,
                                             velocidad, precision, evasion, movimientos);
                
                pokemones.add(pokemon);
                
                // Leer línea vacía entre pokemones
                br.readLine();
            }
            
        } catch (IOException e) {
            throw new ExceptionPOOBkemon("error al importar los pokemones");
        }
        
        return pokemones;
    }

    public static void guardarEstadoJuego(Juego juego, String rutaArchivo) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
        oos.writeObject(juego);
    } catch (IOException e) {

        try (FileWriter log = new FileWriter("pokemonLog.txt", true)) {
            log.write("Error al guardar el estado del juego: " + e.getMessage() + "\n");
        } catch (IOException ignored) {}
    }
    }

    public static Juego cargarEstadoJuego(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Juego) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {

            try (FileWriter log = new FileWriter("pokemonLog.txt", true)) {
                log.write("Error al cargar el estado del juego: " + e.getMessage() + "\n");
            } catch (IOException ignored) {}
            return null;
        }
    }

    
}