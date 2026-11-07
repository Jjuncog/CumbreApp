package models;

public class HardwareConexion extends Producto {

    public HardwareConexion(int id, String nombre, int precio, int cantidad, boolean activo) {
        super(id, nombre, precio, cantidad, activo);
    }

    @Override
    public String getCategoria() {
        return "HARDWARE_CONEXION";
    }

    @Override
    public String fichaTecnica() {
        return "Categoría: Hardware de Conexión. " +
                "Función principal: Proveer puntos de anclaje confiables " +
                "y articular los distintos componentes de los sistemas de seguridad.";
    }
}
