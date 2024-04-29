package ija.ija2023.project;

import ija.ija2023.project.common.Robot;
import ija.ija2023.project.room.ControlledRobot;
import ija.ija2023.project.room.Room;
import ija.ija2023.project.tool.common.Position;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class InputInterface extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Robot Simulation");
        Button button1 = new Button("Load from file");
        Button button2 = new Button("Create new environment");
        Text text = new Text("Roboti a Kameny");
        text.setFont(Font.font("null", FontWeight.BOLD, 20));

        VBox vbox = new VBox(text, button1, button2);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        button1.setOnAction(e -> {
            // Display new window for loading from file with the following fields:
            // - File path
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile == null) {
                return;
            }
            // Get the file content
            String filePath = selectedFile.getAbsolutePath();
            // Open the file
            String content = "";
            try{
                content = new String(Files.readAllBytes(Paths.get(filePath)));
            } catch (IOException ex) {
                System.out.println("Exception " + ex.getMessage());
            }

            // First line is width and height
            String[] lines = content.split("\n");
            String[] dimensions = lines[0].split(";");
            int width = 0;
            int height = 0;
            try {
                width = Integer.parseInt(dimensions[0]);
                height = Integer.parseInt(dimensions[1]);
            } catch (Exception ex){
                System.out.println("Exception " + ex.getMessage());
                return;
            }
            Room room = Room.create(width, height);
            // Load the obstacles and robots into room
            for (int i = 1; i < lines.length; i++) {
                String[] parts = lines[i].split(";");
                if (parts.length != 3) {
                    continue;
                }
                int x = 0;
                int y = 0;
                try {
                    x = Integer.parseInt(parts[1]);
                    y = Integer.parseInt(parts[2]);
                } catch (Exception ex) {
                    System.out.println("Exception " + ex.getMessage());
                    continue;
                }
                switch (parts[0]) {
                    case "O" -> room.createObstacleAt(x, y);
                    case "CR" -> {
                        Position pos = new Position(x, y);
                        Robot robot = ControlledRobot.create(room, pos);
                    }
                    case "AR" -> {
                        Position pos = new Position(x, y);
                        // TODO: Create Autonomous robot at POS
                    }
                }
            }
            // TODO: Open simulation window with the loaded room with obstacles and robots
        });

        button2.setOnAction(e -> {
            // Display new window for creating new environment with the following fields:
            // - Width
            // - Height
            Stage NewEnvStage = new Stage();
            NewEnvStage.setTitle("Create new environment");

            Text textEnv = new Text("Create new environment");
            textEnv.setFont(Font.font("null", FontWeight.BOLD, 20));

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10); // Horizontal gap
            gridPane.setVgap(10); // Vertical gap
            gridPane.setPadding(new Insets(10, 10, 10, 10)); // Padding

            Label widthLabel = new Label("Width:");
            TextField widthField = new TextField();
            gridPane.add(widthLabel, 0, 0); // Add to column 0, row 0
            gridPane.add(widthField, 1, 0); // Add to column 1, row 0

            Label heightLabel = new Label("Height:");
            TextField heightField = new TextField();
            gridPane.add(heightLabel, 0, 1); // Add to column 0, row 1
            gridPane.add(heightField, 1, 1); // Add to column 1, row 1

            Button createButton = new Button("Create");
            Button cancelButton = new Button("Cancel");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            HBox buttonBox = new HBox(cancelButton, spacer, createButton);
            buttonBox.setPadding(new Insets(10, 10, 10, 10)); // Padding

            VBox newEnvVbox = new VBox(textEnv, gridPane, buttonBox);
            newEnvVbox.setAlignment(Pos.CENTER);
            newEnvVbox.setSpacing(10);
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

            widthField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue && widthField.getText().equals("Invalid input.")) {
                        widthField.setText("");
                    }
                }
            });

            heightField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue && heightField.getText().equals("Invalid input.")) {
                        heightField.setText("");
                    }
                }
            });
        });
    }
}
