package clases;

import java.util.ArrayList;
import java.util.TreeMap;

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


}
