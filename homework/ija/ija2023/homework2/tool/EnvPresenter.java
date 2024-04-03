package ija.ija2023.homework2.tool;

import ija.ija2023.homework2.common.Environment;
import ija.ija2023.homework2.tool.common.ToolRobot;
import ija.ija2023.homework2.tool.view.FieldView;
import ija.ija2023.homework2.tool.common.Position;
import ija.ija2023.homework2.tool.common.ToolEnvironment;
import ija.ija2023.homework2.tool.view.RobotView;

import javax.swing.*;
import java.awt.*;

public class EnvPresenter {

    Environment env;
    JFrame frame = new JFrame("Roboti a Kameny");
    FieldView panel = new FieldView();

    public EnvPresenter(ToolEnvironment env){
        // Constructor initializes the Presenter
        // ENV -> Environment that is being presented
        this.env = (Environment) env;
    }

    FieldView fieldAt(Position pos){
        // Returns the FieldView at the given position
        // Display Robots and Obstacles on pos
        return new FieldView();
    }

    public void open() {
        // Create the main frame
        panel.setLayout(new GridLayout(env.rows(), env.cols()));
        // Iterate over the grid
        for (int i = 0; i < env.rows(); i++) {
            for (int j = 0; j < env.cols(); j++) {
                JPanel field = new JPanel();
                Position currentPosition = new Position(i, j);

                // Check if there is a robot at the current position
                RobotView robotView = env.robotViews().stream()
                        .filter(r -> r.position.equals(currentPosition))
                        .findFirst()
                        .orElse(null);

                if (robotView != null) {
                    // If there is a robot, create a RobotView
                    FieldView robotField = new FieldView();
                    robotField.paintComponent(field.getGraphics(), robotView);
                } else if (env.obstacleAt(currentPosition)) {
                    // If there is an obstacle, set the background to gray
                    field = new JPanel();
                    field.setBackground(Color.GRAY);
                } else {
                    // Otherwise, create a new FieldView
                    field = new JPanel();
                }

                // Add the FieldView or RobotView to the panel
                panel.add(field);
            }
        }

        // Add the components to the main frame
        frame.add(panel);
        // Set the properties of the main frame
        frame.setSize(env.cols() * 100, env.rows() * 100); // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the close operation

        // Open the GUI by making the main frame visible
        frame.setVisible(true);
    }

    public static void repaint(Position OldPos, RobotView robotView) {
        // Repaint the GUI

        // Remove the robot from the old position
        System.out.println("Removing robot from position:" + OldPos);
        // Remove the cell from the grid on the OldPos from the panel.




        System.out.println("Repainting for robot: " + robotView.position + " angle:" + robotView.angle);
        // Add the robot to the new position using robotField.paintComponent
        // Add the cell to the grid on the robotView.position to the panel.


    }
}