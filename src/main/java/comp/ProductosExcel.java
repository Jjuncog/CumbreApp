package comp;

import models.Producto;

public class ProductosExcel {

    private static final String HOJA = "Productos";
    private static final String[] ENCABEZADOS = {
            "id_producto", "categoria", "nombre", "precio", "cantidad", "activo"
    };
    private static final int PRIMER_ID = 1001;

    private final ExcelManager excel;

    public ProductosExcel(ExcelManager excel) {
        this.excel = excel;
        excel.prepararHoja(HOJA, ENCABEZADOS);
    }

    public int siguienteId() {

        return Math.max(excel.siguienteId(HOJA), PRIMER_ID);
    }

    public void guardarProducto(Producto producto) {

        excel.agregarFila(HOJA, aFila(producto));
    }

    public void actualizarProducto(Producto producto) {
        if (!excel.actualizarFila(HOJA, producto.getId(), aFila(producto))) {
            throw new IllegalArgumentException("No existe el producto " + producto.getId() + ".");
        }
    }

    public Producto[] listarTodosProductos() {
        Object[][] filas = excel.leerFilas(HOJA);
        Producto[] productos = new Producto[filas.length];
        for (int i = 0; i < filas.length; i++) {
            productos[i] = aObjeto(filas[i]);
        }
        return productos;
    }

    public Producto buscarProductoPorId(int id) {
        Producto[] productos = listarTodosProductos();
        for (int i = 0; i < productos.length; i++) {
            if (productos[i].getId() == id) {
                return productos[i];
            }
        }
        return null;
    }

    private Object[] aFila(Producto p) {
        return new Object[]{
                p.getId(), p.getCategoria(), p.getNombre(), p.getPrecio(), p.getCantidad(), p.isActivo()
        };
    }

    private Producto aObjeto(Object[] index) {
        return Producto.crear(
                ExcelManager.aTexto(index[1]),
                ExcelManager.aEntero(index[0]),
                ExcelManager.aTexto(index[2]),
                ExcelManager.aEntero(index[3]),
                ExcelManager.aEntero(index[4]),
                ExcelManager.aBooleano(index[5])
        );
    }
}
