// Archivo: src/com/ecogestor/modelo/Residuo.java
package com.ecogestor.modelo;

public class Residuo extends Recurso {
    private String tipo; 
    private double materialReciclado; 
    private static final double FACTOR_IMPACTO_NEGATIVO = -0.5; 

    public Residuo(String nombre, double cantidadInicial, String tipo) {
        super(nombre, cantidadInicial);
        this.tipo = tipo;
        this.materialReciclado = 0;
    }

    // Método Específico
    public void reciclar(double cantidad) {
        if (cantidad > 0) {
            this.materialReciclado += cantidad; 
            System.out.println("Material reciclado: +" + cantidad);
        }
    }
    
    @Override
    public void usar(double cantidad) {
        // En este contexto, 'usar' registra la cantidad de residuos generados
        if (cantidad > 0) {
            cantidadDisponible += cantidad; 
            System.out.println("Residuos generados: " + cantidad + " (" + tipo + ")");
        }
    }

    @Override
    public double calcularImpacto() {
        // Devuelve un valor negativo (beneficio) basado en lo reciclado
        return materialReciclado * FACTOR_IMPACTO_NEGATIVO; 
    }
}