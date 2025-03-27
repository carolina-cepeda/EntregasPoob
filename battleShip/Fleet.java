import java.util.ArrayList;

public class Fleet {

	private int name;
	private ArrayList<Machine> machines;
	private ArrayList<Sailor> sailors;
	private Board board;
	private ArrayList<Machine> machinesDestruidas = new ArrayList<>();
	private ArrayList<Sailor> sailorsDestruidos = new ArrayList<>();

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

	public void registrarDestruccion(Machine machine) {
		machinesDestruidas.add(machine);
		System.out.println("Registrado en la flota: " + machine.getName() + " se auto-destruyó.");
	}

	public void registrarDestruccion(Sailor sailor) {
		sailorsDestruidos.add(sailor);
		System.out.println("Registrado en la flota: " + sailor.getName() + " se auto-destruyó.");
	}

	public void mostrarDestruidos() {
		System.out.println("\n **Resumen de elementos auto-destruidos en la flota:**");

		for (Machine m : machinesDestruidas) {
			System.out.println("- " + m.getName() + ": " + m.getCausaDestruccion());
		}

		for (Sailor s : sailorsDestruidos) {
			System.out.println("- " + s.getName() + ": " + s.getCausaDestruccion());
		}
	}
}
