import java.util.ArrayList;

public class AircraftCarrier extends Ship {

	private int number;
	private int capacity;
	private ArrayList<Plane> airPlanes;

	public AircraftCarrier() {
		super();
	}

	@Override
	public boolean weakMachines() {
		for (Plane p : airPlanes) {
			if (p.weakMachines()) {
				return true;
			}
		}
		return false;
	}
}
