package ija.ija2023.homework2.tool;

import ija.ija2023.homework2.tool.view.FieldView;
import ija.ija2023.homework2.tool.common.Position;
import ija.ija2023.homework2.tool.common.ToolEnvironment;

import javax.swing.*;

public class EnvPresenter {

    ToolEnvironment env;
    public EnvPresenter(ToolEnvironment env){
        // Constructor initializes the Presenter
        // ENV -> Environment that is being presented
    this.env = env;
    }

    FieldView fieldAt(Position pos){
        // Returns the FieldView at the given position
        return null;
    }

    public void open() {
        // Create the main frame
        JFrame frame = new JFrame("Tool Environment");

        // Initialize the GUI components
        JPanel panel = new JPanel();

        // Add the components to the main frame
        frame.add(panel);

        // Set the properties of the main frame
        frame.setSize(800, 600); // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the close operation

        // Open the GUI by making the main frame visible
        frame.setVisible(true);
    }
}