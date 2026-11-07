package models;

public class Herramientas extends Producto {

    public Herramientas(int id, String nombre, int precio, int cantidad, boolean activo) {
        super(id, nombre, precio, cantidad, activo);
    }

    @Override
    public String getCategoria() {
        return "HERRAMIENTAS";
    }

    @Override
    public String fichaTecnica() {
        return "Categoría: Herramientas Operativas. " +
                "Función principal: Facilitar la ejecución manual de tareas de instalación, " +
                "ensamble y mantenimiento, optimizando el esfuerzo físico del usuario.";
    }
}
