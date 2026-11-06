package controller;

import models.Producto;

import java.util.Scanner;
import java.util.Arrays;

public class Ventas {

    // Atributos
    private Scanner sc;
    private Catalogo catalogo;
    private Inventario inventario;
    private String[] historial= new String[100];
    private long gananciasAcumuladas;
	private static int contador=0;

    // Constructor
    public Ventas(Catalogo catalogo, Inventario inventario, Scanner sc) {
        this.catalogo = catalogo;
        this.inventario = inventario;
        this.sc = sc;
        gananciasAcumuladas = 0L;
    }

    // Submenú de ventas
    public void mostrarMenuVentas() {
        int opcion;

        do {
        	System.out.println("\n=====================================");
   	     	System.out.println("           MENÚ DE VENTAS");
   	     	System.out.println("=====================================");
            System.out.println(
            				   "Seleccione una opción:\n" +
            				   "\n1. Añadir venta nueva del día.\n" +
                               "2. Registrar corrección o devolución.\n" +
                               "3. Consultar historial.\n" +
                               "4. Volver al menú principal.\n");
            System.out.println("=====================================");

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
                    System.out.println("Regresando al menú principal...\n");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 4);
    }

    // Registrar venta
    public void registrarVenta() {
        int idProducto, cantidad, precioUnitario, salida;
        long valorVenta;
        Producto producto;

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

            historial[contador++] = ("VENTA | ID: " + idProducto
                    + " | Producto: " + producto.getNombre()
                    + " | Cantidad: " + cantidad
                    + " | Total: $" + valorVenta + "\n");

            System.out.println("\nVenta Registrada Exitosamente.");
            System.out.println("Resumen: " + cantidad + "x " + producto.getNombre() + " -> $" + valorVenta);

            System.out.print("\n¿Desea registrar otro producto? 1=Sí, 0=Volver al menú de ventas: ");
            salida = sc.nextInt();

        } while (salida == 1);
    }

    // Registrar corrección o devolución
    private void registrarCorrecion() {
        int idProducto, cantidad, precioUnitario, salida;
        long valorCorrecion;
        Producto producto;

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

            historial[contador++] = ("CORRECCIÓN | ID: " + idProducto
                    + " | Producto: " + producto.getNombre()
                    + " | Cantidad: " + cantidad
                    + " | Valor corrección: -$" + valorCorrecion + "\n");

            System.out.println("\n¡Corrección Registrada Exitosamente!");
            System.out.println("Resumen: Se regresaron " + cantidad + "x " + producto.getNombre() + " -> -$" + valorCorrecion);

            System.out.print("\n¿Desea registrar otra corrección? 1=Sí, 0=Volver al menú de ventas: ");
            salida = sc.nextInt();

        } while (salida == 1);
    }

    // Consultar historial
    private void consultarHistorial() {
    	System.out.println("\n==========================================================================================");
	    System.out.println("                                    HISTORIAL DE VENTAS");
	    System.out.println("==========================================================================================");
        if (historial.isEmpty()) {
            System.out.println("No se han registrado ventas.");
        } else {
            System.out.print(Arrays.toString(historial));
        }
        System.out.println("==========================================================================================");
        
        System.out.print("\nPresione 1 para volver al menú de ventas: ");
        sc.nextInt();
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
        return contador;
    }
}
