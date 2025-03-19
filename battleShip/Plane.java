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
		if (inAir) {
			return false;
		} else {
			return this.estoyAhi(longitude, latitude);
		}
	}

	@Override
	public boolean weakMachines() {
		return (pilot == null);
	}
}
