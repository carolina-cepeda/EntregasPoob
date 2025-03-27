public class Sailor {

	private int name;
	private int rank;
	private String causaDestruccion;

	public Sailor(int name, int rank) {
		this.name = name;
		this.rank = rank;

	}

	public void autoDestruir(String causa) {
		this.causaDestruccion = causa;
		System.out.println("Marino" + name + "se ha autodestruido porqué" + causa);
	}

	public String getCausaDestruccion() {
		return this.causaDestruccion;
	}

	public int getName() {
		return this.name;
	}

}
