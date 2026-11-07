package view;

import controller.FinalizarDia;

public class MenuFinalizarDia {

    private final FinalizarDia finalizarDia;
    private final Consola consola;

    public MenuFinalizarDia(FinalizarDia finalizarDia, Consola consola) {
        this.finalizarDia = finalizarDia;
        this.consola = consola;
    }

    public boolean mostrar() {
        try {
            System.out.println("\n==================================");
            System.out.println("          CIERRE DEL DÍA");
            System.out.println("==================================");
            System.out.println("Fecha: " + finalizarDia.getFecha());
            System.out.println("Ganancias por ventas: " + Consola.dinero(finalizarDia.gananciasVentas()));
            System.out.println("Ventas y correcciones registradas: " + finalizarDia.movimientosVentas());
            System.out.println("Ganancias por préstamos: " + Consola.dinero(finalizarDia.gananciasPrestamos()));
            System.out.println("Préstamos registrados: " + finalizarDia.prestamosRegistrados());
            System.out.println("----------------------------------");
            System.out.println("Ganancias totales: " + Consola.dinero(finalizarDia.gananciasTotales()));
            System.out.println("Movimientos totales: " + finalizarDia.movimientosTotales());
            System.out.println("==================================");
        } catch(RuntimeException e) {
            consola.error(e.getMessage());
        }

        if (consola.confirmar("¿Desea cerrar el programa?")) {
            System.out.println("Cierre de jornada finalizado.");
            return true;
        }
        System.out.println("Se canceló el cierre.");
        return false;
    }
}
