package app;

import comp.ExcelManager;
import comp.PrestamosExcel;
import comp.ProductosExcel;
import comp.VentasExcel;
import controller.FinalizarDia;
import controller.Inventario;
import controller.Prestamos;
import controller.Ventas;
import view.*;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {

            // Esto se cambio para que main solo sea una arrancador con el fin de mejorar la escalabilidad de la APP para entrega 3

            ExcelManager excel = new ExcelManager(Path.of("Data", "cumbreApp.xlsx")); // NO CAMBIAR ES EL PATH DEL EXCEL


            // con la integracion de una "DB" pues ya podemos hacer un crud(create, remove, update, delate) completo
            ProductosExcel productosExcel = new ProductosExcel(excel);
            PrestamosExcel prestamosExcel = new PrestamosExcel(excel);
            VentasExcel ventasExcel = new VentasExcel(excel);

            Inventario inventario = new Inventario(productosExcel);
            Prestamos prestamos = new Prestamos(inventario, prestamosExcel);
            Ventas ventas = new Ventas(inventario, ventasExcel);
            FinalizarDia finalizarDia = new FinalizarDia(ventas, prestamos);

            Consola consola = new Consola(sc);
            MenuPrincipal menu = new MenuPrincipal(consola,
                    new MenuInventario(inventario, consola),
                    new MenuPrestamos(prestamos, inventario, consola),
                    new MenuVentas(ventas, inventario, consola),
                    new MenuFinalizarDia(finalizarDia, consola));

            menu.mostrar();

        } catch (RuntimeException e) {
            System.out.println("CumbreApp se detuvo por un error D: : " + e.getMessage());
        }

        sc.close();
    }
}
