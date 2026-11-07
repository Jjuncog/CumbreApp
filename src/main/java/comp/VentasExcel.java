package comp;

import models.Venta;

public class VentasExcel {

    private static final String HOJA = "Ventas";
    private static final String[] ENCABEZADOS = {
            "id_venta", "fecha", "id_producto", "cantidad", "valor", "tipo"
    };

    private final ExcelManager excel;

    public VentasExcel(ExcelManager excel) {
        this.excel = excel;
        excel.prepararHoja(HOJA, ENCABEZADOS);
    }

    public int siguienteId() {
        return excel.siguienteId(HOJA);
    }

    public void guardar(Venta venta) {
        excel.agregarFila(HOJA, aFila(venta));
    }

    public Venta[] listarTodas() {
        Object[][] filas = excel.leerFilas(HOJA);
        Venta[] ventas = new Venta[filas.length];
        for (int i = 0; i < filas.length; i++) {
            ventas[i] = aObjeto(filas[i]);
        }
        return ventas;
    }

    private Object[] aFila(Venta venta) {
        return new Object[]{
                venta.getIdVenta(), venta.getFecha(), venta.getIdProducto(), venta.getCantidad(), venta.getValor(), venta.getTipo()
        };
    }

    private Venta aObjeto(Object[] index) {
        return new Venta(
                ExcelManager.aEntero(index[0]),
                ExcelManager.aFecha(index[1]),
                ExcelManager.aEntero(index[2]),
                ExcelManager.aEntero(index[3]),
                (long) ExcelManager.aDecimal(index[4]),
                ExcelManager.aTexto(index[5]).toUpperCase()
        );
    }
}
