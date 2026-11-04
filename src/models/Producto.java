package models;

public abstract class Producto {
	
	private int id, precio;
	private String nombre; 
	
	public Producto(int id, String nombre, int precio) {
		this.id = id; this.nombre = nombre; this.precio = precio;
	}
	public abstract String fichaTecnica(); // Metodo abstracto que será manipulado en las subclases

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public int getPrecio() {return precio;}
	public void setPrecio(int precio) {this.precio = precio;}

}
