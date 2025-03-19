import java.util.ArrayList;

public class Ship extends Machine {

	private ArrayList<Sailor> sailors;

	public Ship() {
		super();
		this.sailors = new ArrayList<>();

	}

	public int getnumSailors() {
		return sailors.size();
	}

	@Override
	public boolean weakMachines() {
		return (getnumSailors() < 5);
	}
}
