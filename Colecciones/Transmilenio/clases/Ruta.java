package clases;


import java.util.ArrayList;
import java.util.HashMap;

public class Ruta {

	private String nombre;
	private HashMap<String, Estacion> paradas = new HashMap<String, Estacion>();

	/**
	 * * Constructor de la clase Ruta
	 * @param Inicio
	 * @param destino
	 * @return
	 */
		public Ruta(String nombre,HashMap<String, Estacion> paradas) {
			this.nombre = nombre;
		}

	/**
	 * * metodo para encontrar si la ruta permite el viaje entre dos estaciones
	 * sin hacer transbordo
	 * @param Inicio
	 * @param destino
	 * @return ruta
	 */
	public String Viaje(String Inicio, String destino) {
		
		for (String nombreEstacion : paradas.keySet()) {
			if (nombreEstacion.equals(Inicio) && paradas.containsKey(destino)) {
				return nombre;
			}
		}
		return null; // No se encontró una ruta directa entre las estaciones
	}

	/**
	 * * metodo para agregar una estacion a la ruta
	 * @param estacion
	 */
	public void agregarEstacion(Estacion estacion) {
		paradas.put(estacion.getNombre(), estacion);
	}

	/**
	 * metodo para obtener el nombre de la ruta
	 * @return nombre de la ruta
	 */
	public String getNombre() {
		return nombre;
	}
}
