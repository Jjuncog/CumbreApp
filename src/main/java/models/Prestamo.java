package models;

import java.time.LocalDate;

public class Prestamo {

    public static final double COMISION_PRESTAMO = 30000; // Se agrego una comision por prestamo por peticion del cliente, la anterior estaba mal planteada
    public static final double PORCENTAJE_DIARIO = 0.10; // Aparte se va añadir un costo por dia de prestado

    private final int idPrestamo;

    private final int idCliente;
    private final String nombreCliente;
    private final String contactoCliente;

    private final LocalDate fechaPrestamo;
    private final LocalDate fechaDevolucion;
    private final int idProducto;
    private final int cantidad;
    private final double valor;
    private boolean devuelto;

    public Prestamo(int idPrestamo, int idCliente, String nombreCliente, String contactoCliente, LocalDate fechaPrestamo, LocalDate fechaDevolucion, int idProducto, int cantidad, double valor, boolean devuelto) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        if (fechaPrestamo == null || fechaDevolucion == null) {
            throw new IllegalArgumentException("Las fechas del préstamo son obligatorias");
        }
        if (fechaDevolucion.isBefore(fechaPrestamo)) {
            throw new IllegalArgumentException("La fecha de devolución es anterior a la del préstamo");
        }
        this.idPrestamo = idPrestamo;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.contactoCliente = contactoCliente;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.valor = valor;
        this.devuelto = devuelto;
    }

    public static double calcularValor(int precioProducto, int dias, int cantidad) {
        return (COMISION_PRESTAMO + precioProducto * PORCENTAJE_DIARIO * dias) * cantidad;
    }

    public void registrarDevolucion() {
        if (devuelto) {
            throw new IllegalStateException("El préstamo " + idPrestamo + " ya había sido devuelto");
        }
        devuelto = true;
    }



    public boolean estaEnMora() {
        return !devuelto && LocalDate.now().isAfter(fechaDevolucion);
    }

    public String getEstado() {
        if (devuelto) return "Devuelto";
        if (estaEnMora()) return "En mora";
        return "Activo";
    }



    public int getIdPrestamo() { return idPrestamo; }
    public int getIdCliente() { return idCliente; }
    public String getNombreCliente() { return nombreCliente; }
    public String getContactoCliente() { return contactoCliente; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public int getIdProducto() { return idProducto; }
    public int getCantidad() { return cantidad; }
    public double getValor() { return valor; }
    public boolean isDevuelto() { return devuelto; }
}
