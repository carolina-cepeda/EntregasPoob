package dominio;

public abstract class EntrenadorMaquina extends Entrenador {

	public abstract void decidirAccion();

	public EntrenadorMaquina(String nombre, String color) {
		super(nombre, color);
	}
}
