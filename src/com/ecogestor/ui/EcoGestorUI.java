// Archivo: src/com/ecogestor/ui/EcoGestorUI.java
package com.ecogestor.ui;

import com.ecogestor.control.EcoGestor;
import com.ecogestor.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EcoGestorUI extends JFrame {
    private final EcoGestor gestor;
    private JComboBox<String> usuarioComboBox;
    private JTextField consumoField;
    private JTextField recursoField;
    private JTextArea resultadoArea;

    public EcoGestorUI(EcoGestor gestor) {
        this.gestor = gestor;
        initComponents();
        loadUsers();
    }

    private void initComponents() {
        setTitle("EcoGestor - Simulador de Recursos (Swing)");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel controlPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        usuarioComboBox = new JComboBox<>();
        recursoField = new JTextField(15);
        consumoField = new JTextField(15);
        JButton btnRegistrar = new JButton("Registrar Acción"); // Cambiado a Acción
        
        // --- Controles UI ---
        controlPanel.add(new JLabel("Usuario:"));
        controlPanel.add(usuarioComboBox);
        controlPanel.add(new JLabel("Recurso (Ej: Luz Casa/Plásticos):"));
        controlPanel.add(recursoField);
        controlPanel.add(new JLabel("Cantidad (Consumo/Reciclado):"));
        controlPanel.add(consumoField);
        controlPanel.add(new JLabel()); 
        controlPanel.add(btnRegistrar);
        
        add(controlPanel, BorderLayout.NORTH);

        resultadoArea = new JTextArea(15, 40);
        resultadoArea.setEditable(false);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        // --- Conexión Lógica ---
        btnRegistrar.addActionListener(this::registrarConsumo);

        setVisible(true);
    }
    
    private void loadUsers() {
        for (Usuario u : gestor.getListaUsuarios()) {
            usuarioComboBox.addItem(u.getNombre());
        }
    }

    private void registrarConsumo(ActionEvent e) {
        try {
            String nombreUsuario = (String) usuarioComboBox.getSelectedItem();
            String recurso = recursoField.getText();
            double cantidad = Double.parseDouble(consumoField.getText());

            Usuario usuario = gestor.getUsuarioPorNombre(nombreUsuario); 
            
            if (usuario == null) {
                resultadoArea.setText("Error: Usuario no encontrado.");
                return;
            }
            
            // 1. Registrar el consumo o reciclaje (según lógica en Usuario.java)
            usuario.registrarConsumo(recurso, cantidad); 
            
            // 2. Evaluar y obtener puntos
            gestor.evaluarConsumo(usuario); 

            // 3. Generar comparativa (requisito de la Entrega 2)
            double co2Usuario = usuario.calcularEmisionesCO2Total();
            double promedioGlobal = gestor.calcularPromedioGlobalEmisiones(usuario);
            String comparativa;
            if (co2Usuario < promedioGlobal) {
                comparativa = "¡Mejor que el promedio global! (" + String.format("%.2f", promedioGlobal) + " CO2)";
            } else {
                 comparativa = "Por encima del promedio global (" + String.format("%.2f", promedioGlobal) + " CO2)";
            }
            
            // Mostrar resultados y ranking actualizado
            resultadoArea.setText("Registro Exitoso para " + nombreUsuario + "\n");
            resultadoArea.append("Emisión de CO2 Actual: " + String.format("%.2f", co2Usuario) + "\n");
            resultadoArea.append("Comparativa: " + comparativa + "\n");
            resultadoArea.append("Puntos Acumulados: " + usuario.getPuntuacion() + "\n");
            resultadoArea.append("\n" + gestor.mostrarRanking());
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
             resultadoArea.setText("Error durante el registro: " + ex.getMessage());
        }
    }
}