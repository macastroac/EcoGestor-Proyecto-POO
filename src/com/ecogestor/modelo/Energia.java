// Archivo: src/com/ecogestor/modelo/Energia.java
package com.ecogestor.modelo;

public class Energia extends Recurso {
    private String tipo; 
    private static final double IMPACTO_CO2_CARBON = 0.8; 
    private static final double IMPACTO_CO2_SOLAR = 0.05; 

    public Energia(String nombre, double cantidadInicial, String tipo) {
        super(nombre, cantidadInicial);
        this.tipo = tipo;
    }

    @Override
    public void usar(double cantidad) {
        if (cantidad > 0 && cantidadDisponible >= cantidad) {
            cantidadDisponible -= cantidad;
            System.out.println("Energía utilizada: " + cantidad + " (" + tipo + ")");
        } else if (cantidadDisponible < cantidad) {
             System.out.println("Advertencia: No hay suficiente " + nombre + " disponible.");
        }
    }

    @Override
    public double calcularImpacto() {
        double factor;
        if (tipo.equalsIgnoreCase("Carbón") || tipo.equalsIgnoreCase("Fósil")) {
            factor = IMPACTO_CO2_CARBON; 
        } else if (tipo.equalsIgnoreCase("Solar") || tipo.equalsIgnoreCase("Eólica")) {
            factor = IMPACTO_CO2_SOLAR;
        } else {
            factor = 0.4; 
        }
        return cantidadDisponible * factor; 
    }
}