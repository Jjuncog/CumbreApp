package models;

import java.time.LocalDate;
import java.util.Scanner;

public class M06_FinalizarDia {

    private Scanner sc;

    public M06_FinalizarDia(Scanner sc) {
        this.sc = sc;
    }

    public void finalizar_dia(M05_Ventas ventas, M04_Prestamos prestamos) {

        LocalDate fecha = LocalDate.now();
        long gananciasVentas = ventas.getGananciasAcumuladas();
        int movimientosVentas = ventas.getCantidadMovimientos();
        double gananciasPrestamos = M04_Prestamos.get_ganancias_totales_prestamos();
        int contadorPrestamos = M04_Prestamos.get_contador_prestamos();
        double gananciasTotales = gananciasVentas + gananciasPrestamos;
        int movimientosTotales = movimientosVentas + contadorPrestamos;

        System.out.println("\n==================================");
        System.out.println("          CIERRE DEL DIA");
        System.out.println("==================================");
        System.out.println("Fecha: " + fecha);
        System.out.println("Ganancias por ventas: $" + gananciasVentas);
        System.out.println("Ventas y correcciones registradas: " + movimientosVentas);
        System.out.println("Ganancias por préstamos: $" + gananciasPrestamos);
        System.out.println("Préstamos registrados: " + contadorPrestamos);
        System.out.println("----------------------------------");
        System.out.println("Ganancias totales: $" + gananciasTotales);
        System.out.println("Movimientos totales: " + movimientosTotales);
        System.out.println("==================================");

        System.out.print("¿Desea cerrar el programa? 1=Si 0=No: ");
        int opcion = sc.nextInt();

        if (opcion == 1) {
            System.out.println("Cierre de jornada finalizado.");
            System.exit(0);
        } else {
            System.out.println("Se cancelo el cierre.");
        }
    }
}