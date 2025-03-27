import java.util.ArrayList;

public class Ship extends Machine {

	private ArrayList<Sailor> sailors;
	private ArrayList<capsulaSubmarina> capsulas;

	public Ship(int name) {
		super(name);
		this.sailors = new ArrayList<>();
		this.capsulas = new ArrayList<>();
	}

	public int getnumSailors() {
		return sailors.size();
	}

	public void agregarSailor(Sailor sailor) {
		sailors.add(sailor);
	}

	@Override
	public boolean weakMachines() {
		return (getnumSailors() < 5);
	}

	@Override
	public void autoDestruir(String causa) {
		super.autoDestruir(causa);
	}

	public void agregarCapsula(capsulaSubmarina capsula) {
		capsulas.add(capsula);
		capsula.setNodriza(this);
	}
}
