package logica;
import java.time.LocalDate;


public class L04_Prestamos {

    private int id_prestamo;
    private int id_cliente;
    private LocalDate fecha_prestamo;
    private LocalDate fecha_devolucion;
    private String productos;
    private Double valor_prestamo;

    public L04_Prestamos(int id_prestamo, int id_cliente, String productos, double valor_prestamo) {
        this.id_prestamo = id_prestamo;
        this.id_cliente = id_cliente;
        this.fecha_prestamo = LocalDate.now();
        this.fecha_devolucion = fecha_prestamo.plusDays(30); // Se asigna 30 dias para la devolucion, esta sujeto a cambios en el futuro
        this.productos = productos;
        this.valor_prestamo = valor_prestamo;
    }

    // Getters
    public int getId_prestamo() {
        return id_prestamo;
    }

    public int getId_cliente() {
        return id_cliente;
    }
    public LocalDate getFecha_prestamo() {
        return fecha_prestamo;
    }
    public LocalDate getFecha_devolucion() {
        return fecha_devolucion;
    }
    public String getProductos() {
        return productos;
    }
    public Double getValor_prestamo() {
        return valor_prestamo;
    }

    // NO se agregan setters ya que al modificarse una variable del objeto puede afectar la trasabilidad del codigo y tener problemas con una futura base de datos

    public String consultar_prestamo(){
        return "PRESTAMO " +id_prestamo+ "\n" +
                "---------------------------------" +
                "Id del Cliente: "+ id_cliente + "\n" +
                "Fecha del prestamo: " + fecha_prestamo + "\n" +
                "Fecha limite de devolucion: " + fecha_devolucion + "\n" +
                "Producto(s): " + productos + "\n" +
                "---------------------------------";
    }





}

