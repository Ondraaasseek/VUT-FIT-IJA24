package ija.ija2023.project;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class InputInterface extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Robot Simulation");
        Button button1 = new Button("Load from file");
        Button button2 = new Button("Create new environment");

        VBox vbox = new VBox(button1, button2);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        button1.setOnAction(e -> {
            System.out.println("Load from file");
            // TODO Load from file
        });

        button2.setOnAction(e -> {
            // Display new window for creating new environment with the following fields:
            // - Width
            // - Height
            Stage NewEnvStage = new Stage();
            NewEnvStage.setTitle("Create new environment");
            TextField widthField = new TextField();
            TextField heightField = new TextField();
            Button createButton = new Button("Create");
            Button cancelButton = new Button("Cancel");

            VBox newEnvVbox = new VBox(widthField, heightField, createButton, cancelButton);
            Scene newEnvScene = new Scene(newEnvVbox, 300, 200);
            NewEnvStage.setScene(newEnvScene);
            // Close the old windows
            primaryStage.close();
            NewEnvStage.show();

            cancelButton.setOnAction(e2 -> {
                NewEnvStage.close();
                primaryStage.show();
            });

            createButton.setOnAction(e2 -> {
                // Check if the input fields are int
                boolean valid = true;
                int width = 0;
                int height = 0;
                try {
                    width = Integer.parseInt(widthField.getText());
                    if (width < 1) {
                        throw new Exception("Width out of range.");
                    }
                } catch (Exception ex) {
                    widthField.setText("Invalid input.");
                    System.out.println("Exception " + ex.getMessage());
                    valid = false;
                }

                try {
                    height = Integer.parseInt(heightField.getText());
                    if (height < 1) {
                        throw new Exception("Height out of range.");
                    }
                } catch (Exception ex) {
                    heightField.setText("Invalid input.");
                    System.out.println("Exception " + ex.getMessage());
                    valid = false;
                }

                if (!valid) {
                    return;
                }
                // Call EnvCreator with the width and height
                NewEnvStage.close();
                EnvCreator.start(width, height);

            });
        });
    }
}
