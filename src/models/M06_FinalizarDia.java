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
        long gananciasAcumuladas = ventas.getGananciasAcumuladas();
        int movimientos = ventas.getCantidadMovimientos();
        double gananciasPrestamos = prestamos.get_ganancias_totales_prestamos();
        double contadorPrestamos = prestamos.get_contador_prestamos();

        System.out.println("\n==================================");
        System.out.println("          CIERRE DEL DIA");
        System.out.println("==================================");
        System.out.println("Fecha: " + fecha);
        System.out.println("Ganancias por ventas: $" + gananciasAcumuladas);
        System.out.println("Ventas registradas: " + movimientos);
        System.out.println("Ganancias por préstamos: $" + gananciasPrestamos);
        System.out.println("Préstamos registrados: " + contadorPrestamos);
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
