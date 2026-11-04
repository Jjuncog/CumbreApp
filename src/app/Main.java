package app;
import controller.*;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	Catalogo catalogo = new Catalogo();
	Inventario inventario = new Inventario();
	Prestamos prestamos = new Prestamos(inventario);
	Ventas ventas = new Ventas(catalogo, inventario, sc);
	FinalizarDia finalizar = new FinalizarDia(sc);
	
	System.out.println("Bienvenido a CumbreApp, el sistema de gestión de Cumbre Colombia");
	int opcion;
	
	do {
		 System.out.println("\n=====================================");
	     System.out.println("           MENÚ PRINCIPAL");
	     System.out.println("=====================================");
	     System.out.println(
	    		 			"Seleccione una opción para continuar:\n" +
	    		 			"\n1. Consultar el catálogo disponible.\n" +
	    		 			"2. Consultar inventario de productos.\n" +
	    		 			"3. Desplegar menú de préstamos.\n" +
	    		 			"4. Desplegar menú de ventas.\n" +
	    		 			"5. Finalizar la jornada.\n");
        
         System.out.println("=====================================");

       opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                catalogo.mostrar_catalogo();
                System.out.print("\n¿Desea volver al menú principal? 1=Sí, 0=No (Finalizar jornada): ");
                if (sc.nextInt() == 0) finalizar.finalizar_dia(ventas, prestamos);
                break;
            case 2:
                inventario.mostrar_inventario();
                System.out.print("\n¿Desea volver al menú principal? 1=Sí, 0=No (Finalizar jornada): ");
                if (sc.nextInt() == 0) finalizar.finalizar_dia(ventas, prestamos);
                break;
            case 3:
               	prestamos.mostrarMenuPrestamos();		
                break;
            case 4:
                ventas.mostrarMenuVentas();
                break;
            case 5:
            	finalizar.finalizar_dia(ventas, prestamos);
                break;
            default:
            	System.out.println("Opción inválida, ingrese de nuevo");
                
        }
    } while (opcion != 5);
	
	sc.close();
 }

}