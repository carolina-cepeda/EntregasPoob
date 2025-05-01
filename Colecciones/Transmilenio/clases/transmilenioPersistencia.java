package clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

public class transmilenioPersistencia {

    private Sistema sistema;

    public transmilenioPersistencia(Sistema sistema) {
        this.sistema = sistema;
    }

    public void importarRuta(String nombreTroncal, String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String nombreRuta = br.readLine();
            if (nombreRuta == null) {
                System.out.println("El archivo está vacío.");
                return;
            }

            HashMap<String, Estacion> paradas = new HashMap<>();
            String linea;
            while ((linea = br.readLine()) != null) {
                String nombreEstacion = linea.trim();
                Estacion estacion = sistema.getEstacion(nombreEstacion);
                
                paradas.put(nombreEstacion, estacion);
            }

            Ruta nuevaRuta = new Ruta(nombreRuta, paradas);

            Troncal troncal = sistema.getTroncal(nombreTroncal);
            if (troncal == null) {
                troncal = new Troncal(nombreTroncal, 30.0, new TreeMap<>());
                sistema.agregarTroncal(nombreTroncal, troncal);
            }

            troncal.agregarRuta(nuevaRuta);
            System.out.println("Ruta '" + nombreRuta + "' importada correctamente a la troncal '" + nombreTroncal + "'.");

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
       
        }
    }
}

