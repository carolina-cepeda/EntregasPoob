package dominio;

import java.io.Serializable;

/**
 * clase abstracta item 
 */
public abstract class Item  implements Serializable{

	private String nombre;
/**
 * constructor
 * @param nombre
 */
	public Item(String nombre) {
		this.nombre = nombre;
	}
/**
 * getter del nombre
 * @return
 */
	public String getNombre() {
		return nombre;
	}
/**
 * metodo para usar un item en un pokemon
 * @param p
 */
	public abstract void usar(Pokemon p);

}
