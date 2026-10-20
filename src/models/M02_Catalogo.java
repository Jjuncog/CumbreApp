package models;

public class M02_Catalogo {
	private M01_Producto producto1;
	private M01_Producto producto2;
	private M01_Producto producto3;
	private M01_Producto producto4;
	private M01_Producto producto5;

	public M02_Catalogo(){
		producto1=new M01_Producto(1001,"Arnés de seguridad",255000);
		producto2=new M01_Producto(1002,"Línea de vida",200000);
		producto3=new M01_Producto(1003,"Eslinga con absorbedor",345000); // Acortado levemente para formato
		producto4=new M01_Producto(1004,"Retráctil anticaídas",370000);
		producto5=new M01_Producto(1005,"Mosquetón de seguridad",189000);
	}

	public M01_Producto buscar_producto_por_id(int id) {
    	if (producto1.getId() == id) return producto1;
    	if (producto2.getId() == id) return producto2;
    	if (producto3.getId() == id) return producto3;
    	if (producto4.getId() == id) return producto4;
    	if (producto5.getId() == id) return producto5;
   		return null; // Retorna null si el ID del producto no existe
	}

	public int obtener_precio_producto(int id) {
    	M01_Producto producto = buscar_producto_por_id(id);
    	if (producto != null) {
        	return producto.getPrecio();
    	}
    	return -1; // Retorna -1 si el producto no se encuentra
	}
	

	public void mostrar_catalogo(){
		String catalogo = "\n============================================\n";
		catalogo += "       CATÁLOGO DE EQUIPOS DISPONIBLE\n";
		catalogo += "============================================\n";
		catalogo += "ID: " + producto1.getId() + " | " + producto1.getNombre() + " | $" + producto1.getPrecio() + "\n";
		catalogo += "ID: " + producto2.getId() + " | " + producto2.getNombre() + " | $" + producto2.getPrecio() + "\n";
		catalogo += "ID: " + producto3.getId() + " | " + producto3.getNombre() + " | $" + producto3.getPrecio() + "\n";
		catalogo += "ID: " + producto4.getId() + " | " + producto4.getNombre() + " | $" + producto4.getPrecio() + "\n";
		catalogo += "ID: " + producto5.getId() + " | " + producto5.getNombre() + " | $" + producto5.getPrecio() + "\n";
		catalogo += "============================================";
		System.out.println(catalogo);
	}

}