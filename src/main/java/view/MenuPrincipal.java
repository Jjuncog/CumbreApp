package view;

public class MenuPrincipal {

    private final Consola consola;
    private final MenuInventario menuInventario;
    private final MenuPrestamos menuPrestamos;
    private final MenuVentas menuVentas;
    private final MenuFinalizarDia menuFinalizarDia;

    public MenuPrincipal(Consola consola, MenuInventario menuInventario, MenuPrestamos menuPrestamos,
                         MenuVentas menuVentas, MenuFinalizarDia menuFinalizarDia) {
        this.consola = consola;
        this.menuInventario = menuInventario;
        this.menuPrestamos = menuPrestamos;
        this.menuVentas = menuVentas;
        this.menuFinalizarDia = menuFinalizarDia;
    }
    public void mostrar() {
        System.out.println("Bienvenido a CumbreApp, el sistema de gestión de Cumbre Colombia");
        boolean salir = false;

        while(!salir) {
            consola.titulo("         MENÚ PRINCIPAL");
            System.out.println("""
                    Seleccione una opción para continuar:

                    1. Catálogo e inventario de productos.
                    2. Menú de préstamos.
                    3. Menú de ventas.
                    4. Finalizar la jornada.
                    """);
            System.out.println("=====================================");

            switch (consola.leerEntero("")) {
                case 1: menuInventario.mostrar(); break;
                case 2: menuPrestamos.mostrar(); break;
                case 3: menuVentas.mostrar(); break;
                case 4: salir = menuFinalizarDia.mostrar(); break;
                default: System.out.println("Opción inválida, ingrese de nuevo.");
            }
        }
    }
}
