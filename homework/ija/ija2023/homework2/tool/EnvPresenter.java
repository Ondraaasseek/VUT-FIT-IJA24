/* @file EnvPresenter.java
 * @brief Class for EnvPresenter
 * @autor Lukáš Katona (xkaton00) & Ondřej Novotný (xnovot2p)
 */

package ija.ija2023.homework2.tool;

import ija.ija2023.homework2.common.Environment;
import ija.ija2023.homework2.tool.common.ToolRobot;
import ija.ija2023.homework2.tool.view.FieldView;
import ija.ija2023.homework2.tool.common.Position;
import ija.ija2023.homework2.tool.common.ToolEnvironment;

import javax.swing.*;
import java.awt.*;

public class EnvPresenter {

    static Environment env;
    static JFrame frame = new JFrame("Roboti a Kameny");
    static FieldView panel = new FieldView();

    public EnvPresenter(ToolEnvironment env){
        // Constructor initializes the Presenter
        // ENV -> Environment that is being presented
        this.env = (Environment) env;
    }

    FieldView fieldAt(Position pos){
        // Returns the FieldView at the given position
        // Display Robots and Obstacles on pos
        return panel;
    }

    public void open() {
        // Create the main frame
        panel.setLayout(new GridLayout(env.rows(), env.cols()));
        // Iterate over the grid
        for (int i = 0; i < env.rows(); i++) {
            for (int j = 0; j < env.cols(); j++) {
                JPanel field = new JPanel();
                Position currentPosition = new Position(i, j);

                if (env.obstacleAt(currentPosition)) {
                    // If there is an obstacle, Draw for each obstacle gray 100 x 100 rectangle
                    field = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.setColor(Color.GRAY);
                            g.fillRect(0, 0, this.getWidth(), this.getHeight());
                        }
                    };
                    field.setPreferredSize(new Dimension(100, 100)); // Set the preferred size of the JPanel
                }

                if (env.robotAt(currentPosition)) {
                    // If there is a robot, Draw for each robot a cyan 50 x 50 circle
                    field = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.setColor(Color.CYAN);
                            g.fillOval(0, 0, 70, 70);
                            g.setColor(Color.BLACK);
                            g.fillOval(30, 0, 10, 10);
                        }
                    };
                    field.setPreferredSize(new Dimension(100, 100)); // Set the preferred size of the JPanel
                }

                // Add the FieldView or RobotView to the panel
                panel.add(field);
            }
        }

        // Add the components to the main frame
        frame.add(panel);

        panel.repaint();

        // Set the properties of the main frame
        frame.setSize((env.cols() * 100), (env.rows() * 100)); // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the close operation
        frame.setAlwaysOnTop(true); // Make the frame always on top

        // Open the GUI by making the main frame visible
        frame.setVisible(true);
    }

    public static void repaint() {
        // Repaint the GUI
        if (env == null) {
            return;
        }
        panel.removeAll();
        panel.setLayout(new GridLayout(env.rows(), env.cols()));
        // Iterate over the grid
        for (int i = 0; i < env.rows(); i++) {
            for (int j = 0; j < env.cols(); j++) {
                JPanel field = new JPanel();
                Position currentPosition = new Position(i, j);

                if (env.obstacleAt(currentPosition)) {
                    // If there is an obstacle, Draw for each obstacle gray 100 x 100 rectangle
                    field = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.setColor(Color.GRAY);
                            g.fillRect(0, 0, this.getWidth(), this.getHeight());
                        }
                    };
                    field.setPreferredSize(new Dimension(100, 100)); // Set the preferred size of the JPanel
                }

                if (env.robotAt(currentPosition)) {
                    // If there is a robot, Draw for each robot a cyan 50 x 50 circle
                    field = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.setColor(Color.CYAN);
                            g.fillOval(0, 0, 70, 70);
                            ToolRobot robot = env.getRobotFromPosition(currentPosition);
                            if (robot == null) {
                                return;
                            }
                            switch (robot.angle()) {
                                case 0:
                                    g.setColor(Color.BLACK);
                                    g.fillOval(30, 0, 10, 10);
                                    break;

                                case 45:
                                    g.setColor(Color.BLACK);
                                    g.fillOval(50, 10, 10, 10);
                                    break;
                                case 90:
                                    g.setColor(Color.BLACK);
                                    g.fillOval(60, 30, 10, 10);
                                    break;
                                case 135:
                                    g.setColor(Color.BLACK);
                                    g.fillOval(50, 50, 10, 10);
                                    break;
                                case 180:
                                    g.setColor(Color.BLACK);
                                    g.fillOval(30, 60, 10, 10);
                                    break;
                                case 225:
                                    g.setColor(Color.BLACK);
                                    g.fillOval(10, 50, 10, 10);
                                    break;
                                case 270:
                                    g.setColor(Color.BLACK);
                                    g.fillOval(0, 30, 10, 10);
                                    break;
                                case 315:
                                    g.setColor(Color.BLACK);
                                    g.fillOval(10, 10, 10, 10);
                                    break;
                                default:
                                    g.setColor(Color.BLACK);
                                    g.fillOval(30, 0, 10, 10);
                            }
                        }
                    };
                    field.setPreferredSize(new Dimension(100, 100)); // Set the preferred size of the JPanel
                }

                // Add the FieldView or RobotView to the panel
                panel.add(field);

                panel.repaint();

                frame.setSize((env.cols() * 100), (env.rows() * 100)); // Set the size of the frame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the close operation
                frame.setAlwaysOnTop(true); // Make the frame always on top

                // Open the GUI by making the main frame visible
                frame.setVisible(true);
            }
        }
    }
}