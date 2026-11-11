package comp;

import models.Venta;

public class VentasExcel {

    private static final String HOJA = "Ventas";
    private static final String[] ENCABEZADOS = {
            "id_venta", "id_factura", "fecha", "id_producto", "cantidad", "valor", "tipo"
    };

    private final ExcelManager excel;

    public VentasExcel(ExcelManager excel) {
        this.excel = excel;
        excel.prepararHoja(HOJA, ENCABEZADOS);
    }

    public int siguienteId() {
        return excel.siguienteId(HOJA);
    }

    // Calcula el número de la siguiente factura consultando las existentes
    public int siguienteIdFactura() {
        Venta[] todas = listarTodas();
        int maxId = 0;
        for (Venta v : todas) {
            if (v.getIdFactura() > maxId) {
                maxId = v.getIdFactura();
            }
        }
        return maxId + 1;
    }

    // Filtra las ventas guardadas por el idFactura
    public Venta[] buscarPorFactura(int idFactura) {
        Venta[] todas = listarTodas();
        int contador = 0;
        for (Venta v : todas) {
            if (v.getIdFactura() == idFactura) contador++;
        }

        Venta[] resultado = new Venta[contador];
        int idx = 0;
        for (Venta v : todas) {
            if (v.getIdFactura() == idFactura) {
                resultado[idx++] = v;
            }
        }
        return resultado;
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
                venta.getIdVenta(), venta.getIdFactura(), venta.getFecha(), venta.getIdProducto(),
                venta.getCantidad(), venta.getValor(), venta.getTipo()
        };
    }

    private Venta aObjeto(Object[] index) {
        return new Venta(
                ExcelManager.aEntero(index[0]),
                ExcelManager.aEntero(index[1]),
                ExcelManager.aFecha(index[2]),
                ExcelManager.aEntero(index[3]),
                ExcelManager.aEntero(index[4]),
                (long) ExcelManager.aDecimal(index[5]),
                ExcelManager.aTexto(index[6]).toUpperCase()
        );
    }
}
