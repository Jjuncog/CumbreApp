package controller;
import models.*;

public class Catalogo {

	private Producto[] catalogo;

	public Catalogo(){
		catalogo = new Producto[30];
		catalogo[0] = new Proteccion(1001, "Casco de seguridad dieléctrico", 85000);
		catalogo[1] = new Anticaidas(1002, "Arnes de cuerpo entero de 4 argollas", 200000);
		catalogo[2] = new HardwareConexion(1003, "Mosquetón de acero con cierre automático", 189000);
		catalogo[3] = new Herramientas(1004, "Llave de impacto con anilla de sujeción", 420000);
		catalogo[4] = new Anticaidas(1005, "Polea para trabajo en alturas", 135000);
	}
	public Producto buscar_producto_por_id(int id) {
		for (int i = 0; i <catalogo.length; i++ ) {
			if (catalogo[i] != null && id == catalogo[i].getId() ) {
				return catalogo[i];
			}
		}
        return null; // Retorna null si el objeto con esa id no existe
    }

	public int obtener_precio_producto(int id) {
    	Producto producto = buscar_producto_por_id(id);
    	if (producto != null) {
        	return producto.getPrecio();
    	}
    	return -1; // Retorna -1 si el producto no se encuentra
	}

	public void mostrar_catalogo(){
		StringBuilder cat = new StringBuilder("\n============================================\n" +
				                                "      CATÁLOGO DE EQUIPOS DISPONIBLE\n" +
                					            "============================================\n");
		for (int i = 0; i < catalogo.length; i++) {
			if (catalogo[i] != null) {
				cat.append("ID: ").append(catalogo[i].getId())
						.append(" | ").append(catalogo[i].getNombre())
						.append(" | $").append(catalogo[i].getPrecio())
						.append("\n");
			}
		}
		cat.append("==============================================");
		System.out.println(cat);
	}

	public Producto[] getArrCatalogo() {return  catalogo;}
}