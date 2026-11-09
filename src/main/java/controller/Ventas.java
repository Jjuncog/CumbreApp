package controller;

import comp.VentasExcel;
import models.Producto;
import models.Venta;
import java.time.LocalDate;

public class Ventas {
    private final Inventario inventario;
    private final VentasExcel registro;

    public Ventas(Inventario inventario, VentasExcel registro) {
        this.inventario = inventario;
        this.registro = registro;
    }

    public Venta registrarVenta(int idProducto, int cantidad) {
        Producto producto = inventario.obtenerProductoDisponible(idProducto);
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a cero");
            return;
        }
        if (!producto.hayStock(cantidad)) {
            System.out.println("No hay stock suficiente");
            return;
        }

        long valor = (long) producto.getPrecio() * cantidad;
        Venta venta = new Venta(registro.siguienteId(), LocalDate.now(), idProducto, cantidad, valor, Venta.TIPO_VENTA);

        registro.guardar(venta);
        inventario.descontarStock(idProducto, cantidad);
        return venta;
    }
    public Venta registrarCorreccion(int idProducto, int cantidad) {
        Producto producto = inventario.buscarProducto(idProducto);
        if (producto == null) {
            throw new IllegalArgumentException("No existe un producto con ID " + idProducto);
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }
        int vendidasHoy = unidadesVendidasHoy(idProducto);
        if (cantidad > vendidasHoy) {
            throw new IllegalArgumentException("Hoy solo se han vendido " + vendidasHoy
                    + " unidades de " + producto.getNombre() + "; no se puede corregir más");
        }

        long valor = (long) producto.getPrecio() * cantidad;
        Venta correccion = new Venta(registro.siguienteId(), LocalDate.now(), idProducto,
                cantidad, valor, Venta.TIPO_CORRECCION);

        registro.guardar(correccion);
        inventario.devolverStock(idProducto, cantidad);
        return correccion;
    }

    public Venta[] historialDelDia() {
        Venta[] todas = registro.listarTodas();

        int deHoy = 0;
        for(int i = 0; i < todas.length; i++) {
            if (todas[i].getFecha().equals(LocalDate.now())) deHoy++;
        }

        Venta[] resultado = new Venta[deHoy];
        int k = 0;
        for (int i = 0; i < todas.length; i++) {
            if (todas[i].getFecha().equals(LocalDate.now())) {
                resultado[k] = todas[i];
                k++;
            }
        }
        return resultado;
    }

    public long gananciasDelDia() {
        Venta[] hoy = historialDelDia();
        long total = 0;
        for (int i = 0; i < hoy.length; i++) {
            total += hoy[i].getValorConSigno();
        }
        return total;
    }
    public int movimientosDelDia() {
        return historialDelDia().length;
    }

    private int unidadesVendidasHoy(int idProducto) {
        Venta[] hoy = historialDelDia();
        int unidades = 0;
        for(int i = 0; i < hoy.length; i++) {
            if (hoy[i].getIdProducto() == idProducto) {
                unidades += hoy[i].getCantidadConSigno();
            }
        }
        return unidades;
    }
}
