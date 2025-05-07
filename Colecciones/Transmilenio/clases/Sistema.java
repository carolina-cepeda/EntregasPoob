package clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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


	/**
	 * * metodo para encontrar una lista de rutas que permiten el viaje entre dos estaciones sin hacer transbordo
	 * @param inicio
	 * @param destino
	 * @return
	 */
	public List<Ruta> rutasSinTransbordo(String inicio, String destino) {

		List<Ruta> rutasValidas = new ArrayList<>();
	
		for (Troncal troncal : troncales.values()) {
			Map<String, Ruta> rutasTroncal = troncal.getRutas();

			for (Ruta ruta : rutasTroncal.values()) {
				List<Estacion> estacionesRuta = getEstacionesRuta(ruta);
				boolean inicioEncontrado = false;
				boolean destinoEncontrado = false;
	
				for (Estacion estacion : estacionesRuta) {
					if (estacion.getNombre().equals(inicio)) {
						inicioEncontrado = true;
					}
					if (inicioEncontrado && estacion.getNombre().equals(destino)) {
						destinoEncontrado = true;
						break; // mejorable
					}
				}
	
				if (inicioEncontrado && destinoEncontrado) {
					rutasValidas.add(ruta);
				}
			}
		}
		return rutasValidas;
	}

	/**
	 * * metodo para encontrar la mejor ruta sin transbordo entre dos estaciones
	 * @param inicio
	 * @param destino
	 * @return nombre de la mejor ruta sin transbordo
	 */
	public String mejorRutaSinTransbordo(String inicio, String destino) {
		List<Ruta> rutasValidas = rutasSinTransbordo(inicio, destino);

		if (rutasValidas.isEmpty()) {
			return null;
		}
		double tiempoMinimo = Double.MAX_VALUE;
		Ruta rutaMejor = null;
	
		for (Ruta ruta : rutasValidas) {
			List<Estacion> estacionesRuta = getEstacionesRuta(ruta);
			int idxInicio = -1, idxDestino = -1;
	
			for (int i = 0; i < estacionesRuta.size(); i++) {
				if (estacionesRuta.get(i).getNombre().equals(inicio)) idxInicio = i;
				if (estacionesRuta.get(i).getNombre().equals(destino)) idxDestino = i;
			}
	
			if (idxInicio == -1 || idxDestino == -1 || idxDestino <= idxInicio) continue;
	
			double velocidadPromedio = getVelocidadPromedioTroncal(estacionesRuta.get(idxInicio).getnombreTroncal());
			double tiempoTotal = (1.0 / velocidadPromedio) * 60.0 * (idxDestino - idxInicio);
	
			if (tiempoTotal < tiempoMinimo) {
				tiempoMinimo = tiempoTotal;
				rutaMejor = ruta;
			}
		}
		return (rutaMejor != null) ?rutaMejor.getNombre() : null;
	}

	/**
	 * Obtiene la lista de estaciones de una ruta
	 */
	private List<Estacion> getEstacionesRuta(Ruta ruta) {
		if (ruta != null) {
			return ruta.getParadas(); 
		}
		return new ArrayList<>();
	}


	/**
	 * Obtiene la velocidad promedio (km/h) de la troncal a la que pertenece una ruta
	 */
	private double getVelocidadPromedioTroncal(String nombreTroncal) {
		Troncal troncal = troncales.get(nombreTroncal); 
		if (troncal != null) {
			return troncal.getVelocidadPromedio(); 
		}
		return 20.0;
	}
		

	/**
	 * * metodo para obtener la troncal asociada a una estacion
	 * @param nombreTroncal
	 * @return
	 */
    public Troncal getTroncal(String nombreTroncal) {
        return troncales.get(nombreTroncal);
    }

	/**
	 * * metodo para obtener la estacion asociada a una troncal
	 * @param nombreEstacion
	 * @return
	 */
    public Estacion getEstacion(String nombreEstacion) {
        return estaciones.get(nombreEstacion);
    }

	/**
	 * * metodo para agregar una troncal al sistema
	 * @param nombre
	 * @param troncal
	 */
	public void agregarTroncal(String nombre, Troncal troncal) {
		troncales.put(nombre, troncal);
	}

	/**
	 * * metodo para agregar una estacion al sistema
	 * @param nombre
	 * @param estacion
	 */
	public void agregarEstacion(String nombre, Estacion estacion) {
		estaciones.put(nombre, estacion);
	}

	/**
	 * * metodo para exportar las rutas a un archivo de texto
	 * @param inicio
	 * @param destino
	 * @param rutaArchivo
	 */
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

	/**
	 * * metodo para guardar la informacion de una troncal en un archivo de texto
	 * @param nombreTroncal
	 * @param rutaArchivo
	 */
	public void guardarTroncal(String nombreTroncal, String rutaArchivo) {
		Troncal troncal = getTroncal(nombreTroncal);
		if (troncal != null) {
			troncal.guardarInformacion(rutaArchivo);
		} else {
			System.out.println("La troncal '" + nombreTroncal + "' no existe.");
		}
	}
}
