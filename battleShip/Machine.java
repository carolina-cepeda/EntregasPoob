public class Machine {

	private Position location;

	public Machine() {

	}

	public void moveNorth() {
		location.moveNorth();
	}

	public void advance(int dLon, int dLat) {
		location.advance(dLon, dLat);
	}

	public boolean willBeDestroyed(int longitude, int latitude) {
		return location.estoyAhi(longitude, latitude);
	}

	public Position getLocation() {
		return this.location;
	}
}
