package dominio;

import java.util.*;

public class Pocion extends Item {

	private String nombre;

	private int puntosSanacion;
	
	private Map<String, Object> tiposPocion = new HashMap<String, Object>() {{
        put("Potion", 20);
        put("SuperPotion",50);
        put("HyperPotion",200);
        put("Revive",1/2);
    }};
	
    /**
     * constructor de objeto poción
     * @param nombre
     */
	public Pocion(String nombre) {
		super(nombre);
		this.puntosSanacion = (int) tiposPocion.get(nombre);
	}

	public void usar(Pokemon p) {
		
		if (p.getSalud()> 0) {
			p.aumentoSalud(puntosSanacion);
		}
		
		else {
			p.aumentoSalud(p.getSaludInicial() * (int) tiposPocion.get(nombre));
		}

	}

}
