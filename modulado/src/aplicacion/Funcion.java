package aplicacion;

import java.util.ArrayList;

public class Funcion {
	private String nombre;
	private ArrayList<String> internas;
	private String relleno;
	
	public Funcion(String nombre, ArrayList<String> internas) 
	{
		this.nombre=nombre;
		this.internas= internas;
	}

	public String getNombre() {
		return nombre;
	}

	public ArrayList<String> getInternas() {
		return internas;
	}

	public String getRelleno() {
		return relleno;
	}

	public void setRelleno(String relleno) {
		this.relleno = relleno;
	}
	

}
