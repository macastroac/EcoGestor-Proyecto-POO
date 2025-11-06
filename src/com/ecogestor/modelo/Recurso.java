// Archivo: src/com/ecogestor/modelo/Recurso.java
package com.ecogestor.modelo;

public abstract class Recurso {
    protected String nombre;
    protected double cantidadDisponible; 

    public Recurso(String nombre, double cantidadInicial) {
        this.nombre = nombre;
        this.cantidadDisponible = cantidadInicial;
    }

    // Método implementado
    public void recargar(double cantidad) {
        if (cantidad > 0) {
            this.cantidadDisponible += cantidad;
            System.out.println(nombre + " recargado: +" + cantidad);
        }
    }

    // Métodos Abstractos (Polimorfismo)
    public abstract void usar(double cantidad); 
    public abstract double calcularImpacto(); 

    // Getters y Setters
    public String getNombre() { return nombre; }
    public double getCantidadDisponible() { return cantidadDisponible; }
}