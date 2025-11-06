// Archivo: src/com/ecogestor/modelo/Agua.java
package com.ecogestor.modelo;

public class Agua extends Recurso {
    private String calidad; 
    private static final double FACTOR_IMPACTO_CO2 = 0.01; 
    
    public Agua(String nombre, double cantidadInicial, String calidad) {
        super(nombre, cantidadInicial);
        this.calidad = calidad;
    }

    @Override
    public void usar(double cantidad) {
        if (cantidad > 0 && cantidadDisponible >= cantidad) {
            cantidadDisponible -= cantidad;
            System.out.println("Agua utilizada: " + cantidad + " (Calidad: " + calidad + ")");
        } else if (cantidadDisponible < cantidad) {
             System.out.println("Advertencia: No hay suficiente " + nombre + " disponible.");
        }
    }

    @Override
    public double calcularImpacto() {
        return cantidadDisponible * FACTOR_IMPACTO_CO2; 
    }
    
    public String getCalidad() { return calidad; }
}