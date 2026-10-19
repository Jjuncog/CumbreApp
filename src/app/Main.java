package app;
import models.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	M02_Catalogo catalogo = new M02_Catalogo();
	M03_Inventario inventario = new M03_Inventario();
	M04_Prestamos prestamos = new M04_Prestamos(inventario);
	M05_Ventas ventas = new M05_Ventas(catalogo, inventario, sc);
	M06_FinalizarDia finalizar = new M06_FinalizarDia(sc);
	
	System.out.println("Bienvenido a CumbreApp, el sistema de gestión de Cumbre Colombia\n");
	int opcion;
	
	do {
		 System.out.println("=====================================");
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
                break;
            case 2:
                inventario.mostrar_inventario();
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