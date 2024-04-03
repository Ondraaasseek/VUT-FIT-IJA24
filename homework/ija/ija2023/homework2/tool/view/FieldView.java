package ija.ija2023.homework2.tool.view;


import ija.ija2023.homework2.tool.view.RobotView;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class FieldView extends JPanel {

    FieldView fieldView;

    public FieldView() {
        // Constructor initializes the FieldView
        this.fieldView = this;
    }

    public void paintComponent(Graphics g, RobotView robot) {
        // Paints the FieldView
        // g -> Graphics object
        // robot -> RobotView to be painted
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(robot.angle), robot.position.getRow(), robot.position.getCol());
        g2d.setColor(Color.CYAN);
        g2d.fillRect(robot.position.getRow(), robot.position.getCol(), 50, 50);
        g2d.setTransform(old);
    }
}