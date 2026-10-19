package models;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class M04_Prestamos {
	
	private Scanner sc;
    private int id_prestamo;
    private int id_cliente;
    private String nombre_cliente;
    private String contacto_cliente;

    private LocalDate fecha_prestamo;
    private LocalDate fecha_devolucion;
    private int id_producto;
    private int cantidad_producto;
    private double valor_prestamo;
    private boolean estado_prestamo;
    private boolean mora_prestamo;
    
    private static int contador_prestamos = 0;
    private static double ganancias_totales_prestamos = 0.0;


    private double valor_total_prestamo(int id_producto, int dias_prestamo){
        M02_Catalogo catalogo = new M02_Catalogo();
        int valor_producto = catalogo.obtener_precio_producto(id_producto);
        if(valor_producto != -1) {
            return valor_producto + ((valor_producto * 0.10) * dias_prestamo);
        }
        else{
            return 0;
        }
        // A futuro se espera agregar una nueva regla de negocio que agrege un costo por mora en la entrega de proctos
    }
    
    public M04_Prestamos() {}

    M03_Inventario inventario = new M03_Inventario();

    // Registro de prestamos
    public M04_Prestamos( int id_cliente, String nombre_cliente, String contacto_cliente, LocalDate fecha_prestamo, int dias_prestamo, int id_producto, int cantidad_producto){
        contador_prestamos++;
    	this.id_prestamo = contador_prestamos;
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.contacto_cliente = contacto_cliente;
        this.fecha_prestamo = fecha_prestamo;
        fecha_devolucion = fecha_prestamo.plusDays(dias_prestamo); // Se asignan 30 dias para la entrega, este valor esa sujeto a combios futuros

        inventario.descontar_stock(id_producto, cantidad_producto);

        this.id_producto = id_producto;
        valor_prestamo = valor_total_prestamo(id_producto, dias_prestamo);

        estado_prestamo = true;
        mora_prestamo = false;

        ganancias_totales_prestamos+= valor_prestamo;
    }


    // estado del prestamo
    private String estado(boolean estado_prestamo){
        if(LocalDate.now().isBefore(fecha_devolucion)){
            if(estado_prestamo == true){
                return "activo";
            }
            else return "devuelto";
        }
        else {
            mora_prestamo = true;
            return "En mora";
        }
    }
    
    public void mostrarMenuPrestamos() {
    	int op;
    	sc = new Scanner(System.in);
    	do {
    		System.out.println("\n====================================");
   	     	System.out.println("          MENÚ DE PRÉSTAMOS");
   	     	System.out.println("====================================");
    		System.out.println(
            				   "Seleccione una opción para continuar\n" +
            				   "\n1. Añadir un préstamo\n" +
            				   "2. Registrar devolución de equipo\n" +
                    		   "3. Volver al menú principal\n ");
    		
    		System.out.println("====================================");
    		
    		op = sc.nextInt();
    		switch(op) {
    		case 1: 
    	        System.out.println("Ingrese el ID del cliente:");
    	        int id_cliente = sc.nextInt();
    	        
    	        System.out.println("Ingrese el nombre del cliente:");
    	        sc.nextLine(); // Limpiar el enter
    	        String nombre_cliente = sc.nextLine();
    	        
    	        System.out.println("Ingrese el contacto (correo) del cliente:");
    	        String contacto_cliente = sc.nextLine();
    	        
    	        System.out.println("Ingrese los días de préstamo:");
    	        int dias_prestamo = sc.nextInt();
    	        
    	        System.out.println("Ingrese la ID del producto:");
    	        int id_producto = sc.nextInt();
    	        
    	        System.out.println("Ingrese la cantidad del producto:");
    	        int cantidad_producto = sc.nextInt();
    	        
    	        M04_Prestamos nuevo = new M04_Prestamos(
    	            id_cliente, nombre_cliente, contacto_cliente, 
    	            LocalDate.now(), dias_prestamo, id_producto, cantidad_producto
    	        );
    	        
    	        System.out.println("¡Préstamo registrado exitosamente!\n");
    	        System.out.println(nuevo.consultar_prestamo());
    	        break; 
    	        
    		case 2:
    	        
    	        System.out.println("Ingrese la ID del producto a devolver:");
    	        int id_prod_dev = sc.nextInt();
    	        
    	        System.out.println("Ingrese la cantidad a devolver:");
    	        int cant_prod = sc.nextInt();
    	        
    	        inventario.aumentar_stock(id_prod_dev, cant_prod);
    	        System.out.println("Devolución exitosa. Se regresó la cantidad al inventario.\n");
    	        estado_prestamo = false;
    	        break;
    			
    		case 3:
    			System.out.println("Regresando al menú...\n");
    			break;
    			
    		default:
    			System.out.println("Opción inválida. Ingrese una nueva opción.\n");
    		} 
    	
    		} while(op!=3);
    }
    		

    public String consultar_prestamo(){
        return  "---------------------------------" + "\n" +
                "PRESTAMO " +id_prestamo+ "\n" +
                "---------------------------------" + "\n" +
                "Id del Cliente: "+ id_cliente + "\n" +
                "Fecha del prestamo: " + fecha_prestamo + "\n" +
                "Fecha limite de devolucion: " + fecha_devolucion + "\n" +
                "Producto(s): " + id_producto + "\n" +
                "estado_prestamo: " + estado(estado_prestamo) + "\n" +
                "---------------------------------" + "\n" +
                "Valor prestamo :" + valor_prestamo + "\n" +
                "---------------------------------";

    }

    // Getters
    public int get_id_prestamo() {
        return id_prestamo;
    }
    public int get_id_cliente() {
        return id_cliente;
    }
    public String get_nombre_cliente() {
        return nombre_cliente;
    }
    public String get_contacto_nombre() {
        return contacto_cliente;
    }
    public LocalDate get_fecha_prestamo() {
        return fecha_prestamo;
    }
    public LocalDate get_fecha_devolucion() {
        return fecha_devolucion;
    }
    public int get_id_producto() {
        return id_producto;
    }
    public double get_valor_prestamo() {
        return valor_prestamo;
    }
    public boolean get_estado_prestamo() {
        return estado_prestamo;
    }
    public boolean get_mora_prestamo() {
        return mora_prestamo;
    }
    public static int get_contador_prestamos() {
        return contador_prestamos;
    }
    public static double get_ganancias_totales_prestamos() {
        return ganancias_totales_prestamos;
    }

    // NO se agregan setters ya que al modificarse una variable del objeto puede afectar la trasabilidad del codigo y tener problemas con una futura base de datos




}
