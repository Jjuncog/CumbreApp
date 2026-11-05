package controller;
import models.*;

public class Inventario {
	private Catalogo catalogoAs;
	private int[] stock;

	public Inventario(Catalogo catalogo) {
		this.catalogoAs = catalogo;
		this.stock = new int[30]; //Se hace un arreglo paralelo del mismo tamaño que catalogo
		stock[0] = 60;			  // para poder asignar el stock. En la adición de nuevos productos
		stock[1] = 55;			  // se actualizarán de forma paralela ambos arreglos
		stock[2] = 62;
		stock[3] = 51;
		stock[4] = 63;
	}

	private int buscar_indice_por_id(int id) {
		Producto[] cat = catalogoAs.getArrCatalogo();
		for (int i = 0; i < cat.length; i++) {
			if (cat[i] != null && id == cat[i].getId()) {
				return i;
			}
		}
		return -1;
	}
	// Devuelve el stock del producto, o -1 si el ID no existe

	public void asignar_stock(int id, int valor) {
		int indice = buscar_indice_por_id(id);
		if (indice != -1) {
			stock[indice] = valor;
		}
	}

	public boolean existe_producto(int id) {
		return buscar_indice_por_id(id) != -1;
	}

	public int consultar_stock(int id) {
		int indice = buscar_indice_por_id(id);
		if (indice != -1) {
			return stock[indice];
		}
		return -1;
	}

	public boolean hay_stock(int id, int cantidad) {
		int stockActual = consultar_stock(id);
		return stockActual != -1 && stockActual >= cantidad;
	}

	// Para ventas y prestamos: resta unidades. Devuelve false si no se pudo.
	public boolean descontar_stock(int id, int cantidad) {
		int indice = buscar_indice_por_id(id);
		if (indice != -1 && hay_stock(id, cantidad)) {
			stock[indice] -= cantidad;
			return true;
		}
		return false;
	}

	// Para devoluciones y correcciones de venta: suma unidades.
	public boolean aumentar_stock(int id, int cantidad) {
		int indice = buscar_indice_por_id(id);
		if (indice != -1 && cantidad>0) {
			stock[indice] += cantidad;
			return true;
		}
		return false;
	}

	public void mostrar_inventario(){
		Producto[] cat = catalogoAs.getArrCatalogo();
		StringBuilder inv = new StringBuilder("\n=====================================\n" +
				                              "      INVENTARIO ACTUAL DE EQUIPOS\n" +
				                              "=====================================\n");
		for (int i = 0; i < cat.length; i++) {
			if (cat[i] != null) {
				inv.append("ID: ").append(cat[i].getId())
						.append(" | ").append(cat[i].getNombre())
						.append(" | ").append("Unidades disponibles: ")
						.append(stock[i]).append("\n");
			}
		}
		inv.append("=====================================");
		System.out.println(inv);
	}
}