// Archivo: src/com/ecogestor/control/EcoGestor.java
package com.ecogestor.control;

import com.ecogestor.modelo.Usuario;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EcoGestor {
    private final List<Usuario> listaUsuarios; 

    public EcoGestor() {
        this.listaUsuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario u) {
        this.listaUsuarios.add(u);
    }
    
    public Usuario getUsuarioPorNombre(String nombre) {
        for (Usuario u : listaUsuarios) {
            if (u.getNombre().equalsIgnoreCase(nombre)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        return listaUsuarios;
    }

     //Aplica la lógica del sistema de puntuación.
    public void evaluarConsumo(Usuario u) {
        double impacto = u.calcularEmisionesCO2Total();
        int puntosObtenidos;

        // Si el impacto es negativo (gracias al reciclaje), sumamos muchos puntos.
        if (impacto < 0) {
             puntosObtenidos = 100;
        } else if (impacto < 10.0) {
            puntosObtenidos = 50; 
        } else if (impacto < 25.0) {
            puntosObtenidos = 20; 
        } else {
            puntosObtenidos = 5;  
        }

        u.sumarPuntos(puntosObtenidos); 
    }

    // Calcula el promedio de emisiones de CO2 del resto de usuarios.
    public double calcularPromedioGlobalEmisiones(Usuario usuarioExcluido) {
        double sumaEmisiones = 0;
        int contador = 0;

        for (Usuario u : listaUsuarios) {
            if (u != usuarioExcluido) {
                sumaEmisiones += u.calcularEmisionesCO2Total();
                contador++;
            }
        }
        return (contador > 0) ? sumaEmisiones / contador : 0;
    }

    // Muestra la lista de usuarios ordenada por su puntuacion (Ranking).
    public String mostrarRanking() {
        StringBuilder sb = new StringBuilder("--- RANKING GLOBAL ---\n");
        listaUsuarios.sort(Comparator.comparing(Usuario::getPuntuacion).reversed());
        
        int i = 1;
        for (Usuario u : listaUsuarios) {
            sb.append(i).append(". ").append(u.getNombre())
              .append(" - Puntos: ").append(u.getPuntuacion())
              .append(" - Emisión CO2: ").append(String.format("%.2f", u.calcularEmisionesCO2Total()))
              .append("\n");
            i++;
        }
        return sb.toString();
    }
    
    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
}
