package view;

import controller.Inventario;
import controller.Prestamos;
import models.Prestamo;


public class MenuPrestamos {

    private final Prestamos prestamos;
    private final Inventario inventario;
    private final Consola consola;

    public MenuPrestamos(Prestamos prestamos, Inventario inventario, Consola consola) {
        this.prestamos = prestamos;
        this.inventario = inventario;
        this.consola = consola;
    }

    public void mostrar() {
        int op;
        do {
            consola.titulo("     MENÚ DE PRÉSTAMOS");
            System.out.println("""
                    Seleccione una opción para continuar:

                    1. Añadir un préstamo
                    2. Registrar devolución de equipo
                    3. Consultar un préstamo
                    4. Ver préstamos pendientes
                    5. Volver al menú principal
                    """);
            System.out.println("=====================================");

            op = consola.leerEntero("");

            try {
                switch (op) {
                    case 1: anadirPrestamo(); break;
                    case 2: registrarDevolucion(); break;
                    case 3: consultarPrestamo(); break;
                    case 4: verPendientes(); break;
                    case 5: System.out.println("Regresando al menú principal...\n"); break;
                    default: System.out.println("Opción inválida. Ingrese una nueva opción.");
                }
            } catch(RuntimeException e) {
                consola.error(e.getMessage());
            }
        } while(op != 5);
    }

    private void anadirPrestamo() {
        boolean otra = true;
        while (otra){
        int idCliente = consola.leerEntero("Ingrese el ID del cliente:");
        String nombre = consola.leerTexto("Ingrese el nombre del cliente:");
        String contacto = consola.leerTexto("Ingrese el contacto (correo) del cliente:");
        int dias = consola.leerEntero("Ingrese los días de préstamo:");
        int idProducto = consola.leerEntero("Ingrese la ID del producto:");
        int cantidad = consola.leerEntero("Ingrese la cantidad del producto:");

        Prestamo nuevo = prestamos.crearPrestamo(idCliente, nombre, contacto, dias, idProducto, cantidad);
        System.out.println("\n¡Préstamo registrado exitosamente!");
        System.out.println(ficha(nuevo));
        otra = consola.confirmar("\n¿Desea registrar otro producto?");
        }
    }

    private void registrarDevolucion() {
        int id = consola.leerEntero("Ingrese el ID del préstamo a devolver:");
        Prestamo p = prestamos.registrarDevolucion(id);
        System.out.println("Devolución exitosa. Se regresaron " + p.getCantidad() + " unidades de "
                + inventario.nombreProducto(p.getIdProducto()) + " al inventario.");
    }

    private void consultarPrestamo() {
        int id = consola.leerEntero("Ingrese el ID del préstamo:");
        Prestamo p = prestamos.buscarPrestamo(id);
        if (p == null) {
            System.out.println("No existe el préstamo " + id + ".");
        } else {
            System.out.println(ficha(p));
        }
    }

    private void verPendientes() {
        Prestamo[] pendientes = prestamos.prestamosPendientes();
        System.out.println("\n========================== PRÉSTAMOS PENDIENTES ==========================");
        if (pendientes.length == 0) {
            System.out.println("No hay préstamos pendientes.");
        }
        for (int i = 0; i < pendientes.length; i++) {
            Prestamo p = pendientes[i];
            System.out.printf("#%-4d %-22s %-35s x%-3d hasta %s  [%s]%n",
                    p.getIdPrestamo(), p.getNombreCliente(), inventario.nombreProducto(p.getIdProducto()),
                    p.getCantidad(), p.getFechaDevolucion(), p.getEstado());
        }
        System.out.println("==========================================================================");
    }

    private String ficha(Prestamo p) {
        return "\n=================================================\n" +
               "                  PRÉSTAMO " + p.getIdPrestamo() + "\n" +
               "=================================================\n" +
               "Cliente: " + p.getNombreCliente() + " (ID: " + p.getIdCliente() + ")\n" +
               "Contacto: " + p.getContactoCliente() + "\n" +
               "Fecha del préstamo: " + p.getFechaPrestamo() + "\n" +
               "Fecha límite devolución: " + p.getFechaDevolucion() + "\n" +
               "Producto: " + inventario.nombreProducto(p.getIdProducto()) + " (ID: " + p.getIdProducto() + ")\n" +
               "Cantidad: " + p.getCantidad() + "\n" +
               "Estado préstamo: " + p.getEstado() + "\n" +
               "=================================================\n" +
               "Valor total préstamo: " + Consola.dinero(p.getValor()) + "\n" +
               "=================================================";
    }
}
