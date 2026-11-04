package models;

public class HardwareConexion extends Producto {
    public HardwareConexion(int id, String nombre, int precio) {
        super(id, nombre, precio);
    }

    public String fichaTecnica(){
        return "Categoría: Hardware de Conexión. " +
                "Función principal: Proveer puntos de anclaje confiables " +
                "y articular los distintos componentes de los sistemas de seguridad.";
    }
}

