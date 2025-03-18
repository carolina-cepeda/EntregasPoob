import java.util.ArrayList;

public class Fleet {

	private int name;
	private ArrayList<Machine> machines;
	private ArrayList<Sailor> sailors;
	private Board board;

	public Fleet(int name) {
		this.name = name;
		machines = new ArrayList<>();
		sailors = new ArrayList<>();
		board = new Board();
	}

	public void moveNorth() {
		for (Machine m : machines) {
			m.moveNorth();
		}
	}

	public void advance(int dLon, int dLat) {
		for (Machine m : machines) {
			m.advance(dLon, dLat);
		}
	}

	public ArrayList<Machine> willBeDestroyed(int longitude, int latitude) {
		ArrayList<Machine> destruidas = new ArrayList<>();
		for (Machine m : machines) {
			if (m.willBeDestroyed(longitude, latitude)) {
				destruidas.add(m);
			}
		}
		return destruidas;
	}

}
