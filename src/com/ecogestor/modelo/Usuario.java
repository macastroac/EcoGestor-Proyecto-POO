// Archivo: src/com/ecogestor/modelo/Usuario.java
package com.ecogestor.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre; 
    private List<Recurso> listaRecursos; 
    private int puntuacion; 

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.listaRecursos = new ArrayList<>();
        this.puntuacion = 0;
    }

    public void agregarRecurso(Recurso r) {
        this.listaRecursos.add(r);
    }
    
    // Método clave para la UI
    public void registrarConsumo(String nombreRecurso, double cantidad) {
        for (Recurso r : listaRecursos) {
            if (r.getNombre().equalsIgnoreCase(nombreRecurso)) {
                
                // NOTA: Si es Residuo, queremos llamar a reciclar(). 
                // Por simplicidad en la UI, si el recurso es Residuo, lo tratamos como reciclaje.
                if (r instanceof Residuo) {
                    ((Residuo)r).reciclar(cantidad);
                } else {
                     r.usar(cantidad);
                }
                
                return;
            }
        }
    }

    public double calcularEmisionesCO2Total() {
        double impactoTotal = 0;
        for (Recurso r : listaRecursos) {
            impactoTotal += r.calcularImpacto();
        }
        return impactoTotal;
    }

    public void sumarPuntos(int puntos) {
        this.puntuacion += puntos;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getPuntuacion() { return puntuacion; }
    public List<Recurso> getListaRecursos() { return listaRecursos; }
}