// Archivo: src/com/ecogestor/modelo/Usuario.java
package com.ecogestor.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private final String nombre; 
    private final List<Recurso> listaRecursos; 
    private int puntuacion; 
    private final List<String> historial = new ArrayList<>();

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.listaRecursos = new ArrayList<>();
        this.puntuacion = 0;
    }

    public void agregarRecurso(Recurso r) {
        this.listaRecursos.add(r);
    }

    public List<Recurso> getRecursos() {
        return listaRecursos;
    }

    @Override
    public String toString() {
        return nombre;
    }

    // Registrar uso o reciclaje
    public void registrarConsumo(String nombreRecurso, double cantidad) {

        for (Recurso r : listaRecursos) {
            if (r.getNombre().equalsIgnoreCase(nombreRecurso)) {

              if (r instanceof Residuo res) {
                    res.reciclar(cantidad);
                } else {
                    r.usar(cantidad);
                }

                agregarAccionAlHistorial("Recurso: " + nombreRecurso + " | Cantidad: " + cantidad);
                return;
            }
        }

        // Si no se encuentra el recurso, se registra una sola vez
        agregarAccionAlHistorial("Recurso NO encontrado: " + nombreRecurso);
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

    public void agregarAccionAlHistorial(String accion) {
        historial.add(accion);
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getPuntuacion() { return puntuacion; }
    public List<Recurso> getListaRecursos() { return listaRecursos; }
    public List<String> getHistorial() { return historial; }
}
