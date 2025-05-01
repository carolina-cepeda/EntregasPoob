package clases;

import java.util.ArrayList;
import java.util.TreeMap;

public class Troncal {

	private String nombre;

	private double velocidadPromedio;

	private TreeMap<String,Ruta> rutas;

	private Tramo[] tramo;

	private double distancia;

	/**
	 * * Constructor de la clase Troncal
	 * @param nombre
	 * @param velocidadPromedio
	 * @param rutas
	 */
	public Troncal(String nombre, double velocidadPromedio, TreeMap<String,Ruta> rutas) {
		this.nombre = nombre;
		this.velocidadPromedio = velocidadPromedio;
		this.rutas = rutas;
	}

	/**
	 * metodo para obtener una lista de rutas que permiten el viaje entre dos estaciones
	 * @param Inicio
	 * @param destino
	 * @return
	 */
	public ArrayList<String> RutasViaje(String Inicio, String destino) {
		ArrayList<String> rutasViaje = new ArrayList<>();
		for (Ruta ruta : rutas.values()) {
			if (ruta.Viaje(Inicio, destino) != null) {
				rutasViaje.add(ruta.Viaje(Inicio, destino));
			}
		}
		return rutasViaje;
	}

	public void agregarRuta(Ruta ruta) {
		rutas.put(ruta.getNombre(), ruta);
	}
}
