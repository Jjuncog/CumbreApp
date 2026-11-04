package models;

public class Anticaidas extends Producto {

    public Anticaidas(int id, String nombre, int precio) {
        super(id, nombre, precio);
    }
    public String fichaTecnica() {
        return "Categoría: Equipo Anticaídas." +
                " Función principal: Prevenir descensos bruscos, absorber impactos" +
                " y garantizar soporte continuo durante trabajos exhaustivos en alturas.";
    }
}
