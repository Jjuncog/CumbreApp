package models;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class M04_Prestamos {

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

    M03_Inventario inventario = new M03_Inventario();

    // Registro de prestamos
    public M04_Prestamos(int id_prestamo, int id_cliente, String nombre_cliente, String contacto_cliente, LocalDate fecha_prestamo, int dias_prestamo, int id_producto, int cantidad_producto){
        this.id_prestamo = id_prestamo;
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


    public void devolucion_prestamo(){
        estado_prestamo = false;
        inventario.aumentar_stock(id_producto, cantidad_producto);
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


    // NO se agregan setters ya que al modificarse una variable del objeto puede afectar la trasabilidad del codigo y tener problemas con una futura base de datos




}