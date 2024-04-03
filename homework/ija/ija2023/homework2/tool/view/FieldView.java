package ija.ija2023.homework2.tool.view;


import ija.ija2023.homework2.tool.common.ToolRobot;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class FieldView extends JPanel {

    RobotView robot;

    public FieldView() {
        // Constructor initializes the FieldView

    }

    public void addRobot(ToolRobot robot) {
        // Adds a robot to the FieldView
        this.robot = new RobotView();
        this.robot.position = robot.getPosition();
        this.robot.angle = robot.angle();
    }

    public void paintComponent(Graphics g) {
        // Paints the FieldView

    }
}