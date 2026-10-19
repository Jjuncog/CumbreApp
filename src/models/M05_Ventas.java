
package models;

import java.util.Scanner;

public class M05_Ventas {

    // Atributos
    private Scanner sc;
    private M02_Catalogo catalogo;
    private M03_Inventario inventario;
    private String historial;
    private long gananciasAcumuladas;

    // Constructor
    public M05_Ventas(M02_Catalogo catalogo, M03_Inventario inventario, Scanner sc) {
        this.catalogo = catalogo;
        this.inventario = inventario;
        this.sc = sc;
        historial = "";
        gananciasAcumuladas = 0L;
    }

    // Submenú de ventas
    public void mostrarMenuVentas() {
        int opcion;

        do {
        	System.out.println("\n====================================");
   	     	System.out.println("          MENÚ DE VENTAS");
   	     	System.out.println("====================================");
            System.out.println(
            				   "Seleccione una opcion:\n" +
            				   "\n1. Añadir venta nueva del día.\n" +
                               "2. Registrar corrección o devolución.\n" +
                               "3. Consultar historial.\n" +
                               "4. Volver al menú principal.\n");
            System.out.println("====================================");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    registrarVenta();
                    break;
                case 2:
                    registrarCorrecion();
                    break;
                case 3:
                    consultarHistorial();
                    break;
                case 4:
                    System.out.println("Sistema de venta finalizado");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 4);
    }

    // Registrar venta
    public void registrarVenta() {
        int idProducto;
        int cantidad;
        int precioUnitario;
        long valorVenta;
        int salida;
        M01_Producto producto;

        do {
            System.out.print("Ingrese el ID del producto: ");
            idProducto = sc.nextInt();

            producto = catalogo.buscar_producto_por_id(idProducto);

            if (producto == null) {
                System.out.println("ID incorrecto, ingrese uno válido");
                return;
            }

            System.out.print("Ingrese la cantidad solicitada: ");
            cantidad = sc.nextInt();

            if (cantidad <= 0) {
                System.out.println("Cantidad inválida");
                return;
            }

            if (!inventario.hay_stock(idProducto, cantidad)) {
                return;
            }

            precioUnitario = producto.getPrecio();
            valorVenta = (long) precioUnitario * cantidad;

            if (!inventario.descontar_stock(idProducto, cantidad)) {
                return;
            }

            gananciasAcumuladas += valorVenta;

            historial += "VENTA | ID: " + idProducto
                    + " | Producto: " + producto.getNombre()
                    + " | Cantidad: " + cantidad
                    + " | Total: $" + valorVenta + "\n";

            System.out.println("Venta: " + cantidad + " "
                    + producto.getNombre() + " -> $" + valorVenta);

            System.out.print("¿Terminó el registro? 1=Si 0=No -> ");
            salida = sc.nextInt();

            System.out.println("Registro exitoso");

        } while (salida != 1);
    }

    // Registrar corrección o devolución
    private void registrarCorrecion() {
        int idProducto;
        int cantidad;
        int precioUnitario;
        long valorCorrecion;
        int salida;
        M01_Producto producto;

        do {
            System.out.print("Ingrese el ID del producto: ");
            idProducto = sc.nextInt();

            producto = catalogo.buscar_producto_por_id(idProducto);

            if (producto == null) {
                System.out.println("ID incorrecto, ingrese uno válido");
                return;
            }

            System.out.print(
                    "Ingrese la cantidad que debe corregirse "
                            + "(si ingresó 5 y eran 3 ingrese 2): ");

            cantidad = sc.nextInt();

            if (cantidad <= 0) {
                System.out.println("Cantidad inválida");
                return;
            }

            precioUnitario = producto.getPrecio();
            valorCorrecion = (long) precioUnitario * cantidad;

            if (!inventario.aumentar_stock(idProducto, cantidad)) {
                System.out.println("Operación inválida");
                return;
            }

            gananciasAcumuladas -= valorCorrecion;

            historial += "CORRECCION | ID: " + idProducto
                    + " | Producto: " + producto.getNombre()
                    + " | Cantidad: " + cantidad
                    + " | Valor corrección: $" + valorCorrecion + "\n";

            System.out.println("Corrección: " + cantidad + " "
                    + producto.getNombre() + " -> $" + valorCorrecion);

            System.out.print("¿Terminó el registro? 1=Si 0=No -> ");
            salida = sc.nextInt();

            System.out.println("Registro exitoso");

        } while (salida != 1);
    }

    // Consultar historial
    private void consultarHistorial() {
        if (historial.isEmpty()) {
            System.out.println("No se han registrado ventas");
            return;
        }

        System.out.println(historial);
    }

    // Consultar ganancias acumuladas
    public long getGananciasAcumuladas() {
        return gananciasAcumuladas;
    }

    // Contar ventas y correcciones registradas
    public int getCantidadMovimientos() {
        if (historial.isEmpty()) {
            return 0;
        }

        return historial.split("\n").length;
    }
}
