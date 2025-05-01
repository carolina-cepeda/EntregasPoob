package clases;

public class Estacion {

	private String nombre;

	private String nivelDeOcupacion;

	private int tiempoDeEspera;

	private String nombreTroncal;

	public Estacion(String nombre, int tiempoDeEspera, String nivelDeOcupacion, String nombreTroncal) {	
		this.nombre = nombre;
		this.nivelDeOcupacion = nivelDeOcupacion;
		this.tiempoDeEspera = tiempoDeEspera;
		this.nombreTroncal = nombreTroncal;
	}
	public int getTiempoDeEspera() {
		return tiempoDeEspera;
	}

	public String getnombreTroncal() {
		return nombreTroncal;
	}

	public String getNombre() {
		return nombre;
	}

}
