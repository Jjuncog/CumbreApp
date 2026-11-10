package comp;

import models.Prestamo;

public class PrestamosExcel {

    private static final String HOJA = "Prestamos";
    private static final String[] ENCABEZADOS = {
            "id_prestamo", "id_factura", "id_cliente", "nombre_cliente", "contacto_cliente",
            "fecha_prestamo", "fecha_devolucion", "id_producto", "cantidad_producto",
            "valor_prestamo", "estado_prestamo"
    };

    private final ExcelManager excel;

    public PrestamosExcel(ExcelManager excel) {
        this.excel = excel;
        excel.prepararHoja(HOJA, ENCABEZADOS);
    }

    public int siguienteId() {
        return excel.siguienteId(HOJA);
    }

public int siguienteIdFactura() {
        Prestamo[] todos = listarTodosPrestamos();
        int maxId = 0;
        for (Prestamo p : todos) {
            if (p.getIdFactura() > maxId) {
                maxId = p.getIdFactura();
            }
        }
        return maxId + 1;
    }

    public Prestamo[] buscarPorFactura(int idFactura) {
        Prestamo[] todos = listarTodosPrestamos();
        int contador = 0;
        for (Prestamo p : todos) {
            if (p.getIdFactura() == idFactura) contador++;
        }

        Prestamo[] resultado = new Prestamo[contador];
        int idx = 0;
        for (Prestamo p : todos) {
            if (p.getIdFactura() == idFactura) {
                resultado[idx++] = p;
            }
        }
        return resultado;
    }
    
    public void guardarPrestamos(Prestamo prestamo) {
        excel.agregarFila(HOJA, aFila(prestamo));
    }

    public void actualizarPrestamos (Prestamo prestamo) {
        if (!excel.actualizarFila(HOJA, prestamo.getIdPrestamo(), aFila(prestamo))) {
            throw new IllegalArgumentException("No existe el prestamo " + prestamo.getIdPrestamo() + ".");
        }
    }

    public Prestamo[] listarTodosPrestamos(){
        Object[][] filas = excel.leerFilas(HOJA);
        Prestamo[] prestamos = new Prestamo[filas.length];
        for (int i = 0; i < filas.length; i++) {
            prestamos[i] = aObjeto(filas[i]);
        }
        return prestamos;
    }

    public Prestamo buscarPorId(int idPrestamo) {
        Prestamo[] prestamos = listarTodosPrestamos();
        for(int i = 0; i < prestamos.length; i++) {
            if (prestamos[i].getIdPrestamo() == idPrestamo) {
                return prestamos[i];
            }
        }
        return null;
    }
    private Object[] aFila(Prestamo prestamo) {
        return new Object[]{
                prestamo.getIdPrestamo(), prestam.gestIdFactura(), prestamo.getIdCliente(),prestamo.getNombreCliente(), prestamo.getContactoCliente(),
                prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion(), prestamo.getIdProducto(), prestamo.getCantidad(),
                prestamo.getValor(), prestamo.isDevuelto() ? "DEVUELTO" : "ACTIVO"
        };
    }

    private Prestamo aObjeto(Object[] index) {
        return new Prestamo(
                ExcelManager.aEntero(index[0]),
                ExcelManager.aEntero(index[1]),
                ExcelManager.aTexto(index[2]),
                ExcelManager.aTexto(index[3]),
                ExcelManager.aFecha(index[4]),
                ExcelManager.aFecha(index[5]),
                ExcelManager.aEntero(index[6]),
                ExcelManager.aEntero(index[7]),
                ExcelManager.aDecimal(index[8]),
                ExcelManager.aTexto(index[9]).equalsIgnoreCase("DEVUELTO")
        );
    }
}
