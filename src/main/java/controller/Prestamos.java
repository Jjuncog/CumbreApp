package controller;

import comp.PrestamosExcel;
import models.Prestamo;
import models.Producto;
import java.time.LocalDate;

public class Prestamos {

    private final Inventario inventario;
    private final PrestamosExcel registro;

    public Prestamos(Inventario inventario, PrestamosExcel registro) {
        this.inventario = inventario;
        this.registro = registro;
    }
    
public int generarNuevoIdFactura() {
        return registro.siguienteIdFactura();
    }
    
    public Prestamo crearPrestamo(int idFactura, int idCliente, String nombreCliente, String contactoCliente, int dias, int idProducto, int cantidad){
        Producto producto = inventario.obtenerProductoDisponible(idProducto);
        if(dias <= 0) {
        throw new IllegalArgumentException ("Los días de préstamo deben ser mayores a cero");
        }
        if(cantidad <= 0) {
            throw new IllegalArgumentException ("La cantidad debe ser mayor a cero");
        }
        if(!producto.hayStock(cantidad)) {
            throw new IllegalArgumentException ("No hay stock suficiente ");
        }

        LocalDate hoy = LocalDate.now();
        double valor = Prestamo.calcularValor(producto.getPrecio(), dias, cantidad);
        Prestamo prestamo = new Prestamo(registro.siguienteId(), idFactura, idCliente, nombreCliente, contactoCliente, hoy, hoy.plusDays(dias), idProducto, cantidad, valor, false);

        registro.guardarPrestamos(prestamo);
        inventario.descontarStock(idProducto, cantidad); // Solo se va a descontar el producto del estoc si el prestamo se pudo cargar bien
        return prestamo;
    }

    public Prestamo[] obtenerPrestamosPorFactura(int idFactura) {
        return registro.buscarPorFactura(idFactura);
    }

    public Prestamo registrarDevolucion(int idPrestamo) {
        Prestamo prestamo = registro.buscarPorId(idPrestamo);
        if(prestamo == null) {
            throw new IllegalArgumentException ("No existe el préstamo " + idPrestamo);
        }
        prestamo.registrarDevolucion();
        registro.actualizarPrestamos(prestamo);
        inventario.devolverStock(prestamo.getIdProducto(), prestamo.getCantidad());
        return prestamo;
    }

    public Prestamo buscarPrestamo(int idPrestamo) {

        return registro.buscarPorId(idPrestamo);
    }

    public Prestamo[] prestamosPendientes() {
        Prestamo[] todos = registro.listarTodosPrestamos();

        int pendientes = 0;
        for(int i = 0; i < todos.length;i++) {
            if(!todos[i].isDevuelto()) pendientes++;
        }

        Prestamo[] resultado = new Prestamo[pendientes];
        int j = 0;
        for(int i = 0; i < todos.length; i++) {
            if(!todos[i].isDevuelto()) {
                resultado[j] = todos[i];
                j++;
            }
        }
        return resultado;
    }

    public double gananciasDelDia() {
        Prestamo[] todos = registro.listarTodosPrestamos();
        double total = 0;
        for(int i = 0; i < todos.length; i++) {
            if(todos[i].getFechaPrestamo().equals(LocalDate.now())) {
                total += todos[i].getValor();
            }
        }
        return total;
    }
    public int prestamosDelDia() {
        Prestamo[] todos = registro.listarTodosPrestamos();
        int contador = 0;
        for(int i = 0; i< todos.length; i++) {
            if(todos[i].getFechaPrestamo().equals(LocalDate.now())) {
                contador++;
            }
        }
        return contador;
    }
}
