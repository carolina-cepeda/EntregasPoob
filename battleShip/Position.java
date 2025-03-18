
import java.lang.classfile.instruction.ThrowInstruction;

public class Position {

	private int longitude;

	private int latitude;

	public Position(int longitude, int latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public void moveNorth() {
		this.latitude += 1;
	}

	public void advance(int dLon, int dLat) {
		this.longitude += dLon;
		this.latitude += dLat;
	}

	public boolean estoyAhi(int longitude, int latitude) {
		return (this.longitude == longitude && this.latitude == latitude);
	}
}
