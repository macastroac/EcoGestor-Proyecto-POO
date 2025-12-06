// Archivo: src/com/ecogestor/ui/EcoGestorUI.java
package com.ecogestor.ui;

import com.ecogestor.control.EcoGestor;
import com.ecogestor.modelo.Usuario;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class EcoGestorUI extends JFrame {

    private final EcoGestor gestor;
    private JComboBox<String> usuarioComboBox;
    private JTextField consumoField;
    private JTextField recursoField;
    private JTextArea resultadoArea;
    // Historial de acciones
    private final java.util.List<String> historialAcciones = new java.util.ArrayList<>();
    //Arbol
    private ArbolPanel arbolPanel;

    // Espacio tabla de ranking
    private JTable tablaRanking;
    private DefaultTableModel modeloRanking;

    public EcoGestorUI(EcoGestor gestor) {
        this.gestor = gestor;
        initComponents();
        loadUsers();
        actualizarTablaRanking();
    }

    private void initComponents() {

        // Configuración ventana
        setTitle("EcoGestor - Gestión Ambiental");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 20));

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(213, 250, 180));
        add(panelPrincipal, BorderLayout.CENTER);

        // Formulario de registro
        JPanel controlPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Registrar Acción Ambiental"));
        controlPanel.setBackground(new Color(250, 255, 248));

        TitledBorder borde = BorderFactory.createTitledBorder("Registrar Acción Ambiental");
        borde.setTitleColor(new Color(46, 130, 50)); 
        borde.setTitleFont(new Font("Segoe UI", Font.BOLD, 16));
        controlPanel.setBorder(borde);

        usuarioComboBox = new JComboBox<>();
        recursoField = new JTextField(15);
        consumoField = new JTextField(15);
        JButton btnRegistrar = new JButton("Registrar Acción");

        btnRegistrar.setBackground(new Color(46, 130, 50));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);

        controlPanel.add(new JLabel(" - Usuario:"));
        controlPanel.add(usuarioComboBox);

        controlPanel.add(new JLabel(" - Recurso (Luz, Plástico, Agua, etc):"));
        controlPanel.add(recursoField);

        controlPanel.add(new JLabel(" - Cantidad (Consumo / Reciclaje):"));
        controlPanel.add(consumoField);

        controlPanel.add(new JLabel(""));
        controlPanel.add(btnRegistrar);

        panelPrincipal.add(controlPanel, BorderLayout.NORTH);

        // Resultados del registro
        resultadoArea = new JTextArea(10, 40);
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultadoArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollResultados = new JScrollPane(resultadoArea);
        scrollResultados.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollResultados.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollResultados.setBorder(BorderFactory.createTitledBorder("Resultados del Registro"));
        scrollResultados.setBackground(new Color(250, 255, 248));

        // Tabla del ranking
        String[] columnas = {"Usuario", "Puntos", "CO2 Total"};
        modeloRanking = new DefaultTableModel(columnas, 0);
        tablaRanking = new JTable(modeloRanking);

        JScrollPane scrollTabla = new JScrollPane(tablaRanking);
        scrollTabla.setBackground(new Color(250, 255, 248));
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Ranking Global"));

        // Color area vacia
        scrollTabla.getViewport().setBackground(new Color(250, 255, 248));
        scrollTabla.setBackground(new Color(250, 255, 248));

        JPanel corner = new JPanel();
        corner.setBackground(new Color(250, 255, 248));
        scrollTabla.setCorner(JScrollPane.LOWER_RIGHT_CORNER, corner);

        // Panel con los resultados y la tabla
        JPanel panelInferior = new JPanel(new GridLayout(1, 2, 10, 10));
        panelInferior.setBackground(new Color(250, 255, 248));

        panelInferior.add(scrollResultados);
        panelInferior.add(scrollTabla);

        panelPrincipal.add(panelInferior, BorderLayout.CENTER);

        // Botón para ver historial
        JPanel panelBotonHistorial = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonHistorial.setBackground(new Color(213, 250, 180));

        JButton btnHistorial = new JButton("Ver Historial");
        btnHistorial.setBackground(new Color(46, 130, 50));
        btnHistorial.setForeground(Color.WHITE);
        btnHistorial.setFocusPainted(false);

        btnHistorial.addActionListener(e -> abrirVentanaHistorial());

        panelBotonHistorial.add(btnHistorial);
        panelPrincipal.add(panelBotonHistorial, BorderLayout.SOUTH);

        // Evento registro
        btnRegistrar.addActionListener(this::registrarConsumo);

        //Arbol
        arbolPanel = new ArbolPanel();
        JPanel panelArbol = new JPanel(new BorderLayout());
        panelArbol.setOpaque(true);

        arbolPanel.setOpaque(true);

        panelArbol.add(arbolPanel, BorderLayout.CENTER);
        panelArbol.setPreferredSize(new Dimension(230, 0));

        panelPrincipal.add(panelArbol, BorderLayout.EAST);

        panelPrincipal.revalidate();
        panelPrincipal.repaint();

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

            usuario.registrarConsumo(recurso, cantidad);
            gestor.evaluarConsumo(usuario);

            double co2Usuario = usuario.calcularEmisionesCO2Total();
            double promedio = gestor.calcularPromedioGlobalEmisiones(usuario);

            String comparativa = (co2Usuario < promedio)
                    ? "¡Mejor que el promedio global! (" + String.format("%.2f", promedio) + " CO2)"
                    : "Por encima del promedio global (" + String.format("%.2f", promedio) + " CO2)";
            
            historialAcciones.add(
            usuario.getNombre() + " → Recurso: " + recurso +
            ", Cantidad: " + cantidad +
            ", CO2 total: " + String.format("%.2f", co2Usuario)
            );

            resultadoArea.setText(
                "Registro Exitoso para " + usuario.getNombre() + "\n" +
                "-------------------------------------\n" +
                "- Emisión CO2 Actual: " + String.format("%.2f", co2Usuario) + "\n" +
                "- Comparativa: " + comparativa + "\n" +
                "- Puntos Acumulados: " + usuario.getPuntuacion() + "\n"
            );

            // ACTUALIZAR TABLA
            actualizarTablaRanking();
            //ACTUALIZAR ARBOL
            actualizarArbol();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠ La cantidad debe ser numérica.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método actualizar la tabla de ranking
    private void actualizarTablaRanking() {
        modeloRanking.setRowCount(0); // limpiar tabla

        // Ordenar lista
        gestor.getListaUsuarios().sort(
            (a, b) -> Integer.compare(b.getPuntuacion(), a.getPuntuacion())
        );

        for (Usuario u : gestor.getListaUsuarios()) {
            modeloRanking.addRow(new Object[]{
                    u.getNombre(),
                    u.getPuntuacion(),
                    String.format("%.2f", u.calcularEmisionesCO2Total())
            });
        }
    }
    // Método actualizar el arbol
    private void actualizarArbol() {
        int totalPuntos = gestor.getListaUsuarios()
                .stream()
                .mapToInt(Usuario::getPuntuacion)
                .sum();

        // Puntos a crecimiento
        int crecimiento = Math.min(100, totalPuntos / 5); 
        arbolPanel.setCrecimiento(crecimiento);
    }

    private void abrirVentanaHistorial() {
        JFrame ventana = new JFrame("Historial de Acciones");
        ventana.setSize(500, 400);
        ventana.setLocationRelativeTo(this);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();

        if (historialAcciones.isEmpty()) {
            sb.append("No hay acciones registradas aún.");
        } else {
            for (String h : historialAcciones) {
                sb.append(h).append("\n");
            }
        }

        textArea.setText(sb.toString());

        ventana.add(new JScrollPane(textArea));
        ventana.setVisible(true);
    }
}
