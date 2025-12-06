// Archivo: src/com/ecogestor/main/Main.java
package com.ecogestor.main;

import com.ecogestor.control.EcoGestor;
import com.ecogestor.modelo.Agua;
import com.ecogestor.modelo.Energia;
import com.ecogestor.modelo.Residuo;
import com.ecogestor.modelo.Usuario;
import com.ecogestor.ui.BienvenidaUI;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        
        // 1. Inicialización del Sistema de Control
        EcoGestor gestor = new EcoGestor();

        // 2. Creación de Usuarios Iniciales
        Usuario u1 = new Usuario("Daniela Sofía");
        Usuario u2 = new Usuario("Juan Pablo");
        Usuario u3 = new Usuario("María Alejandra");
        
        gestor.agregarUsuario(u1);
        gestor.agregarUsuario(u2);
        gestor.agregarUsuario(u3);

        // 3. Asignación de Recursos Iniciales
        
        // Daniela (Energía Fósil y Agua)
        u1.agregarRecurso(new Energia("Luz Casa", 100.0, "Carbón"));
        u1.agregarRecurso(new Agua("Agua Hogar", 500.0, "Potable"));
        
        // Juan Pablo (Energía Limpia y Reciclaje)
        u2.agregarRecurso(new Energia("Luz Apartamento", 100.0, "Solar"));
        u2.agregarRecurso(new Residuo("Plásticos", 0.0, "Plástico"));
        
        // María Alejandra (Energía Fósil)
        u3.agregarRecurso(new Energia("Luz Finca", 100.0, "Fósil"));
        
        // 4. Ejecución de la Interfaz Gráfica
        SwingUtilities.invokeLater(() -> new BienvenidaUI(gestor));
    }
}
