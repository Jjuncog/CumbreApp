package models;
import java.util.Scanner;
public class M05_Ventas {
   //Atributos de clases anteriores y funciones locales :)
    private Scanner sc;
   private M02_Catalogo catalogo;
   private M03_Inventario inventario;
   private String historial;
   private long gananciasAcumuladas;
   //constructor para crear objeto de la clase
   M05_Ventas(M02_Catalogo catalogo, M03_Inventario inventario, Scanner sc){
      this.catalogo= catalogo;
      this.inventario= inventario;
      this.sc=sc;
      historial = "";
      gananciasAcumuladas = 0L;}
   //metodo de submenu del registro de ventas
public void mostrarMenuVentas(){
   int opcion;
      do{
         System.out.println(
                 "1. Añadir venta nueva del día. "+"\n"+
                 "2. Registrar corrección o devolución.  "+"\n"+
                 "3. Consultar histórial. "+"\n"+
                 "4. Volver al menú principal. "+"\n");
         opcion=sc.nextInt();
         switch (opcion) {
            case 1:
               registrarVenta();
               break;
            case 2:
               registrarCorrecion();
               break;
            case 3:
               consultarHistorial();
               break;
            case 4:
               System.out.println("Sistema de venta finalizado");
               break;
            default:
               System.out.println("opcion invalida");
         }
      }while(opcion != 4);
      }
   //metodo de registrar venta
public void registrarVenta(){

      int idProducto;
      int cantidad;
      int precioUnitario;
      long valorVenta;
      int salida;
      M01_Producto producto;
      do {
          //llamar al producto
          System.out.print("Ingrese el ID del producto: ");
          idProducto = sc.nextInt();
          producto = catalogo.buscar_producto_por_id(idProducto);
          if (producto == null) {
              System.out.println("ID incorrecto, ingrese uno valido");
              return;
          }
          //cantidad y comprobante de stock
          System.out.print("Ingrese la cantidad solicitada: ");
          cantidad = sc.nextInt();
          if (cantidad <= 0) {
              System.out.print("Cantidad invalida");
              return;
          }
          if (inventario.hay_stock(idProducto, cantidad) == false) {
              return;
          }
          precioUnitario = producto.getPrecio();
          valorVenta = (long) precioUnitario * cantidad;
          if (!inventario.descontar_stock(idProducto, cantidad)) {
              return;
          }
          gananciasAcumuladas += valorVenta;
          historial += "VENTA | ID: " + idProducto + " | Producto: " + producto.getNombre() + " | Cantidad: " + cantidad + " | Total: $" + valorVenta + "\n";
          System.out.println("Venta: " + cantidad + " " + producto.getNombre() + " -> $" + valorVenta);
          System.out.print("¿Terminó el registro? 1=Si 0=No -> ");
          salida=sc.nextInt();
          System.out.println("Registro exitoso");
      }while(salida!=1);
   }
private void registrarCorrecion(){
    int idProducto;
    int cantidad;
    int precioUnitario;
    long valorCorrecion;
    int salida;
    M01_Producto producto;
    do {
        System.out.print("Ingrese el ID del producto: ");
        idProducto = sc.nextInt();
        producto = catalogo.buscar_producto_por_id(idProducto);
        if (producto == null) {
            System.out.println("ID incorrecto, ingrese uno valido");
            return;
        }
        System.out.print("Ingrese la cantidad que debe corregirse (si ingresó 5 y eran 3 ingrese 2): ");
        cantidad = sc.nextInt();
        if (cantidad <= 0) {
            System.out.print("Cantidad invalida");
            return;
        }
        precioUnitario= producto.getPrecio();
        valorCorrecion= precioUnitario*cantidad;

        if(!inventario.aumentar_stock(idProducto, cantidad)){
            System.out.println("Operación invalida");
            return;
        }
        gananciasAcumuladas-=valorCorrecion;
        historial += "CORRECCION | ID: " + idProducto + " | Producto: " + producto.getNombre() + " | Cantidad: " + cantidad + " | Valor correcion: $" + valorCorrecion + "\n";
        System.out.println("Correccion: " + cantidad + " " + producto.getNombre() + " -> $" + valorCorrecion);
        System.out.print("¿Terminó el registro? 1=Si 0=No -> ");
        salida=sc.nextInt();
        System.out.println("Registro exitoso");
    }while(salida!=1);
}

private void consultarHistorial(){
    if(historial.isEmpty()){
        System.out.println("No se han registrado ventas");
        return;
    }
    System.out.println(historial);
}
}

