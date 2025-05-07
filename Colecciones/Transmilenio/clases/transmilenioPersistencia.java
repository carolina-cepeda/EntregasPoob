package clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class transmilenioPersistencia {

    private Sistema sistema;

    public transmilenioPersistencia(Sistema sistema) {
        this.sistema = sistema;
    }

    /**
     * * Método para importar estaciones desde un archivo txt.
     * @param rutaArchivo
     */
    public void importarEstaciones(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
    
                if (partes.length < 4) {
                    System.out.println("Línea inválida, faltan datos: " + linea);
                    continue;
                }
    
                String nombre = partes[0].trim();
                int tiempoDeEspera;
                try {
                    tiempoDeEspera = Integer.parseInt(partes[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Tiempo de espera inválido en: " + linea);
                    continue;
                }
                String nivelOcupacion = partes[2].trim();
                String nombreTroncal = partes[3].trim();
    
                if (sistema.getEstacion(nombre) == null) {
                    Estacion estacion = new Estacion(nombre, tiempoDeEspera, nivelOcupacion, nombreTroncal);
                    sistema.agregarEstacion(nombre,estacion);
                    System.out.println("Estación añadida: " + nombre);
                } else {
                    System.out.println("La estación '" + nombre + "' ya existe. Se omite.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de estaciones: " + e.getMessage());
        }
    }
    
    /**
     * * Método para importar rutas desde un archivo txt.
     * @param nombreTroncal
     * @param rutaArchivo
     */
    public void importarRuta(String nombreTroncal, String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String nombreRuta = br.readLine();
            if (nombreRuta == null) {
                System.out.println("El archivo está vacío.");
                return;
            }

            List<Estacion> paradas = new ArrayList<>();
            String linea;
            while ((linea = br.readLine()) != null) {
                String nombreEstacion = linea.trim();
                Estacion estacion = sistema.getEstacion(nombreEstacion);

                if (estacion == null) {
                    System.out.println("Advertencia: La estación '" + nombreEstacion + "' no existe en el sistema. Se omitirá.");
                    continue;
                }

                paradas.add(estacion);
            }

            Ruta nuevaRuta = new Ruta(nombreRuta, paradas);  

            Troncal troncal = sistema.getTroncal(nombreTroncal);
            if (troncal == null) {
                troncal = new Troncal(nombreTroncal, 10.0, new TreeMap<>());  // valor x
                sistema.agregarTroncal(nombreTroncal, troncal);
            }

            troncal.agregarRuta(nuevaRuta);
            System.out.println("Ruta '" + nombreRuta + "' importada correctamente a la troncal '" + nombreTroncal + "'.");

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

}

