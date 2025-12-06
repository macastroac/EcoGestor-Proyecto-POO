// Archivo: src/com/ecogestor/ui/BienvenidaUI.java
package com.ecogestor.ui;

import com.ecogestor.control.EcoGestor;
import java.awt.*;
import javax.swing.*;

public class BienvenidaUI extends JFrame {

    public BienvenidaUI(EcoGestor gestor) {

        setTitle("EcoGestor");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBackground(new Color(213, 250, 180));
        panel.setLayout(new GridBagLayout());

        // Tarjeta blanca
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(500, 140));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("    ¡Bienvenido a EcoGestor!    ", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(34, 34, 34));

        JLabel subtitulo = new JLabel(
            "Simulador de gestión de recursos naturales", SwingConstants.CENTER
        );
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(90, 90, 90));
        subtitulo.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        // Botón comenzar
        JButton boton = new JButton("Comenzar");
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(new Color(46, 130, 50));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addActionListener(e -> {
            new EcoGestorUI(gestor).setVisible(true);
            dispose();
        });

        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.add(titulo, BorderLayout.NORTH);
        centro.add(subtitulo, BorderLayout.CENTER);

        card.add(centro, BorderLayout.CENTER);
        card.add(boton, BorderLayout.SOUTH);
        panel.add(card);

        add(panel);
        setVisible(true);
    }
}
