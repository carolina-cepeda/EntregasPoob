package clases;

import java.util.ArrayList;
import java.util.TreeMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Sistema {

	private TreeMap<String, Troncal> troncales = new TreeMap<String, Troncal>();

	private TreeMap<String, Estacion> estaciones = new TreeMap<String, Estacion>();

	/**
	 * * Constructor de la clase Sistema
	 * @param nombreEstacion
	 * @return
	 */
	public int getTiempoDeEspera(String nombreEstacion) {
		Estacion estacion = estaciones.get(nombreEstacion);
		if (estacion != null) {
			return estacion.getTiempoDeEspera();
		} else {
			return 1000000; 
		}
	}

	/**
	 * metodo para encontrar una lista de rutas que permiten el viaje entre dos
	 * estaciones sin hacer transbordo
	 * @param Inicio : nombre de la estacion de inicio
	 * @param destino : nombre de la estacion de destino
	 * @return
	 */
	public ArrayList<String> RutasViaje(String Inicio, String destino) {
		String nombreTroncal = estaciones.get(destino).getnombreTroncal();
		Troncal troncalDestino = troncales.get(nombreTroncal);
		if (troncalDestino == null) {
			return null; 
		}
		return troncalDestino.RutasViaje(Inicio, destino);
	}

	/**
	 * metodo para encontrar una lista de rutas que permiten el viaje entre dos
	 * estaciones haciendo transbordo siendo el viaje mas corto
	 * @param Inicio
	 * @param destino
	 * @return
	 */
	public ArrayList<String> Recorrido(String Inicio, String destino) {
		return null;
	}

	// metodos que no estan en el diagrama de clases pero son necesarios para la persistencia
    public Troncal getTroncal(String nombreTroncal) {
        return troncales.get(nombreTroncal);
    }

    public Estacion getEstacion(String nombreEstacion) {
        return estaciones.get(nombreEstacion);
    }

	public void agregarTroncal(String nombre, Troncal troncal) {
		troncales.put(nombre, troncal);
	}

	public void agregarEstacion(String nombre, Estacion estacion) {
		estaciones.put(nombre, estacion);
	}

	
	public void exportarRutas(String inicio, String destino, String rutaArchivo) {
		ArrayList<String> rutas = RutasViaje(inicio, destino);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
			if (rutas != null && !rutas.isEmpty()) {
				for (String ruta : rutas) {
					writer.write(ruta);
					writer.newLine();
				}
				System.out.println("Rutas exportadas correctamente a " + rutaArchivo);
			} else {
				System.out.println("No se encontraron rutas para el viaje de " + inicio + " a " + destino);
			}
		} catch (IOException e) {
			System.out.println("Error al escribir en el archivo: " + e.getMessage());
		}
	}

	public void guardarTroncal(String nombreTroncal, String rutaArchivo) {
		Troncal troncal = getTroncal(nombreTroncal);
		if (troncal != null) {
			troncal.guardarInformacion(rutaArchivo);
		} else {
			System.out.println("La troncal '" + nombreTroncal + "' no existe.");
		}
	}
}
