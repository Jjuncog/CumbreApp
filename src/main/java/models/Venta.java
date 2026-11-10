package models;

import java.time.LocalDate;


public class Venta {

    public static final String TIPO_VENTA = "VENTA";
    public static final String TIPO_CORRECCION = "CORRECCION";

    private final int idVenta;
    private final LocalDate fecha;
    private final int idProducto;
    private final int idFactura;
    private final int cantidad;
    private final long valor;
    private final String tipo;

    public Venta(int idVenta, int idFactura, LocalDate fecha, int idProducto, int cantidad, long valor, String tipo) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        if (!TIPO_VENTA.equals(tipo) && !TIPO_CORRECCION.equals(tipo)) {
            throw new IllegalArgumentException("Tipo de movimiento inválido: " + tipo);
        }
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.idFactura= idFactura;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.valor = valor;
        this.tipo = tipo;
    }

    public boolean esCorreccion() {
        return TIPO_CORRECCION.equals(tipo);
    }
    public long getValorConSigno() {
        return esCorreccion() ? -valor : valor;

    }
    public int getCantidadConSigno() {
        return esCorreccion() ? -cantidad : cantidad;

    }

    public int getIdVenta() { return idVenta; }
    public LocalDate getFecha() { return fecha; }
    public int getIdFactura() { return idFactura;}
    public int getIdProducto() { return idProducto; }
    public int getCantidad() { return cantidad; }
    public long getValor() { return valor; }
    public String getTipo() { return tipo; }
}
