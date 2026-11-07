package view;

import java.util.Scanner;



// Esta es una clase "plantilla" para no tener que estar escribiendo lo mismo solo se llama la clase y yap

public class Consola {

    private final Scanner sc;

    public Consola(Scanner sc) {
        this.sc = sc;
    }

    public int leerEntero(String mensaje) {
        if(!mensaje.isEmpty()) {
            System.out.println(mensaje);
        }
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.println("Debe ingresar un numero entero:");
        }
        int valor = sc.nextInt();
        sc.nextLine(); // limpia el enter
        return valor;
    }
    public String leerTexto(String mensaje) {
        System.out.println(mensaje);
        String texto = sc.nextLine().trim();
        while(texto.isEmpty()) {
            System.out.println("Este campo no puede estar vacío:");
            texto = sc.nextLine().trim();
        }
        return texto;
    }
    public boolean confirmar(String mensaje) {
        return leerEntero(mensaje + " 1=Sí, 0=No: ") == 1;
    }

    public void titulo(String texto) {
        System.out.println("\n=====================================");
        System.out.println("  " + texto);
        System.out.println("=====================================");
    }

    public void error(String mensaje) {
        System.out.println("\n[!] " + mensaje);
    }

    public static String dinero(double valor) {
        if(valor < 0) {
            return String.format("-$%,.0f", -valor);
        }
        return String.format("$%,.0f", valor);
    }
}
