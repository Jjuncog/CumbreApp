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
                    2. Ver ficha técnica de un producto o en general
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
        int caso =consola.leerEntero("Ingrese 0 si desea consultar las fichas técnicas en general o 1 si desa consultar la ficha de un producto");
        switch (caso){
            case 0:
                String [][] matriz = new String[2][2];
                matriz[0][0]="Categoría: Equipo Anticaídas. " +
                        "Función principal: Prevenir descensos bruscos, absorber impactos " +
                        "y garantizar soporte continuo durante trabajos exhaustivos en alturas.";
                matriz[0][1]="Categoría: Hardware de Conexión. " +
                        "Función principal: Proveer puntos de anclaje confiables " +
                        "y articular los distintos componentes de los sistemas de seguridad.";
                matriz[1][0]="Categoría: Herramientas Operativas. " +
                        "Función principal: Facilitar la ejecución manual de tareas de instalación, " +
                        "ensamble y mantenimiento, optimizando el esfuerzo físico del usuario.";
                matriz[1][1]="Categoría: Protección Personal. " +
                        "Función principal: Mitigar el riesgo de lesiones " +
                        "y salvaguardar la integridad física del operario frente a riesgos laborales generales.";

                System.out.println("        [EQUIPO ANTICAIDAS]     |    [HARDWARE DE CONEXIÓN]");
                System.out.println("--------------------------------------------");
                System.out.println("    [HERRAMIENTAS OPERATIVAS]   |    [PROTECCIÓN PERSONAL]");


                int i=consola.leerEntero("Ingrese la fila de que ficha técnica quiere consultar (de 0 a 1): ");
                int j=consola.leerEntero("Ingrese la columna de que ficha técnica quiere consultar (de 0 a 1): ");
                if (i>1 || j>1 || i<0 || j<0){
                    System.out.println(" NÚMERO INVÁLIDO"); }
                else{
                    System.out.println(matriz[i][j]);}
                    break;
            case 1:

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

        break;

        default:
            System.out.println("INGRESA UNA OPCIÓN VÁLIDA");}
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
