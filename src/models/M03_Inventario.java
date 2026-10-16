package models;

/**
 * Inventario de CumbreApp.
 * Guarda el stock de cada producto del catalogo (IDs 1001 a 1005) con una
 * variable por producto, sin arreglos ni listas (restriccion de la Entrega 1).
 */
public class M03_Inventario {

	private int stock1001;
	private int stock1002;
	private int stock1003;
	private int stock1004;
	private int stock1005;

	public M03_Inventario() {
		stock1001 = 60;
		stock1002 = 55;
		stock1003 = 62;
		stock1004 = 51;
		stock1005 = 63;
	}

	public M03_Inventario(int s1001, int s1002, int s1003, int s1004, int s1005) {
		stock1001 = Math.max(0, s1001);
		stock1002 = Math.max(0, s1002);
		stock1003 = Math.max(0, s1003);
		stock1004 = Math.max(0, s1004);
		stock1005 = Math.max(0, s1005);
	}

	// Devuelve el stock del producto, o -1 si el ID no existe
	private int obtener_stock(int id) {
		switch (id) {
			case 1001: return stock1001;
			case 1002: return stock1002;
			case 1003: return stock1003;
			case 1004: return stock1004;
			case 1005: return stock1005;
			default:   return -1;
		}
	}

	private void asignar_stock(int id, int valor) {
		switch (id) {
			case 1001: stock1001 = valor; break;
			case 1002: stock1002 = valor; break;
			case 1003: stock1003 = valor; break;
			case 1004: stock1004 = valor; break;
			case 1005: stock1005 = valor; break;
			default: break;
		}
	}

	public boolean existe_producto(int id) {
		return obtener_stock(id) != -1;
	}

	public int consultar_stock(int id) {
		return obtener_stock(id);
	}

	public boolean hay_stock(int id, int cantidad) {
		if (!existe_producto(id) || cantidad <= 0) {
			return false;
		}
		return obtener_stock(id) >= cantidad;
	}

	// Para ventas y prestamos: resta unidades. Devuelve false si no se pudo.
	public boolean descontar_stock(int id, int cantidad) {
		if (!hay_stock(id, cantidad)) {
			return false;
		}
		asignar_stock(id, obtener_stock(id) - cantidad);
		return true;
	}

	// Para devoluciones y correcciones de venta: suma unidades.
	public boolean aumentar_stock(int id, int cantidad) {
		if (!existe_producto(id) || cantidad <= 0) {
			return false;
		}
		asignar_stock(id, obtener_stock(id) + cantidad);
		return true;
	}

	public void mostrar_inventario() {
		String inventario = "INVENTARIO ACTUAL DE EQUIPO DE ALTURAS\n";
		inventario += "ID: 1001 || Unidades disponibles: " + stock1001 + "\n";
		inventario += "ID: 1002 || Unidades disponibles: " + stock1002 + "\n";
		inventario += "ID: 1003 || Unidades disponibles: " + stock1003 + "\n";
		inventario += "ID: 1004 || Unidades disponibles: " + stock1004 + "\n";
		inventario += "ID: 1005 || Unidades disponibles: " + stock1005 + "\n";
		System.out.println(inventario);
	}

}
