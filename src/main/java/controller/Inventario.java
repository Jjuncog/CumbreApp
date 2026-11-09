package controller;

import comp.ProductosExcel;
import models.Producto;

public class Inventario {

    // Al final se unio inventario y catalogo ya que en logica estarian haciendo la misma labor pero con pasos extra

    private final ProductosExcel registro;

    public Inventario(ProductosExcel registro) {
        this.registro = registro;
    }

    public Producto[] listarProductos() {
        Producto[] todos = registro.listarTodosProductos();

        int activos = 0;
        for(int i = 0; i < todos.length; i++) {
            if(todos[i].isActivo()) activos++;
        }

        Producto[] resultado = new Producto[activos];
        int k = 0;
        for(int i = 0; i < todos.length; i++) {
            if(todos[i].isActivo()) {
                resultado[k] = todos[i];
                k++;
            }
        }
        return resultado;
    }

    public Producto buscarProducto(int id) {

        return registro.buscarProductoPorId(id);
    }

    public Producto obtenerProductoDisponible(int id) {
        Producto producto = registro.buscarProductoPorId(id);
        if(producto == null) {
            throw new IllegalArgumentException("No existe un producto con ID "+id);
        }
        if(!producto.isActivo()) {
            throw new IllegalArgumentException("El producto " +id +" fue retirado del catálogo");
        }
        return producto;
    }

    public int consultarStock(int id) {
        Producto producto = registro.buscarProductoPorId(id);
        return (producto == null) ? -1 : producto.getCantidad();
    }

    public boolean hayStock(int id, int cantidad) {
        Producto producto = registro.buscarProductoPorId(id);
        return producto != null && producto.hayStock(cantidad);
    }

    public String nombreProducto(int id) {
        Producto producto = registro.buscarProductoPorId(id);
        return (producto != null) ? producto.getNombre() : "Producto desconocido";
    }

    public Producto agregarProducto(String categoria, String nombre, int precio, int cantidad) {
        Producto nuevo = Producto.crear(categoria, registro.siguienteId(), nombre, precio, cantidad, true);
        registro.guardarProducto(nuevo);
        return nuevo;
    }

    public Producto actualizarPrecio(int id, int nuevoPrecio) {
        Producto producto = obtenerProductoDisponible(id);
        producto.setPrecio(nuevoPrecio);
        registro.actualizarProducto(producto);
        return producto;
    }

    public Producto agregarUnidades(int id, int cantidad) {
        Producto producto = obtenerProductoDisponible(id);
        producto.aumentar(cantidad);
        registro.actualizarProducto(producto);
        return producto;
    }

    public Producto retirarProducto(int id) {
        Producto producto = obtenerProductoDisponible(id);
        producto.retirar();
        registro.actualizarProducto(producto);
        return producto;
    }

    public void descontarStock(int id, int cantidad) {
        Producto producto = obtenerProductoDisponible(id);
        producto.descontar(cantidad);
        registro.actualizarProducto(producto);
    }

    public void devolverStock(int id, int cantidad) {
        Producto producto = registro.buscarProductoPorId(id);
        if(producto == null) {
        throw new IllegalArgumentException("No existe un producto con ID " + id);
        }
        producto.aumentar(cantidad);
        registro.actualizarProducto(producto);
    }
}
