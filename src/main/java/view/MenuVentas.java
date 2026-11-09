package view;

import controller.Inventario;
import controller.Ventas;
import models.Venta;

public class MenuVentas {
    private final Ventas ventas;
    private final Inventario inventario;
    private final Consola consola;

    public MenuVentas(Ventas ventas, Inventario inventario, Consola consola) {
        this.ventas = ventas;
        this.inventario = inventario;
        this.consola = consola;
    }

    public void mostrar() {
        int op;
        do {
            consola.titulo("       MENÚ DE VENTAS");
            System.out.println("""
                    Seleccione una opción:

                    1. Añadir venta nueva del día.
                    2. Registrar corrección o devolución.
                    3. Consultar historial del día.
                    4. Volver al menú principal.
                    """);
            System.out.println("=====================================");

            op = consola.leerEntero("");
            try {
                switch (op) {
                    case 1: registrarVenta(); break;
                    case 2: registrarCorreccion(); break;
                    case 3: verHistorial(); break;
                    case 4: System.out.println("Regresando al menú principal...\n"); break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (RuntimeException e) {
                consola.error(e.getMessage());
            }
        } while (op != 4);
    }

    private void registrarVenta() {
        int idFactura = ventas.generarNuevoIdFactura();
        boolean otra = true;
        while (otra){
            int idProducto = consola.leerEntero("Ingrese el ID del producto:");
            int cantidad = consola.leerEntero("Ingrese la cantidad solicitada:");

            Venta venta = ventas.registrarVenta(idFactura, idProducto, cantidad);
            System.out.println("\nVenta registrada exitosamente.");
            System.out.println("Resumen: " + venta.getCantidad() + "x "
                    + inventario.nombreProducto(idProducto) + " -> " + Consola.dinero(venta.getValor()));

            otra = consola.confirmar("\n¿Desea registrar otro producto?");
        }
        imprimirFactura(idFactura);
    }

    private void imprimirFactura(int idFactura) {
        Venta[] detalle = ventas.obtenerVentasPorFactura(idFactura);
        if (detalle.length == 0) return;

        System.out.println("\n==========================================================");
        System.out.println("                  FACTURA DE VENTA N° " + idFactura);
        System.out.println("                  Fecha: " + detalle[0].getFecha());
        System.out.println("==========================================================");

        long granTotal = 0;
        for (Venta v : detalle) {
            String nombreProd = inventario.nombreProducto(v.getIdProducto());
            System.out.printf("%-30s | Cant: %2d | Subtotal: %s%n",
                    nombreProd, v.getCantidad(), Consola.dinero(v.getValor()));
            granTotal += v.getValor();
        }

        System.out.println("----------------------------------------------------------");
        System.out.printf("TOTAL A PAGAR: %37s%n", Consola.dinero(granTotal));
        System.out.println("==========================================================\n");
    }
    
    private void registrarCorreccion() {
        int idProducto = consola.leerEntero("Ingrese el ID del producto:");
        int cantidad = consola.leerEntero("Ingrese la cantidad que debe corregirse (si ingresó 5 y eran 3 ingrese 2):");

        Venta correccion = ventas.registrarCorreccion(idProducto, cantidad);
        System.out.println("\n¡Corrección registrada exitosamente!");
        System.out.println("Resumen: se regresaron " + correccion.getCantidad() + "x "
                + inventario.nombreProducto(idProducto) + " -> -" + Consola.dinero(correccion.getValor()));
    }


    private void verHistorial() {
        Venta[] historial = ventas.historialDelDia();
        System.out.println("\n==========================================================================================");
        System.out.println("                                HISTORIAL DE VENTAS DEL DÍA");
        System.out.println("==========================================================================================");
        if (historial.length == 0) {
            System.out.println("No se han registrado ventas hoy.");
        }
        for (int i = 0; i < historial.length; i++) {
            Venta v = historial[i];
            System.out.printf("%-10s | Factura: %d | ID: %d | Producto: %-42s | Cantidad: %d | Total: %s%n",
                    v.getTipo(), v.getIdFactura(), v.getIdProducto(), inventario.nombreProducto(v.getIdProducto()),
                    v.getCantidad(), Consola.dinero(v.getValorConSigno()));
        }
        System.out.println("==========================================================================================");
    }
}
