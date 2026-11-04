package models;

public class Proteccion extends Producto {

    public Proteccion(int id, String nombre, int precio) {
        super(id, nombre, precio);
    }

    public String fichaTecnica(){
        return "Categoría: Protección Personal. " +
                "Función principal: Mitigar el riesgo de lesiones " +
                "y salvaguardar la integridad física del operario frente a riesgos laborales generales.";
    }
}
