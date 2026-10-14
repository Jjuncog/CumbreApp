package logica;

public class L02_Catalogo {
private L01_Producto producto1;
private L01_Producto producto2;
private L01_Producto producto3;
private L01_Producto producto4;
private L01_Producto producto5;

public L02_Catalogo(){
producto1=new L01_Producto(1001,"Arnés de seguridad",255000);
producto2=new L01_Producto(1002,"Línea de vida",200000);
producto3=new L01_Producto(1003,"Eslinga con absorbedor de energía",345000);
producto4=new L01_Producto(1004,"Retráctil anticaídas", 370000);
producto5=new L01_Producto(1005,"Mosquetón de seguridad",189000);
  }

public void mostrar_catalogo(){
String catalogo = "CATÁLOGO DE EQUIPO DE ALTURAS DISPONIBLE\n";
catalogo += "ID: " + producto1.getId() + " || Nombre: " + producto1.getNombre() + " || Precio " + producto1.getPrecio() + "\n";
catalogo += "ID: " + producto2.getId() + " || Nombre: " + producto2.getNombre() + " || Precio " + producto2.getPrecio() + "\n";
catalogo += "ID: " + producto3.getId() + " || Nombre: " + producto3.getNombre() + " || Precio " + producto3.getPrecio() + "\n";
catalogo += "ID: " + producto4.getId() + " || Nombre: " + producto4.getNombre() + " || Precio " + producto4.getPrecio() + "\n";
catalogo += "ID: " + producto5.getId() + " || Nombre: " + producto5.getNombre() + " || Precio " + producto5.getPrecio() + "\n";
System.out.println(catalogo);
}
}
