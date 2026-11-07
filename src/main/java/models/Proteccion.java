package models;

public class Proteccion extends Producto {

    public Proteccion(int id, String nombre, int precio, int cantidad, boolean activo) {
        super(id, nombre, precio, cantidad, activo);
    }

    @Override
    public String getCategoria() {
        return "PROTECCION";
    }

    @Override
    public String fichaTecnica() {
        return "Categoría: Protección Personal. " +
                "Función principal: Mitigar el riesgo de lesiones " +
                "y salvaguardar la integridad física del operario frente a riesgos laborales generales.";
    }
}
