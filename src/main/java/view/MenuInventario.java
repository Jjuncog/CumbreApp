package view;

import controller.Inventario;
import models.Producto;

public class MenuInventario {

    private final Inventario inventario;
    private final Consola consola;

    public MenuInventario(Inventario inventario, Consola consola) {
        this.inventario = inventario;
        this.consola = consola;
    }

    public void mostrar() {
        int op;
        do {
            consola.titulo("  CATÁLOGO E INVENTARIO");
            System.out.println("""
                    
                    1. Ver catálogo e inventario
                    2. Ver ficha técnica de un producto
                    3. Agregar un producto nuevo
                    4. Actualizar el precio de un producto
                    5. Registrar entrada de unidades
                    6. Retirar un producto del catálogo
                    7. Volver al menú principal
                    """);
            System.out.println("=====================================");

            op = consola.leerEntero("");
            try {
                switch (op) {
                    case 1: verInventario(); break;
                    case 2: verFicha(); break;
                    case 3: agregarProducto(); break;
                    case 4: actualizarPrecio(); break;
                    case 5: agregarUnidades(); break;
                    case 6: retirarProducto(); break;
                    case 7: System.out.println("Regresando al menú principal...\n"); break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (RuntimeException e) {
                consola.error(e.getMessage());
            }
        } while (op != 7);
    }

    private void verInventario() {
        Producto[] productos = inventario.listarProductos();
        System.out.println("\n==========================================================================================");
        System.out.println("                              CATÁLOGO E INVENTARIO ACTUAL");
        System.out.println("==========================================================================================");

        if (productos.length == 0) {
            System.out.println("No hay productos registrados. Use la opción 3 para agregar uno.");
        }


        for(int i = 0; i < productos.length; i++) {
            Producto p = productos[i];
            System.out.printf("ID: %d | %-42s | %-17s | %12s | Unidades: %d%n",
                    p.getId(), p.getNombre(), p.getCategoria(), Consola.dinero(p.getPrecio()), p.getCantidad());
        }
        System.out.println("==========================================================================================");
    }

    private void verFicha() {
        int id = consola.leerEntero("Ingrese el ID del producto:");
        Producto p = inventario.buscarProducto(id);

        if (p == null) {
            System.out.println("No existe un producto con ID " + id + ".");
            return;
        }
        System.out.println("\n" + p.getNombre() + " (ID " + p.getId() + ")"
                + (p.isActivo() ? "" : "  [RETIRADO]"));
        System.out.println("Precio: " + Consola.dinero(p.getPrecio()) + " | Unidades: " + p.getCantidad());
        System.out.println(p.fichaTecnica());
    }

    private void agregarProducto() {
        System.out.println("Categorías disponibles:");
        for (int i = 0; i < Producto.CATEGORIAS.length; i++) {
            System.out.println((i + 1) + ". " + Producto.CATEGORIAS[i]);
        }
        int opcion = consola.leerEntero("Seleccione la categoría:");
        if (opcion < 1 || opcion > Producto.CATEGORIAS.length) {
            consola.error("Categoría inválida.");
            return;
        }
        String nombre = consola.leerTexto("Nombre del producto:");
        int precio = consola.leerEntero("Precio unitario:");
        int cantidad = consola.leerEntero("Unidades iniciales:");

        Producto nuevo = inventario.agregarProducto(Producto.CATEGORIAS[opcion - 1], nombre, precio, cantidad);
        System.out.println("Producto agregado con ID " + nuevo.getId() + ".");
    }

    private void actualizarPrecio() {
        int id = consola.leerEntero("Ingrese el ID del producto:");
        int precio = consola.leerEntero("Nuevo precio:");
        Producto p = inventario.actualizarPrecio(id, precio);
        System.out.println("Precio de " + p.getNombre() + " actualizado a " + Consola.dinero(p.getPrecio()) + ".");
    }

    private void agregarUnidades() {
        int id = consola.leerEntero("Ingrese el ID del producto:");
        int cantidad = consola.leerEntero("Unidades que ingresan:");
        Producto p = inventario.agregarUnidades(id, cantidad);
        System.out.println("Stock de " + p.getNombre() + " actualizado: " + p.getCantidad() + " unidades.");
    }

    private void retirarProducto() {
        int id = consola.leerEntero("Ingrese el ID del producto a retirar:");
        if (consola.confirmar("El producto ya no se podrá vender ni prestar. ¿Confirma?")) {
            Producto p = inventario.retirarProducto(id);
            System.out.println(p.getNombre() + " fue retirado del catálogo.");
        }
    }
}
