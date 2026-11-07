package models;


// lo unico que se cambio de producto es que ahora es abtracta para no tener que estra creando un objeto cada vez que se necesite
public abstract class Producto {

    public static final String[] CATEGORIAS = {
            "PROTECCION", "ANTICAIDAS", "HARDWARE_CONEXION", "HERRAMIENTAS"
    };

    private final int id;
    private String nombre;
    private int precio;
    private int cantidad;
    private boolean activo;

    public Producto(int id, String nombre, int precio, int cantidad, boolean activo) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        this.id = id;
        setNombre(nombre);
        setPrecio(precio);
        this.cantidad = cantidad;
        this.activo = activo;
    }

    public static Producto crear(String categoria, int id, String nombre, int precio,
                                 int cantidad, boolean activo) {
        switch (categoria.trim().toUpperCase()) {
            case "PROTECCION":
                return new Proteccion(id, nombre, precio, cantidad, activo);
            case "ANTICAIDAS":
                return new Anticaidas(id, nombre, precio, cantidad, activo);
            case "HARDWARE_CONEXION":
                return new HardwareConexion(id, nombre, precio, cantidad, activo);
            case "HERRAMIENTAS":
                return new Herramientas(id, nombre, precio, cantidad, activo);
            default:
                throw new IllegalArgumentException("Categoría desconocida: " + categoria);
        }
    }

    public abstract String getCategoria();
    public abstract String fichaTecnica();



    public boolean hayStock(int unidades) {

        return activo && unidades > 0 && cantidad >= unidades;
    }

    public void descontar(int unidades) {
        if (unidades <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        if (unidades > cantidad) {
            throw new IllegalStateException("Stock insuficiente de " + nombre
                    + ". Disponibles: " + cantidad + ".");
        }
        cantidad -= unidades;
    }

    public void aumentar(int unidades) {
        if (unidades <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        cantidad += unidades;
    }

    public void retirar() {
        activo = false;
    }



    // ---------- Getters y setters ----------

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public boolean isActivo() { return activo; }

    public final void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public final void setPrecio(int precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        this.precio = precio;
    }
}
