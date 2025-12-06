// Archivo: src/com/ecogestor/ui/ArbolPanel.java
package com.ecogestor.ui;

import java.awt.*;
import javax.swing.*;

public class ArbolPanel extends JPanel {

    private int crecimientoActual = 0; 
    private int crecimientoObjetivo = 0;
    private final Timer animador;

    public ArbolPanel() {
        setPreferredSize(new Dimension(100, 380));
        setBackground(new Color(240, 248, 245));

        animador = new Timer(20, e -> animarCrecimiento());
    }

    public void setCrecimiento(int valor) {
        crecimientoObjetivo = Math.max(0, Math.min(100, valor));

        if (!animador.isRunning()) {
            animador.start();
        }
    }

    private void animarCrecimiento() {
        if (crecimientoActual < crecimientoObjetivo) crecimientoActual++;
        else if (crecimientoActual > crecimientoObjetivo) crecimientoActual--;

        repaint();

        if (crecimientoActual == crecimientoObjetivo) {
            animador.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        float t = crecimientoActual / 100f;

        //Piso
        g2.setColor(new Color(0x8D6E63));
        g2.fillOval(-100, h - 45, w + 200, 200);

        //Tronco
        int trunkMaxH = 100;
        int trunkH = (int) (trunkMaxH * t);
        int trunkY = h - 45 - trunkH;

        g2.setColor(new Color(0x8B4513));
        g2.fillRoundRect(w/2 - 20, trunkY, 40, trunkH, 16, 16);

        // Centro del árbol
        int cx = w / 2;
        int baseY = trunkY + 80;

        //Ramas
        float branchT = Math.max(0, (t - 0.50f) / 0.65f);

        if (branchT > 0) {
            g2.setColor(new Color(0x6B3410));
            g2.setStroke(new BasicStroke(4));
            
            //Angulo ramas
            drawBranch(g2, cx, baseY - 30, cx - 60, baseY - 50, branchT);
            drawBranch(g2, cx, baseY - 30, cx + 60, baseY - 50, branchT);

            g2.setStroke(new BasicStroke(6));
            drawBranch(g2, cx, baseY - 60, cx - 80, baseY - 90, branchT);
            drawBranch(g2, cx, baseY - 60, cx + 80, baseY - 90, branchT);
        }

        //Hojas
        float leafT = Math.max(0, (t - 0.59f) / 0.35f);

        if (leafT > 0) {
            drawLeaf(g2, cx - 50, baseY - 80, 45, new Color(0x2D5016), leafT);
            drawLeaf(g2, cx + 50, baseY - 80, 45, new Color(0x2D5016), leafT);
            drawLeaf(g2, cx - 70, baseY - 120, 40, new Color(0x3A6B1F), leafT);
            drawLeaf(g2, cx + 70, baseY - 120, 40, new Color(0x3A6B1F), leafT);
            drawLeaf(g2, cx,     baseY - 160, 55, new Color(0x4A7F28), leafT);
        }

        //Titulo
        g2.setColor(new Color(30, 90, 40));
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Árbol Colectivo: " + crecimientoActual + "%", 10, 20);
    }

    // Línea con interpolación (ramas)
    private void drawBranch(Graphics2D g2, int x1, int y1, int x2, int y2, float t) {
        int xm = (int) (x1 + (x2 - x1) * t);
        int ym = (int) (y1 + (y2 - y1) * t);
        g2.drawLine(x1, y1, xm, ym);
    }

    // Círculo escalado (hojas)
    private void drawLeaf(Graphics2D g2, int x, int y, int r, Color c, float t) {
        int rr = (int) (r * t);
        g2.setColor(c);
        g2.fillOval(x - rr, y - rr, rr * 2, rr * 2);
    }
}
