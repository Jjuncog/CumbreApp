package controller;

import java.time.LocalDate;

public class FinalizarDia {

    private final Ventas ventas;
    private final Prestamos prestamos;

    public FinalizarDia(Ventas ventas, Prestamos prestamos) {
        this.ventas = ventas;
        this.prestamos = prestamos;
    }

    public LocalDate getFecha() { return LocalDate.now(); }
    public long gananciasVentas() { return ventas.gananciasDelDia(); }
    public int movimientosVentas() { return ventas.movimientosDelDia(); }
    public double gananciasPrestamos() { return prestamos.gananciasDelDia(); }
    public int prestamosRegistrados(){ return prestamos.prestamosDelDia(); }

    public double gananciasTotales() {
        return gananciasVentas() + gananciasPrestamos();
    }

    public int movimientosTotales() {
        return movimientosVentas() + prestamosRegistrados();
    }
}
