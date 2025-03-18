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

	public ArrayList<Machine> weakMachines() {
		ArrayList<Machine> weak = new ArrayList<>();
		for (Machine m : machines) {
			if (m.weakMachines()) {
				weak.add(m);
			}
		}
		return weak;
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

	public boolean isGoodAttack(int longitude, int latitude) {
		for (Machine m : machines) {
			if (m.estoyAhi(longitude, latitude)) {
				return false;
			}
		}
		return true;
	}

	public void attack(int lon, int lat) {
		for (Machine m : machines) {
			if (!m.weakMachines()) {
				int x = 0;
				int y = 0;

				while (x != lon || y != lat) {
					if (x < lon) {
						m.advance(1, 0);
						x++;
					} else if (x > lon) {
						x--;
						m.advance(-1, 0);
					}
					if (y < lat) {
						y++;
						m.advance(0, 1);
					} else if (y > lat) {
						y--;
						m.advance(0, -1);
					}
				}
			}
		}
	}
}
