package comp;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
public class ExcelManager {

    private static final int FILA_ENCABEZADO = 0;
    private static final int COL_ID = 0;
    private static final String FORMATO_FECHA = "dd/mm/yyyy";
    private final Path ruta;

    /*
        NOTA :D:
        La clase ExcelManager es un intento de base de datos que se espera mejorar a futuro implmentando una DB en si.

        Esta clase y ciertos metodos de esta clase fueron revisados y mejorados a nivel de logica con el apoyo de
        claude modelo opus 5.5. en especial para la culminacion de la clase ExcelManager.
    */




    public ExcelManager(Path ruta) {
        this.ruta = ruta; // path == ruta
        if (!Files.exists(ruta)) {
            crearArchivo();
        }
    }

    public void prepararHoja(String nombreHoja, String[] encabezados) {
        try (Workbook libro = cargarLibro()) {


            if(libro.getSheet(nombreHoja) != null) return; // se hace para saber si ya existe una hoja con el mismo nombre


            Sheet hoja = libro.createSheet(nombreHoja);
            Row fila =hoja.createRow(FILA_ENCABEZADO);

            for(int i = 0; i<encabezados.length; i++) {
                Cell celda = fila.createCell(i);
                celda.setCellValue(encabezados[i]);

                hoja.setColumnWidth(i, 20 * 256);
            }

            guardarLibro(libro);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Object[][] leerFilas(String nombreHoja) {
        try(Workbook libro = cargarLibro()) {
            Sheet hoja = obtenerHoja(libro, nombreHoja);
            int columnas = contarColumnas(hoja);

            int total = 0;
            for(int i = FILA_ENCABEZADO + 1; i <= hoja.getLastRowNum(); i++) {
                if(!filaVacia(hoja.getRow(i))) total++;

            }


            Object[][] filas = new Object[total][columnas];
            int k = 0;
            for(int i = FILA_ENCABEZADO + 1; i <= hoja.getLastRowNum(); i++) {
                Row fila = hoja.getRow(i);
                if (filaVacia(fila)) {
                    continue;
                }
                for (int col = 0; col < columnas; col++) {
                    filas[k][col] = leerValor(fila.getCell(col));
                }
                k++;
            }
            return filas;

        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void agregarFila(String nombreHoja, Object[] valores) {
        try(Workbook libro = cargarLibro()) {
            Sheet hoja = obtenerHoja(libro, nombreHoja);
            int numFila = primeraFilaVacia(hoja);
            Row fila = hoja.getRow(numFila);
            if (fila == null) {
                fila = hoja.createRow(numFila);
            }
            escribirFila(libro, fila, valores);
            guardarLibro(libro);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean actualizarFila(String nombreHoja, int id, Object[] valores) {
        try (Workbook libro = cargarLibro()) {
            Sheet hoja = obtenerHoja(libro, nombreHoja);
            Row fila = buscarFilaPorId(hoja, id);
            if (fila == null) {
                return false;
            }
            escribirFila(libro, fila, valores);
            guardarLibro(libro);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /** Siguiente ID disponible: el mayor ID de la hoja + 1. */
    public int siguienteId(String nombreHoja) {
        Object[][] filas = leerFilas(nombreHoja);
        int maximo = 0;
        for (int i = 0; i < filas.length; i++) {
            maximo = Math.max(maximo, aEntero(filas[i][COL_ID]));
        }
        return maximo + 1;
    }




    public static int aEntero(Object valor) {
        if (valor == null) return 0;
        if (valor instanceof Number) return ((Number) valor).intValue();
        return Integer.parseInt(valor.toString().trim());
    }

    public static double aDecimal(Object valor) {
        if (valor == null) return 0;
        if (valor instanceof Number) return ((Number) valor).doubleValue();
        return Double.parseDouble(valor.toString().trim());
    }

    public static boolean aBooleano(Object valor) {
        if (valor instanceof Boolean) return (Boolean) valor;
        String texto = aTexto(valor);
        return texto.equalsIgnoreCase("true") || texto.equalsIgnoreCase("si")
                || texto.equalsIgnoreCase("verdadero");
    }

    public static String aTexto(Object valor) {
        if (valor == null) return "";
        if (valor instanceof Double && (Double) valor % 1 == 0) {
            return String.valueOf(((Double) valor).longValue());
        }
        return valor.toString().trim();
    }

    public static LocalDate aFecha(Object valor) {
        if (valor instanceof LocalDate) return (LocalDate) valor;
        if (valor == null) return null;
        return LocalDate.parse(valor.toString().trim());
    }



    private Workbook cargarLibro() throws IOException {
        try (InputStream entrada = Files.newInputStream(ruta)) {
            return WorkbookFactory.create(entrada); // lee todo y libera el archivo
        }
    }

    private void guardarLibro(Workbook libro) throws IOException {
        try (OutputStream salida = Files.newOutputStream(ruta)) {
            libro.write(salida);
        }
    }

    private void crearArchivo() {
        try (Workbook libro = new XSSFWorkbook()) {
            if (ruta.getParent() != null) {
                Files.createDirectories(ruta.getParent());
            }
            guardarLibro(libro);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Sheet obtenerHoja(Workbook libro, String nombreHoja) {
        Sheet hoja = libro.getSheet(nombreHoja);
        if (hoja == null) {
            throw new IllegalStateException("El archivo no tiene la hoja: " + nombreHoja );
        }
        return hoja;
    }

    private int contarColumnas(Sheet hoja) {
        Row encabezado = hoja.getRow(FILA_ENCABEZADO);
        return (encabezado == null) ? 0 : encabezado.getLastCellNum();
    }


    // metodo solo para saber si una fila esta vacia
    private boolean filaVacia(Row fila) {
        if (fila == null) return true;
        Cell id = fila.getCell(COL_ID);
        return id == null || id.getCellType() == CellType.BLANK;
    }


    // va a retornar el encabezado de la fila vacia
    private int primeraFilaVacia(Sheet hoja) {
        int i = FILA_ENCABEZADO + 1;
        while (!filaVacia(hoja.getRow(i))) {
            i++;
        }
        return i;
    }

    private Row buscarFilaPorId(Sheet hoja, int id) {
        for (int i = FILA_ENCABEZADO + 1; i <= hoja.getLastRowNum(); i++) {
            Row fila = hoja.getRow(i);
            if (!filaVacia(fila) && aEntero(leerValor(fila.getCell(COL_ID))) == id) return fila;
        }
        return null;
    }

    private Object leerValor(Cell celda) {
        if (celda == null) return null;
        switch (celda.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(celda)) {
                    return celda.getLocalDateTimeCellValue().toLocalDate();
                }
                return celda.getNumericCellValue();
            case STRING:
                return celda.getStringCellValue();
            case BOOLEAN:
                return celda.getBooleanCellValue();
            default:
                return null;
        }
    }

    private void escribirFila(Workbook libro, Row fila, Object[] valores) {
        CellStyle estiloFecha = obtenerEstiloFecha(libro);
        for (int col = 0; col < valores.length; col++) {
            Cell celda = fila.getCell(col);
            if (celda == null) {
                celda = fila.createCell(col);
            }
            Object valor = valores[col];

            if (valor == null) celda.setBlank();
            else if(valor instanceof Number) {
                celda.setCellValue(((Number) valor).doubleValue());
            }
            else if(valor instanceof Boolean) {
                celda.setCellValue((Boolean) valor);
            }
            else if (valor instanceof LocalDate) {
                celda.setCellValue((LocalDate) valor);
                celda.setCellStyle(estiloFecha);
            }
            else celda.setCellValue(valor.toString());

        }
    }

    private CellStyle obtenerEstiloFecha(Workbook libro) {
        for (int i = 0; i < libro.getNumCellStyles(); i++) {
            CellStyle estilo = libro.getCellStyleAt(i);
            if (FORMATO_FECHA.equals(estilo.getDataFormatString())) {
                return estilo;
            }
        }
        CellStyle nuevo = libro.createCellStyle();
        nuevo.setDataFormat(libro.createDataFormat().getFormat(FORMATO_FECHA));
        return nuevo;
    }

}
