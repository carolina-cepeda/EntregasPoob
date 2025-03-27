public class Machine {
	private int name;
	private Position location;
	private String causaDestruccion;

	public Machine(int name) {
		this.name = name;
		this.causaDestruccion = null;
	}

	public void moveNorth() {
		location.moveNorth();
	}

	public void advance(int dLon, int dLat) {
		location.advance(dLon, dLat);
	}

	public boolean willBeDestroyed(int longitude, int latitude) {
		return estoyAhi(longitude, latitude);
	}

	public boolean estoyAhi(int longitude, int latitude) {
		return location.estoyAhi(longitude, latitude);
	}

	public boolean weakMachines() {
		return false;
	}

	public void autoDestruir(String causa) {
		this.causaDestruccion = causa;
		System.out.println(name + "se ha auto-destruido porqué" + causa);
	}

	public String getCausaDestruccion() {
		return this.causaDestruccion;
	}

	public int getName() {
		return this.name;
	}

}
