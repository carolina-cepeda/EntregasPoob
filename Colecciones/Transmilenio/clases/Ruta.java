package clases;


import java.util.List;

public class Ruta {

	private String nombre;
	private List< Estacion > paradas;

	/**
	 * * Constructor de la clase Ruta
	 * @param Inicio
	 * @param destino
	 * @return
	 */
		public Ruta(String nombre,List<Estacion> paradas) {
			this.nombre = nombre;
			this.paradas = paradas;

		}

	/**
	 * * metodo para encontrar si la ruta permite el viaje entre dos estaciones
	 * sin hacer transbordo
	 * @param Inicio
	 * @param destino
	 * @return ruta
	 */
	public String Viaje(String inicio, String destino) {
		boolean inicioEncontrado = false;
	
		for (Estacion estacion : paradas) {
			if (estacion.getNombre().equals(inicio)) {
				inicioEncontrado = true;
			}
	
			if (inicioEncontrado && estacion.getNombre().equals(destino)) {
				return nombre; 
			}
		}
		return null;
	}
	
	/**
	 * metodo para añadir una estación
	 * @param estacion 
	 * @return
	 */
	public void agregarEstacion(Estacion estacion) {
		paradas.add(estacion);
	}
	

	/**
	 * metodo para obtener el nombre de la ruta
	 * @return nombre de la ruta
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * metodo que retorna las paradas 
	 */
	public List<Estacion> getParadas() {
		return paradas;
	}
}
