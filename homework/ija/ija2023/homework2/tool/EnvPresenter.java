package ija.ija2023.homework2.tool;

import ija.ija2023.homework2.common.Environment;
import ija.ija2023.homework2.tool.view.FieldView;
import ija.ija2023.homework2.tool.common.Position;
import ija.ija2023.homework2.tool.common.ToolEnvironment;

import javax.swing.*;
import java.awt.*;

public class EnvPresenter {

    Environment env;
    JPanel panel;

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
        JFrame frame = new JFrame("Roboti a Kameny");

        // Initialize the GUI components
        JPanel panel = new JPanel(new GridLayout(env.rows(), env.cols()));

        // Initialize Robot Field 3x3 FieldView with 0 1 -> Black
        JPanel robotEye = new JPanel(new GridLayout(2, 2));
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                FieldView robotField = new FieldView();
                robotField.setBackground(Color.BLACK);
                robotEye.add(robotField);
            }
        }





        // Iterate over the grid
        for (int i = 0; i < env.rows(); i++) {
            for (int j = 0; j < env.cols(); j++) {
                // Create a new FieldView
                FieldView field = new FieldView();

                if (env.obstacleAt(new Position(i, j))) {
                    field.setBackground(Color.GRAY);
                }

                if (env.robotAt(new Position(i, j))) {
                    field.setBackground(Color.CYAN);
                }
                // Add the FieldView to the panel
                panel.add(field);
            }
        }

        // Add the components to the main frame
        frame.add(panel);

        // Set the properties of the main frame
        frame.setSize(800, 600); // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the close operation

        // Open the GUI by making the main frame visible
        frame.setVisible(true);
    }
}