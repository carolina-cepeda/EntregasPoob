public class Plane extends Machine {

	private int plate;
	private boolean inAir;
	private Sailor pilot;
	private Sailor copilot;

	public Plane() {
		super();
	}

	@Override
	public boolean willBeDestroyed(int longitude, int latitude) {
		Position location = getLocation();
		if (inAir) {
			return false;
		} else {
			return location.estoyAhi(longitude, latitude);
		}
	}
}
