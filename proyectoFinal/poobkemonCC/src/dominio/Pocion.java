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
	
	public Pocion(String nombre) {
		super(nombre);
		this.puntosSanacion = (int) tiposPocion.get(nombre);
	}

	public void usar(Pokemon p) {
		
		if (p.getSalud()> 0) {
			p.AumentoSalud(puntosSanacion);
		}
		
		else {
			p.aumentoSalud(p.getSaludInicial() * (double) tiposPocion.get(nombre));
		}

	}

}
